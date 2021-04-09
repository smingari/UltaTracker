package com.example.ultratracker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class WeightliftingWorkout extends Exercise {

    List<Weightlifting> exerciseList;

    public WeightliftingWorkout(String exerciseType, LocalDate completedDate, LocalTime duration, int caloriesBurned, List<Weightlifting> exerciseList) {
        super("Weightlifting", completedDate, duration, caloriesBurned);
        this.exerciseList = exerciseList;
    }

    public WeightliftingWorkout(String exerciseType, String completedDate, String duration, int caloriesBurned, List<Weightlifting> exerciseList) {
        super("Weightlifting", completedDate, duration, caloriesBurned);
        this.exerciseList = exerciseList;
    }

    public List<Weightlifting> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(List<Weightlifting> exerciseList) {
        this.exerciseList = exerciseList;
    }
}