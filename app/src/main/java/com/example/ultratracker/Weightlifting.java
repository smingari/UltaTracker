package com.example.ultratracker;

import java.time.LocalDate;

public class Weightlifting {

    private String exerciseName;
    private int sets, reps, weight, key;
    private LocalDate date;

    // Constructor for database retrieval
    public Weightlifting(String exerciseName, int sets, int reps, int weight, String date, int key) {
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.date = LocalDate.parse(date);
        this.key = key;
    }

    // Constructor with user input
    public Weightlifting(String exerciseName, int sets, int reps, int weight, LocalDate date) {
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.date = date;
        this.key = getRandomBetweenRange(1, 100000);
    }

    // Getters/Setters for each field
    public String getName() { return exerciseName; }
    public void setName(String exerciseName) { this.exerciseName = exerciseName; }
    public int getSets() { return sets; }
    public void setSets(int sets) { this.sets = sets; }
    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }
    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }
    public int getKey() { return key; }
    public void setKey(int key) { this.key = key; }
    public String getDate() {
        return date.toString();
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public static int getRandomBetweenRange(double min, double max){
        double x = (Math.random()*((max-min)+1))+min;
        return (int) x;
    }
}