package com.example.ultratracker;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WeightliftingUnitTest {

    private Weightlifting w1, w2;
    private String name;
    private int set, rep, weight;

    @Before
    public void setUp() {
        name = "bench press";
        set = 5;
        rep = 10;
        weight = 125;

        // default Constructor
        w1 = new Weightlifting();
        w2 = new Weightlifting(name, set, rep, weight);
    }


    @Test
    public void testWeightLiftDefaultConstructor() {
        assertEquals("default constructor name", "", w1.getExerciseName());
        assertEquals("default constructor sets", 0, w1.getSets());
        assertEquals("default constructor reps", 0, w1.getReps());
        assertEquals("default constructor weight", 0, w1.getWeight());
    }

    @Test
    public void testWeightLiftConstructor() {
        assertEquals("constructor name", name, w2.getExerciseName());
        assertEquals("constructor sets", set, w2.getSets());
        assertEquals("constructor reps", rep, w2.getReps());
        assertEquals("constructor weight", weight, w2.getWeight());
    }

    @Test
    public void testWeightLiftSetter(){
        w1.setExerciseName(name);
        w1.setReps(rep);
        w1.setSets(set);
        w1.setWeight(weight);
        assertEquals("default constructor name", name, w1.getExerciseName());
        assertEquals("default constructor sets", set, w1.getSets());
        assertEquals("default constructor reps", rep, w1.getReps());
        assertEquals("default constructor weight", weight, w1.getWeight());
    }

}
