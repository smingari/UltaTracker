package com.example.ultratracker;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WeightLiftingWorkoutUnitTest {
    private WeightliftingWorkout w1, w2;
    private String exerType;
    private int sets, cal;
    private LocalDate d1;
    private LocalTime t1;
    private int duration;
    private List<Weightlifting> exerciseList;


    @Before
    public void setUp() {
        d1 = LocalDate.of(2021,4,20); // format is 2021-04-20
        t1 = LocalTime.of(10,14); // format is 10:14
        exerType = "Weightlifting";
        sets = 3;
        cal = 400;
        duration = 10;
        exerciseList = new ArrayList<Weightlifting>();

        w1 = new WeightliftingWorkout(d1.toString(), t1.toString(), duration, cal, exerciseList);
        // exercise args
        w2 = new WeightliftingWorkout(d1, t1, duration, cal, exerciseList);
    }

    @Test
    public void testExerciseConstructor() {
        assertEquals("Test exerciseType", exerType, w1.getExerciseType());
        assertEquals("Test completedDate", d1.toString(), w1.getCompletedDate());
        assertEquals("Test duration", t1.toString(), w1.getCompletedTime());
        assertEquals("Test caloriesBurned", cal, w1.getCaloriesBurned());
        assertEquals("Test exerciseList", exerciseList, w1.getExerciseList());
        assertEquals("Test exerciseList", duration, w1.getDuration());

        assertEquals("Test exerciseType", exerType, w2.getExerciseType());
        assertEquals("Test completedDate", d1.toString(), w2.getCompletedDate());
        assertEquals("Test completedTime", t1.toString(), w2.getCompletedTime());
        assertEquals("Test duration", duration, w2.getDuration());
        assertEquals("Test caloriesBurned", cal, w2.getCaloriesBurned());
        assertEquals("Test exerciseList", exerciseList, w2.getExerciseList());
    }

    @Test
    public void testSetter() {
        assertEquals("Test exerciseList", exerciseList, w1.getExerciseList());

        exerciseList.add(new Weightlifting("bench", 2, 5, 100));
        w1.setExerciseList(exerciseList);

        assertEquals("Test exerciseList", exerciseList, w1.getExerciseList());
        assertEquals("Test exerciseList", "bench", w1.getExerciseList().get(0).getExerciseName());
        assertEquals("Test exerciseList", 2, w1.getExerciseList().get(0).getSets());

    }
}
