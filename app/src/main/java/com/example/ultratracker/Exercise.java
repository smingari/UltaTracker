package com.example.ultratracker;

import java.time.LocalDate;
import java.time.LocalTime;

class Exercise {

    private String exerciseType;
    private LocalDate completedDate;
    private LocalTime duration;
    private int caloriesBurned;
    private int key;

    // Constructor from user input
    public Exercise(String exerciseType, LocalDate completedDate, LocalTime duration, int caloriesBurned) {
        this.exerciseType = exerciseType;
        this.completedDate = completedDate;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.key = getRandomBetweenRange(1, 100000);
    }

    // Default constructor
    public Exercise() {
        this.exerciseType = "";
        this.caloriesBurned = 0;
        this.key = getRandomBetweenRange(1, 100000);
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }

    public String getCompletedDate() {
        return completedDate.toString();
    }

    public void setCompletedDate(LocalDate completedDate) {
        this.completedDate = completedDate;
    }

    public String getDuration() {
        return duration.toString();
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
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