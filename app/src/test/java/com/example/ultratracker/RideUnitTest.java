package com.example.ultratracker;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RideUnitTest {

    private double distance1, distance2;
    private double pace1, pace2;
    private LocalTime t1;
    private LocalDate d1;

    private Ride r1, r2;
    private int duration;

    @Before
    public void setup() throws Exception {
        d1 = LocalDate.of(2021,4,20); // format is 2021-04-20
        t1 = LocalTime.of(10,14); // format is 10:14
        distance1 = 10.1;
        distance2 = 10.2;
        duration = 30;
        pace1 = 2.5;
        pace2 = 2.6;

        r1 = new Ride(d1, t1, duration,200,  distance2, pace2);
        r2 = new Ride(d1.toString(), t1.toString(), duration, 200,  distance2, pace2);
    }

    @Test
    public void testConstructors() {
        Ride r2 = new Ride(d1, t1, duration, 200,  distance2, pace2);

        assertEquals("Test Exercise Type", "Ride", r2.getExerciseType());
        assertEquals("Test completed Date", d1.toString(), r2.getCompletedDate());
        assertEquals("Test Duration", t1.toString(), r2.getCompletedTime());
        assertEquals("Test Calories", 200, r2.getCaloriesBurned());
        assertEquals("Test Distance", distance2, r2.getDistance(), 0.01);
        assertEquals("Test Pace", pace2, r2.getPace(), 0.01);

    }
    @Test
    public void testRideSetter() {
        // Test the setters
        r1.setDistance(12.1);
        r1.setPace(5.5);

        assertEquals("Test setDistance", 12.1, r1.getDistance(), 0.01);
        assertEquals("Test setPace", 5.5, r1.getPace(), 0.01);
    }
}