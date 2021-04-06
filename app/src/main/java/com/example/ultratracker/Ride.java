package com.example.ultratracker;

import java.time.LocalDate;
import java.time.LocalTime;

public class Ride extends Exercise {

    private double distance;
    private double pace;

    public Ride(String exerciseType, LocalDate completedDate, LocalTime duration, int caloriesBurned, int sets, double distance, double pace) {
        super(exerciseType, completedDate, duration, caloriesBurned, sets);
        this.distance = distance;
        this.pace = pace;
    }

    public Ride(double distance, double pace) {
        this.distance = distance;
        this.pace = pace;
    }
}