package com.example.ultratracker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class WeightliftingWorkout extends Exercise {

    List<Weightlifting> exerciseList;

    public WeightliftingWorkout(List<Weightlifting> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public WeightliftingWorkout(String exerciseType, LocalDate completedDate, LocalTime duration, int caloriesBurned, int sets, List<Weightlifting> exerciseList) {
        super(exerciseType, completedDate, duration, caloriesBurned, sets);
        this.exerciseList = exerciseList;
    }
}