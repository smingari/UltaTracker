package com.example.ultratracker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class WeightliftingWorkout extends Exercise {

    List<Weightlifting> exerciseList;

    public WeightliftingWorkout(LocalDate completedDate, LocalTime completedTime, int duration, int caloriesBurned, List<Weightlifting> exerciseList) {
        super("Weightlifting", completedDate, completedTime, duration, caloriesBurned);
        this.exerciseList = exerciseList;
    }

    public WeightliftingWorkout(String completedDate, String completedTime, int duration,  int caloriesBurned, List<Weightlifting> exerciseList) {
        super("Weightlifting", completedDate, completedTime, duration, caloriesBurned);
        this.exerciseList = exerciseList;
    }

    public List<Weightlifting> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(List<Weightlifting> exerciseList) {
        this.exerciseList = exerciseList;
    }
}