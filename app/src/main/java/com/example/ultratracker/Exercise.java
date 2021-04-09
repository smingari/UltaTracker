package com.example.ultratracker;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

class Exercise {

    private String exerciseType;
    private LocalDate completedDate;
    private LocalTime completedTime;
    private int duration;
    private int caloriesBurned;
    private int key;

    // Constructor from user input
    public Exercise(String exerciseType, LocalDate completedDate, LocalTime completedTime, int duration, int caloriesBurned) {
        this.exerciseType = exerciseType;
        this.completedDate = completedDate;
        this.completedTime = completedTime;
        this.caloriesBurned = caloriesBurned;
        this.duration = duration;
        this.key = getRandomBetweenRange(1, 100000);
    }

    // Constructor with date and duration as strings
    public Exercise(String exerciseType, String completedDate, String completedTime, int duration, int caloriesBurned) {
        this.exerciseType = exerciseType;
        this.completedDate = LocalDate.parse(completedDate);
        this.completedTime = LocalTime.parse(completedTime);
        this.caloriesBurned = caloriesBurned;
        this.duration = duration;
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

    public String getCompletedTime() {
        return completedTime.toString();
    }

    public void setCompletedTime(LocalTime completedTime) {
        this.completedTime = completedTime;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    public static int getRandomBetweenRange(double min, double max){
        double x = (Math.random()*((max-min)+1))+min;
        return (int) x;
    }
}