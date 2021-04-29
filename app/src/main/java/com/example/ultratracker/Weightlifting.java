package com.example.ultratracker;

public class Weightlifting {

    private String exerciseName;
    private int sets, reps, weight, key;

    public Weightlifting(String exerciseName, int sets, int reps, int weight, int key) {
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.key = key;
    }

    // Constructor with user input
    public Weightlifting(String exerciseName, int sets, int reps, int weight) {
        this(exerciseName,sets,reps,weight,getRandomBetweenRange(1, 100000));
    }

    // Default constructor
    public Weightlifting() {
        this("",0,0,0,getRandomBetweenRange(1, 100000));
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
    public int getKey() { return weight; }
    public void setKey(int weight) { this.weight = weight; }
    public static int getRandomBetweenRange(double min, double max){
        double x = (Math.random()*((max-min)+1))+min;
        return (int) x;
    }
}