package com.example.ultratracker;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class WeightUnitTest {

    private Weight w1, w2;
    private LocalDate d1;
    private double weight1, weight2;

    @Before
    public void setup() throws Exception {
        d1 = LocalDate.of(2021, 4, 20); // format is 2021-04-20
        weight1 = 150.0;
        weight2 = 175.0;

        w1 = new Weight(weight1, d1);
        w2 = new Weight(weight2, d1.toString());
    }

    @Test
    public void testConstructors() {
        assertEquals("Test constructor name", weight1, w1.getWeight(), 0.01);
        assertEquals("Test constructor date", d1.toString(), w1.getDate());

        assertEquals("Test constructor name", weight2, w2.getWeight(), 0.01);
        assertEquals("Test constructor date", d1.toString(), w2.getDate());
    }

    @Test
    public void testSetters() {
        assertEquals("Test constructor name", weight1, w1.getWeight(), 0.01);
        assertEquals("Test constructor date", d1.toString(), w1.getDate());
        LocalDate d2 = LocalDate.of(2021,7,23);
        w1.setWeight(weight2);
        w1.setDate(d2);

        assertEquals("Test constructor name", weight2, w1.getWeight(), 0.01);
        assertEquals("Test constructor date", d2.toString(), w1.getDate());
    }
}
