package com.example.ultratracker;

import java.time.LocalDate;
import java.time.LocalTime;

public class Ride extends Exercise {

    private double distance;
    private double pace;

    public Ride(LocalDate completedDate, LocalTime completedTime, int duration, int caloriesBurned, double distance, double pace) {
        super("Ride", completedDate, completedTime, duration, caloriesBurned);
        this.distance = distance;
        this.pace = pace;
    }

    public Ride(String completedDate, String completedTime, int duration, int caloriesBurned, double distance, double pace) {
        super("Ride", completedDate, completedTime, duration, caloriesBurned);
        this.distance = distance;
        this.pace = pace;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getPace() {
        return pace;
    }

    public void setPace(double pace) {
        this.pace = pace;
    }
}