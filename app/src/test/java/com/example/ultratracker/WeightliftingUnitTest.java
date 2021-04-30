package com.example.ultratracker;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.time.LocalDate;

import static org.junit.Assert.*;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WeightliftingUnitTest {

    private Weightlifting w1, w2;
    private String name;
    private int set, rep, weight, key;
    private LocalDate d1;

    @Before
    public void setUp() {
        d1 = LocalDate.of(2021, 4, 20); // format is 2021-04-20
        name = "bench press";
        set = 5;
        rep = 10;
        weight = 125;
        key = 5;
        // default Constructor
        w1 = new Weightlifting(name, set, rep, weight, d1);
        w2 = new Weightlifting(name, set, rep, weight, d1.toString(), key);
    }


    @Test
    public void testWeightLiftDefaultConstructor() {
        assertEquals("default constructor name", name, w1.getName());
        assertEquals("default constructor sets", set, w1.getSets());
        assertEquals("default constructor reps", rep, w1.getReps());
        assertEquals("default constructor weight", weight, w1.getWeight());
    }

    @Test
    public void testWeightLiftConstructor() {
        assertEquals("constructor name", name, w2.getName());
        assertEquals("constructor sets", set, w2.getSets());
        assertEquals("constructor reps", rep, w2.getReps());
        assertEquals("constructor weight", weight, w2.getWeight());
    }

    @Test
    public void testWeightLiftSetter(){
        w1.setName(name);
        w1.setReps(rep);
        w1.setSets(set);
        w1.setWeight(weight);
        assertEquals("default constructor name", name, w1.getName());
        assertEquals("default constructor sets", set, w1.getSets());
        assertEquals("default constructor reps", rep, w1.getReps());
        assertEquals("default constructor weight", weight, w1.getWeight());
    }

}
