package com.example.ultratracker;


import java.time.LocalDate;

public class Food {
    private String name;
    private int cals;
    private int protein;
    private int carbs;
    private int fat;
    private int fiber;
    private LocalDate date;
    private int key;

    // Constructor method
    public Food(String name, int cals, int protein, int carbs, int fat, int fiber, LocalDate date) {
        this.name = name;
        this.cals = cals;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.fiber = fiber;
        this.date = date;
        this.key = getRandomBetweenRange(1, 100000);
    }

    public Food(String name, int cals, int protein, int carbs, int fat, int fiber, String date, int key) {
        this.name = name;
        this.cals = cals;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.fiber = fiber;
        this.date = LocalDate.parse(date);
        this.key = key;
    }

    // Getter methods
    public String getName() { return name; }
    public int getCals() { return cals; }
    public int getProtein() { return protein; }
    public int getCarbs() { return carbs; }
    public int getFat() { return fat; }
    public int getFiber() { return fiber; }
    public String getDate() {
        return date.toString();
    }
    public int getKey() { return key; }

    // Setter methods
    public void setName(String name) { this.name = name; }
    public void setCals(int cals) { this.cals = cals; }
    public void setProtein(int protein) { this.protein = protein; }
    public void setCarbs(int carbs) { this.carbs = carbs; }
    public void setFat(int fat) { this.fat = fat; }
    public void setFiber(int fiber) { this.fiber = fiber; }
    public void setDate(LocalDate date) { this.date = date; }

    // Generates random number for key
    public static int getRandomBetweenRange(double min, double max){
        double x = (Math.random()*((max-min)+1))+min;
        return (int) x;
    }

    @Override
    public String toString() {
        return this.name + ", " + this.cals + ", " + this.protein + ", " + this.carbs + ", " + this.fat + ", " + this.fiber;
    }
}
