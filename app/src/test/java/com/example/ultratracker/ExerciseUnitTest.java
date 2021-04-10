package com.example.ultratracker;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExerciseUnitTest {

    private Exercise e1, e2;

    private LocalTime t1;
    private LocalDate d1;
    private int duration, cal;
    private String exerciseType1, exerciseType2;


    @Before
    public void setUp(){
        d1 = LocalDate.of(2021,4,20); // format is 2021-04-20
        t1 = LocalTime.of(10,14); // format is 10:14
        exerciseType1 = "Run"; exerciseType2 = "Swim";
        duration = 10;
        cal = 300;
        e1 = new Exercise(exerciseType1, d1, t1, duration, cal);
        e2 = new Exercise(exerciseType2, d1.toString(), t1.toString(), duration, cal);
    }

    @Test
    public void testConstructors() {
        assertEquals("Test type on constructor", exerciseType1, e1.getExerciseType());
        assertEquals("Test date on constructor", d1.toString(), e1.getCompletedDate());
        assertEquals("Test time on constructor", t1.toString(), e1.getCompletedTime());
        assertEquals("Test duration on constructor", duration, e1.getDuration());
        assertEquals("Test calories on constructor", cal, e1.getCaloriesBurned());
        assertTrue("Test range of key",1 <= e1.getKey() && e1.getKey() <= 100000);


        assertEquals("Test type on constructor", exerciseType2, e2.getExerciseType());
        assertEquals("Test date on constructor", d1.toString(), e2.getCompletedDate());
        assertEquals("Test time on constructor", t1.toString(), e2.getCompletedTime());
        assertEquals("Test duration on constructor", duration, e2.getDuration());
        assertEquals("Test calories on constructor", cal, e2.getCaloriesBurned());
        assertTrue("Test range of key",1 <= e2.getKey() && e2.getKey() <= 100000);

    }

    @Test
    public void testSetters() {
        assertEquals("Test type on constructor", exerciseType1, e1.getExerciseType());
        assertEquals("Test date on constructor", d1.toString(), e1.getCompletedDate());
        assertEquals("Test time on constructor", t1.toString(), e1.getCompletedTime());
        assertEquals("Test duration on constructor", duration, e1.getDuration());
        assertEquals("Test calories on constructor", cal, e1.getCaloriesBurned());
        assertTrue("Test range of key",1 <= e1.getKey() && e1.getKey() <= 100000);

        e1.setKey(0);
        assertEquals("Test key setter", 0, e1.getKey());

        e1.setCaloriesBurned(100);
        assertEquals("Test calorie setter", 100, e1.getCaloriesBurned());

        e1.setCompletedTime(LocalTime.parse("04:20"));
        assertEquals("Test time setter", "04:20", e1.getCompletedTime());

        e1.setCompletedDate(LocalDate.parse("2021-04-22"));
        assertEquals("Test date setter", "2021-04-22", e1.getCompletedDate());

        e1.setExerciseType("Death");
        assertEquals("Test ExerciseType setter", "Death", e1.getExerciseType());

        e1.setDuration(5);
        assertEquals("Test duration setter", 5, e1.getDuration());

    }

}
