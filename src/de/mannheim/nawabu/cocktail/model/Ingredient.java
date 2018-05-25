package de.mannheim.nawabu.cocktail.model;

public class Ingredient {
    private int id;
    private String name;
    private int size;
    private int level;
    private int pump;
    private int amount = 0;

    public Ingredient(int id, String name, int size, int level, int pump) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.level = level;
        this.pump = pump;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public int getLevel() {
        return level;
    }

    public int getPump() { return pump; }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
