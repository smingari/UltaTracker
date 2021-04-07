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
    private String mealName;
    private int key;
    private int mealKey;

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

    // Constructor for use in meal database (passed in)
    public Food(String name, int cals, int protein, int carbs, int fat, int fiber, String date, String mealName, int mealKey) {
        this.name = name;
        this.cals = cals;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.fiber = fiber;
        this.date = LocalDate.parse(date);
        this.mealName = mealName;
        this.key = getRandomBetweenRange(1, 100000);
        this.mealKey = mealKey;
    }

    // Constructor for use in meal database (retrieval)
    public Food(String name, int cals, int protein, int carbs, int fat, int fiber, String date, String mealName, int key, int mealKey) {
        this.name = name;
        this.cals = cals;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.fiber = fiber;
        this.date = LocalDate.parse(date);
        this.mealName = mealName;
        this.key = key;
        this.mealKey = mealKey;
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
    public String getMealName() { return mealName;}
    public int getKey() { return key; }
    public int getMealKey() { return mealKey; }

    // Setter methods
    public void setName(String name) { this.name = name; }
    public void setCals(int cals) { this.cals = cals; }
    public void setProtein(int protein) { this.protein = protein; }
    public void setCarbs(int carbs) { this.carbs = carbs; }
    public void setFat(int fat) { this.fat = fat; }
    public void setFiber(int fiber) { this.fiber = fiber; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setMealName(String mealName) { this.mealName = mealName;}
    public void setMealKey(int mealKey) { this.mealKey = mealKey; }

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
