package com.example.ultratracker;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.time.LocalDate;

import static org.junit.Assert.*;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WeightliftingUnitTest {

    private Weightlifting w1, w2, w3, w4;
    private String name, woName;
    private int set, rep, weight, key, woKey;
    private LocalDate d1;

    @Before
    public void setUp() {
        d1 = LocalDate.of(2021, 4, 20); // format is 2021-04-20
        name = "bench press";
        set = 5;
        rep = 10;
        weight = 125;
        key = 5;
        woKey = 10;
        woName = "WoName";
        // default Constructor
        w1 = new Weightlifting(name, set, rep, weight, d1);
        w2 = new Weightlifting(name, set, rep, weight, d1.toString(), key);
        w3 = new Weightlifting(name, set, rep, weight, woName, d1);
        w4 = new Weightlifting(name, set, rep, weight, woName, d1.toString(), key, woKey);
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
        assertEquals("constructor key", key, w2.getKey());

    }

    @Test
    public void testWeightLiftConstructorWo() {
        assertEquals("constructor name", name, w3.getName());
        assertEquals("constructor sets", set, w3.getSets());
        assertEquals("constructor reps", rep, w3.getReps());
        assertEquals("constructor weight", weight, w3.getWeight());
        assertEquals("constructor woName", woName, w3.getWorkoutName());
    }

    @Test
    public void testWeightLiftConstructorWoKey() {
        assertEquals("constructor name", name, w4.getName());
        assertEquals("constructor sets", set, w4.getSets());
        assertEquals("constructor reps", rep, w4.getReps());
        assertEquals("constructor weight", weight, w4.getWeight());
        assertEquals("constructor key", key, w4.getKey());
        assertEquals("constructor woName", woName, w4.getWorkoutName());
        assertEquals("constructor woName", woKey, w4.getWorkoutKey());
    }

    @Test
    public void testWeightLiftSetter(){
        w1.setName(name);
        w1.setReps(rep);
        w1.setSets(set);
        w1.setWeight(weight);
        w1.setKey(0);
        w3.setWorkoutKey(5);
        w3.setWorkoutName("Test");
        LocalDate d2 = LocalDate.of(2021, 7, 23);
        w1.setDate(d2);
        assertEquals("setter name", name, w1.getName());
        assertEquals("setter sets", set, w1.getSets());
        assertEquals("setter reps", rep, w1.getReps());
        assertEquals("setter weight", weight, w1.getWeight());
        assertEquals("setter key", 0, w1.getKey());
        assertEquals("setter Wokey", 5, w3.getWorkoutKey());
        assertEquals("setter woName", "Test", w3.getWorkoutName());
        assertEquals("setter date", d2.toString(), w1.getDate());


    }

}
