package com.example.ultratracker;

public class Weightlifting {

    private String exerciseName;
    private int sets, reps, weight;

    // Constructor with user input
    public Weightlifting(String exerciseName, int sets, int reps, int weight) {
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    // Default constructor
    public Weightlifting() {
        this.exerciseName = "";
        this.sets = 0;
        this.reps = 0;
        this.weight = 0;
    }

    // Getters/Setters for each field
    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }
    public int getSets() { return sets; }
    public void setSets(int sets) { this.sets = sets; }
    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }
    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }
}