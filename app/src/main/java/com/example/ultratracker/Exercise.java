package com.example.ultratracker;

import java.time.LocalDate;
import java.time.LocalTime;

class Exercise {

    private String exerciseType;
    private LocalDate completedDate;
    private LocalTime duration;
    private int caloriesBurned;
    private int sets;
    private int key;

    // Constructor from user input
    public Exercise(String exerciseType, LocalDate completedDate, LocalTime duration, int caloriesBurned, int sets) {
        this.exerciseType = exerciseType;
        this.completedDate = completedDate;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.sets = sets;
        this.key = getRandomBetweenRange(1, 100000);
    }

    // Default constructor
    public Exercise() {
        this.exerciseType = "";
        this.caloriesBurned = 0;
        this.sets = 0;
        this.key = getRandomBetweenRange(1, 100000);
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public String getCompletedDate() {
        return completedDate.toString();
    }

    public String getDuration() {
        return duration.toString();
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public int getSets() {
        return sets;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public static int getRandomBetweenRange(double min, double max){
        double x = (Math.random()*((max-min)+1))+min;
        return (int) x;
    }
}