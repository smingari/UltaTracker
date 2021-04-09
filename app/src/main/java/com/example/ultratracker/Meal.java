package com.example.ultratracker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Meal {
    private String name;
    private int cals;
    private int protein;
    private int carbs;
    private int fat;
    private int fiber;
    private LocalDate date;
    private int key;
    private String mealName;
    private List<Food> foodList = new ArrayList<>();
    //private int listSize = 0;

    // Constructor method
    public Meal(String name, int cals, int protein, int carbs, int fat, int fiber, LocalDate date, List<Food> foodList) {
        this.name = name;
        this.cals = cals;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.fiber = fiber;
        this.date = date;
        this.mealName = mealName;
        for (Food food: foodList) { this.foodList.add(food); }
        this.key = getRandomBetweenRange(1, 100000);
    }

    public Meal(String name, int cals, int protein, int carbs, int fat, int fiber, String date, String mealName, List<Food> foodList, int key) {
        this.name = name;
        this.cals = cals;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.fiber = fiber;
        this.date = LocalDate.parse(date);
        this.mealName = mealName;
        for (Food food: foodList) { this.foodList.add(food); }
        this.key = key;
    }

    // Getter methods
    public String getName() { return name; }
    public int getCals() { return cals; }
    public int getProtein() { return protein; }
    public int getCarbs() { return carbs; }
    public int getFat() { return fat; }
    public int getFiber() { return fiber; }
    public String getDate() { return date.toString(); }
    public String getMealName() {
        return mealName;
    }
    public List<Food> getFoodList() {
        return foodList;
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
    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public void addToMeal(Food food) { foodList.add(food); }
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
