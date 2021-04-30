package com.example.ultratracker;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WorkoutUnitTest {

    private LocalDate d1;
    private String wName1, wName2;
    private List<Weightlifting> lw1, lw2;
    private Weightlifting w1, w2, w3, w4;
    private int s1, s2, s3, s4, r1, r2, r3, r4;
    private int weight1, weight2, weight3, weight4;
    private String exName1, exName2, exName3, exName4;
    private Workout wo1, wo2;
    private int key;

    @Before
    public void setup() throws Exception {
        d1 = LocalDate.of(2021,4,20); // format is 2021-04-20

        wName1 = "Workout1"; wName2 = "Workout2";
        s1 = 1; s2 = 2; s3 = 3; s4 = 4;
        r1 = 10; r2 = 20; r3 = 30; r4 = 40;
        weight1 = 100; weight2 = 200; weight3 = 300; weight4 = 400;
        exName1 = "Bench"; exName2 = "Squat"; exName3 = "Press"; exName4 = "Curl";
        key = 5;

        w1 = new Weightlifting(exName1,s1,r1,weight1,d1);
        w2 = new Weightlifting(exName2,s2,r2,weight2,d1);
        w3 = new Weightlifting(exName3,s3,r3,weight3,d1);
        w4 = new Weightlifting(exName4,s4,r4,weight4,d1);

        lw1 = new ArrayList<Weightlifting>();
        lw2 = new ArrayList<Weightlifting>();

        lw1.add(w1);
        lw1.add(w2);

        lw2.add(w3);
        lw2.add(w4);

        // no key arg
        wo1 = new Workout(wName1, lw1, d1);
        // with key arg
        wo2 = new Workout(wName2, lw2, d1.toString(), key);
    }

    @Test
    public void testConstructors() {
        assertEquals("Test no key constructor name", wName1, wo1.getName());
        assertEquals("Test no key constructor date", d1.toString(), wo1.getDate());
        assertEquals("Test no key constructor name", lw1.get(0).getName(), wo1.getLiftList().get(0).getName());
        assertEquals("Test no key constructor name", lw1.get(1).getName(), wo1.getLiftList().get(1).getName());assertEquals("Test no key constructor name", wName1, wo1.getName());

        assertEquals("Test key constructor name", wName2, wo2.getName());
        assertEquals("Test key constructor date", d1.toString(), wo2.getDate());
        assertEquals("Test key constructor name", lw2.get(0).getName(), wo2.getLiftList().get(0).getName());
        assertEquals("Test key constructor name", lw2.get(1).getName(), wo2.getLiftList().get(1).getName());
    }

    @Test
    public void testSetters() {
        assertEquals("Test key constructor name", wName2, wo2.getName());
        assertEquals("Test key constructor date", d1.toString(), wo2.getDate());
        assertEquals("Test key constructor name", lw2.get(0).getName(), wo2.getLiftList().get(0).getName());
        assertEquals("Test key constructor name", lw2.get(1).getName(), wo2.getLiftList().get(1).getName());

        LocalDate d2 = LocalDate.of(2021,7,23);

        wo2.setName(wName1);
        wo2.setKey(0);
        wo2.setLiftList(lw1);
        wo2.setDate(d2);

        assertEquals("Test key constructor name", wName1, wo2.getName());
        assertEquals("Test key constructor key", 0, wo2.getKey());
        assertEquals("Test key constructor date", d2.toString(), wo2.getDate());
        assertEquals("Test key constructor name", lw1.get(0).getName(), wo2.getLiftList().get(0).getName());
        assertEquals("Test key constructor name", lw1.get(1).getName(), wo2.getLiftList().get(1).getName());
    }


}
