package de.mannheim.nawabu.cocktail.model;

import java.io.File;
import java.sql.*;
import java.util.*;

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
        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }


    }

    private void openDB() throws SQLException {
        String url;
        String system = System.getProperty("os.name").toLowerCase();

        if(system.contains("win"))
            url = "jdbc:sqlite:" + System.getProperty("user.home") + File.separator + "cocktail.sqlite";
        else
            url = "jdbc:sqlite:" + File.separator + "home" + File.separator + "pi" + File.separator + "CocktailMixer" + File.separator + "cocktail.sqlite";

        conn = DriverManager.getConnection(url);
        Statement st = conn.createStatement();
        String sql = "PRAGMA foreign_keys = ON;";
        st.executeUpdate(sql);
        st.close();
    }

    private void createTables() throws SQLException {
        String sql;
        Statement st = conn.createStatement();

        sql = "create table IF NOT EXISTS recipes\n" +
                "(\n" +
                "  recipe_id integer not null,\n" +
                "  name      text    not null,\n" +
                "  primary key (recipe_id autoincrement)\n" +
                ");";
        st.executeUpdate(sql);

        sql = "create table IF NOT EXISTS ingredients\n" +
                "(\n" +
                "  ingredient_id integer not null,\n" +
                "  name          text    not null,\n" +
                "  size          integer not null,\n" +
                "  level         integer not null,\n" +
                "  pump          int,\n" +
                "  primary key (ingredient_id autoincrement)\n" +
                ");";
        st.executeUpdate(sql);

        sql = "create unique index IF NOT EXISTS ingredients_pump_uindex\n" +
                "  on ingredients (pump);";
        st.executeUpdate(sql);

        sql = "create table IF NOT EXISTS recipe_ingredients\n" +
                "(\n" +
                "  recipe_id     integer not null,\n" +
                "  ingredient_id integer not null,\n" +
                "  amount        integer not null,\n" +
                "  filler        integer not null,\n" +
                "  primary key (recipe_id, ingredient_id),\n" +
                "  foreign key (recipe_id) references recipes\n" +
                "    on delete cascade,\n" +
                "  foreign key (ingredient_id) references ingredients\n" +
                "    on delete cascade\n" +
                ");";
        st.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS configs(\n" +
                "  Lock char(1) not null DEFAULT 'X',\n" +
                "  glass_size integer NOT NULL,\n" +
                "  password text NOT NULL,\n" +
                "  constraint PK_CONFIGS PRIMARY KEY (Lock),\n" +
                "  constraint CK_CONFIGS_Locked CHECK (Lock='X')\n" +
                ");";
        st.executeUpdate(sql);

        try {
            sql = "INSERT INTO configs (glass_size, password) VALUES (500, 'test');";
            st.executeUpdate(sql);
        }
        catch (Exception e) {}

        st.close();
    }

    public int getGlassSize() {
        String sql = "SELECT glass_size FROM configs;";
        int size = 0;

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            size = rs.getInt("glass_size");

            rs.close();
            st.close();

        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return size;
    }

    public void setGlassSize(int size) {
        String sql = String.format("UPDATE configs SET glass_size=%d;", size);

        runStatement(sql);
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
        String sql = String.format("SELECT r.recipe_id FROM\n" +
                "  recipes r JOIN recipe_ingredients ri on r.recipe_id = ri.recipe_id\n" +
                "  WHERE ri.ingredient_id=%d;", iID);

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                int id = rs.getInt("recipe_id");
                delteRecipe(id);
            }

            rs.close();
            st.close();

        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        sql = String.format("DELETE FROM ingredients WHERE ingredient_id=%d;", iID);

        runStatement(sql);
    }

    public void delteRecipe(int rID) {
        String sql = String.format("DELETE FROM recipes WHERE recipe_id=%d;", rID);

        runStatement(sql);
    }

    public void updatePump(int iID, int pump) {
        if(pump >= 0) {
            String sql = String.format("UPDATE ingredients SET pump=NULL WHERE pump=%d;", pump);
            runStatement(sql);
            sql = String.format("UPDATE ingredients SET pump=%d WHERE ingredient_id=%d;", pump, iID);
            runStatement(sql);
        }
        else {
            String sql = String.format("UPDATE ingredients SET pump=NULL WHERE ingredient_id=%d;", iID);
            runStatement(sql);
        }
    }

    public void updateIngredientLevel(int iID, int level) {
        String sql = String.format("UPDATE ingredients SET level=%d WHERE ingredient_id=%d;", level, iID);

        runStatement(sql);
    }

    public int amountIngredients() {
        String sql = "SELECT COUNT(ingredient_id) FROM ingredients;";
        int size = 0;

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            size = rs.getInt("COUNT(ingredient_id)");

            rs.close();
            st.close();

        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return size;
    }

    private ArrayList<Ingredient> runIngredientsStatement(String sql) {
        ArrayList<Ingredient> rl = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                int id = rs.getInt("ingredient_id");
                String name = rs.getString("name");
                int size = rs.getInt("size");
                int level = rs.getInt("level");
                int pump = rs.getInt("pump");

                rl.add(new Ingredient(id, name, size, level, pump));
            }

            rs.close();
            st.close();

        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return rl;
    }

    public ArrayList<Ingredient> getIngredients(int page) {
        String sql = "SELECT * FROM ingredients LIMIT 7 OFFSET " + 7*(page-1) + ";";

        return runIngredientsStatement(sql);
    }

    public Map<Integer, Ingredient> getPumpIngredients() {
        String sql = "SELECT * FROM ingredients WHERE pump IS NOT NULL;";

        ArrayList<Ingredient> ingredients =  runIngredientsStatement(sql);

        Map<Integer, Ingredient> ingredientMap = new HashMap<>();

        for(ListIterator<Ingredient> li = ingredients.listIterator(); li.hasNext();) {
            Ingredient i = li.next();

            ingredientMap.put(i.getPump(), i);
        }

        return ingredientMap;
    }

    public void saveRecipe(Recipe recipe) {
        try {
            Statement st = conn.createStatement();
            String sql = String.format("INSERT INTO recipes (name) VALUES ('%s');", recipe.getName());

            st.executeUpdate(sql);
            ResultSet rs = st.getGeneratedKeys();
            long id = rs.getLong(1);
            rs.close();

            sql = String.format("INSERT INTO recipe_ingredients (recipe_id, ingredient_id, amount, filler) " +
                    "VALUES (%d, %d, 0, 1);", id, recipe.getFiller().getId());
            st.executeUpdate(sql);

            for(ListIterator<Ingredient> li = recipe.getIngredients().listIterator(); li.hasNext();) {
                Ingredient i = li.next();
                sql = String.format("INSERT INTO recipe_ingredients (recipe_id, ingredient_id, amount, filler) " +
                        "VALUES (%d, %d, %d, 0);", id, i.getId(), i.getAmount());
                st.executeUpdate(sql);
            }


            st.close();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public int amountRecipes() {
        String sql = "SELECT COUNT(recipe_id) FROM recipes;";
        int size = 0;

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            size = rs.getInt("COUNT(recipe_id)");

            rs.close();
            st.close();

        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return size;
    }

    public int amountCraftableRecipes() {
        String sql = "SELECT * FROM recipes EXCEPT SELECT r.* FROM\n" +
                "  recipes r JOIN recipe_ingredients ri on r.recipe_id = ri.recipe_id LEFT JOIN ingredients i on ri.ingredient_id = i.ingredient_id\n" +
                "  WHERE i.pump IS NULL;";

        ArrayList<Recipe> rl =  runRecipeStatement(sql);

        return rl.size();
    }

    public ArrayList<Recipe> getRecipes(int page) {
        String sql = "SELECT * FROM recipes LIMIT 7 OFFSET " + 7*(page-1) + ";";

        ArrayList<Recipe> rl = new ArrayList<>();

        return runRecipeStatement(sql);
    }

    public ArrayList<Recipe> getCraftableRecipes(int page) {
        String sql = "SELECT * FROM recipes EXCEPT SELECT r.* FROM\n" +
                "  recipes r JOIN recipe_ingredients ri on r.recipe_id = ri.recipe_id LEFT JOIN ingredients i on ri.ingredient_id = i.ingredient_id\n" +
                "  WHERE i.pump IS NULL LIMIT 12 OFFSET " + 12*(page-1) + ";";

        return runRecipeStatement(sql);

    }

    public Recipe getRecipe(int id) {
        String sql = String.format("SELECT * FROM recipes WHERE recipe_id=%d;", id);

        ArrayList<Recipe> recipes = runRecipeStatement(sql);

        return recipes.get(0);
    }

    private ArrayList<Recipe> runRecipeStatement(String sql) {
        ArrayList<Recipe> rl = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rSet = st.executeQuery(sql);

            while(rSet.next()) {
                int id = rSet.getInt("recipe_id");
                String name = rSet.getString("name");

                Recipe r = new Recipe(id, name);

                rl.add(r);
            }

            rSet.close();

            for (Recipe r : rl) {
                sql = String.format("SELECT i.*, r.* FROM\n" +
                        "  recipe_ingredients r JOIN ingredients i on r.ingredient_id = i.ingredient_id\n" +
                        "  WHERE r.recipe_id=%d;", r.getId());
                ResultSet iSet = st.executeQuery(sql);

                while (iSet.next()) {
                    int iID = iSet.getInt("ingredient_id");
                    String iName = iSet.getString("name");
                    int size = iSet.getInt("size");
                    int level = iSet.getInt("level");
                    int pump = iSet.getInt("pump");
                    int amount = iSet.getInt("amount");
                    int filler = iSet.getInt("filler");

                    if(filler == 1)
                        r.setFiller(new Ingredient(iID, iName, size, level, pump));
                    else
                        r.addIngredient(new Ingredient(iID, iName, size, level, pump, amount));
                }

                iSet.close();
            }

            st.close();

        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return rl;
    }
}
