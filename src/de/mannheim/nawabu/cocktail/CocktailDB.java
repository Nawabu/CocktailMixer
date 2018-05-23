package de.mannheim.nawabu.cocktail;

import javax.swing.plaf.nimbus.State;
import java.io.File;
import java.sql.*;
import java.util.Map;

public class CocktailDB {
    private static CocktailDB ourInstance = new CocktailDB();
    private Connection conn;

    public static CocktailDB getInstance() {
        return ourInstance;
    }

    private CocktailDB() {
        try {
            openDB();
            createTables();
            initializePumps();
        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }


    }

    private void openDB() throws SQLException {
        String url = "jdbc:sqlite:" + System.getProperty("user.home") + File.separator + "cocktail.sqlite";

        conn = DriverManager.getConnection(url);
        Statement st = conn.createStatement();
        String sql = "PRAGMA foreign_keys = ON;";
        st.executeUpdate(sql);
        st.close();
    }

    private void createTables() throws SQLException {
        String sql;
        Statement st = conn.createStatement();

        sql = "CREATE TABLE IF NOT EXISTS recipes (\n" +
                "  recipe_id integer PRIMARY KEY AUTOINCREMENT NOT NULL ,\n" +
                "  name text NOT NULL\n" +
                ");";
        st.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS ingredients (\n" +
                "  ingredient_id integer PRIMARY KEY AUTOINCREMENT NOT NULL ,\n" +
                "  name text NOT NULL ,\n" +
                "  size integer NOT NULL ,\n" +
                "  level integer NOT NULL\n" +
                ");";
        st.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS pumps (\n" +
                "  pump_id integer PRIMARY KEY NOT NULL ,\n" +
                "  ingredient_id integer ,\n" +
                "  FOREIGN KEY (ingredient_id) REFERENCES ingredients (ingredient_id)\n" +
                "  ON DELETE SET NULL ON UPDATE NO ACTION\n" +
                ");";
        st.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS recipe_ingredients (\n" +
                "  recipe_id integer,\n" +
                "  ingredient_id integer,\n" +
                "  PRIMARY KEY (recipe_id, ingredient_id),\n" +
                "  FOREIGN KEY (recipe_id) REFERENCES recipes (recipe_id)\n" +
                "  ON DELETE CASCADE ON UPDATE NO ACTION,\n" +
                "  FOREIGN KEY (ingredient_id) REFERENCES ingredients(ingredient_id)\n" +
                "  ON DELETE CASCADE ON UPDATE NO ACTION\n" +
                ");";
        st.executeUpdate(sql);

        st.close();
    }

    private void initializePumps() {
        String sql;
        for(int x=1; x<=14; x++) {
            sql = String.format("INSERT INTO pumps (pump_id) VALUES (%d)", x);
            runStatement(sql);
        }
    }

    private void runStatement(String sql) {
        try {
            Statement st = conn.createStatement();
            st.executeUpdate(sql);
            st.close();
        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public void saveIngredient(String name, int size, int level) {
        String sql = String.format("INSERT INTO ingredients (name, size, level) VALUES ('%s', %d, %d);", name, size, level);

        runStatement(sql);
    }

    public void deleteIngredient(int iID) {
        String sql = String.format("DELETE FROM ingredients WHERE ingredient_id=%d;", iID);

        runStatement(sql);
    }

    public void updateIngredientLevel(int iID, int level) {
        String sql = String.format("UPDATE ingredients SET level=%d WHERE ingredient_id=%d;", level, iID);

        runStatement(sql);
    }
}
