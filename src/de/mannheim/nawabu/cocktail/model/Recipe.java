package de.mannheim.nawabu.cocktail.model;

import java.util.ArrayList;

public class Recipe {
    private int id;
    private String name;
    private Ingredient filler;
    ArrayList<Ingredient> ingredients = new ArrayList<>();

    public Recipe() {
    }

    public Recipe(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public Ingredient getFiller() {
        return filler;
    }

    public void setFiller(Ingredient filler) {
        this.filler = filler;
    }
}
