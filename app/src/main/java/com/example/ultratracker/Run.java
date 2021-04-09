package com.example.ultratracker;

import java.time.LocalDate;
import java.time.LocalTime;

public class Run extends Exercise {

    private double distance;
    private double pace;

    public Run(String exerciseType, LocalDate completedDate, LocalTime duration, int caloriesBurned, double distance, double pace) {
        super("Run", completedDate, duration, caloriesBurned);
        this.distance = distance;
        this.pace = pace;
    }

    public Run(String exerciseType, String completedDate, String duration, int caloriesBurned, double distance, double pace) {
        super("Run", completedDate, duration, caloriesBurned);
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