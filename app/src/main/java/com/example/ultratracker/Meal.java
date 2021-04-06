package com.example.ultratracker;

import java.time.LocalDate;
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
    private List<Food> foodList;

    // Constructor method
    public Meal(String name, int cals, int protein, int carbs, int fat, int fiber, LocalDate date, List<Food> foodList) {
        this.name = name;
        this.cals = cals;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.fiber = fiber;
        this.date = date;
        this.foodList = foodList;
        this.key = getRandomBetweenRange(1, 100000);
    }

    public Meal(String name, int cals, int protein, int carbs, int fat, int fiber, String date, List<Food> foodList, int key) {
        this.name = name;
        this.cals = cals;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.fiber = fiber;
        this.date = LocalDate.parse(date);
        this.foodList = foodList;
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

    public void addFood(Food food) { this.foodList.add(food); }
    public void deleteFood(Food food) { this.foodList.remove(food); }

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
