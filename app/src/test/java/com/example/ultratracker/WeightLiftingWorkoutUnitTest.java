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
    private List<Weightlifting> exerciseList;


    @Before
    public void setUp() {
        d1 = LocalDate.of(2021,4,20); // format is 2021-04-20
        t1 = LocalTime.of(10,14); // format is 10:14
        exerType = "Weightlifting";
        sets = 3;
        cal = 400;
        exerciseList = new ArrayList<Weightlifting>();

        w1 = new WeightliftingWorkout(d1.toString(), t1.toString(), cal, exerciseList);

        // exercise args
        w2 = new WeightliftingWorkout(d1, t1, cal, exerciseList);
    }

    @Test
    public void testExerciseConstructorNonString() {
        assertEquals("Test exerciseType", exerType, w2.getExerciseType());
        assertEquals("Test completedDate", d1.toString(), w2.getCompletedDate());
        assertEquals("Test duration", t1.toString(), w2.getDuration());
        assertEquals("Test caloriesBurned", cal, w2.getCaloriesBurned());
        assertEquals("Test exerciseList", exerciseList, w2.getExerciseList());
    }

    @Test
    public void testExerciseConstructorString() {
        assertEquals("Test exerciseType", exerType, w1.getExerciseType());
        assertEquals("Test completedDate", d1.toString(), w1.getCompletedDate());
        assertEquals("Test duration", t1.toString(), w1.getDuration());
        assertEquals("Test caloriesBurned", cal, w1.getCaloriesBurned());
        assertEquals("Test exerciseList", exerciseList, w1.getExerciseList());
    }

    @Test
    public void testSetter() {
        exerciseList.add(new Weightlifting("Bench", 5, 10, 125));
        w2.setExerciseList(exerciseList);
        assertEquals("Test exerciseList", exerciseList, w1.getExerciseList());
        assertEquals(1, w1.getExerciseList().size());
        assertEquals("Bench", w1.getExerciseList().get(0).getExerciseName());

    }
}
