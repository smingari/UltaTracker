package com.example.ultratracker;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReminderUnitTest {

    private Reminder r1, r2;
    private LocalDate d1;
    private LocalTime t1;
    private String name1, name2, des1, des2;
    private int key;

    @Before
    public void setUp(){
        d1 = LocalDate.of(2021,4,20); // format is 2021-04-20
        t1 = LocalTime.of(10,14); // format is 10:14

        name1 = "note 1"; name2 = "note 2";
        des1 = "des1"; des2 = "des2";
        key = 5;
        r1 = new Reminder(name1, d1, t1, des1);
        r2 = new Reminder(name2, d1.toString(), t1.toString(), des2, key);

    }

    @Test
    public void testConstructors() {
        assertEquals("Test name on constructor", name1, r1.getName());
        assertEquals("Test date on constructor", d1.toString(), r1.getDate());
        assertEquals("Test description on constructor", des1, r1.getDesc());
        assertTrue("Test range of key",1 <= r1.getKey() && r1.getKey() <= 100000);


        assertEquals("Test name on string constructor", name2, r2.getName());
        assertEquals("Test date on string constructor", d1.toString(), r2.getDate());
        assertEquals("Test description on string constructor", des2, r2.getDesc());
        assertEquals("Test key on constructor", key, r2.getKey());
    }

    @Test
    public void testSetters() {
        assertEquals("Test name on string constructor", name2, r2.getName());
        assertEquals("Test date on string constructor", d1.toString(), r2.getDate());
        assertEquals("Test description on string constructor", des2, r2.getDesc());

//        n2.setKey(0);
//        assertEquals("Test key setter", 0, n2.getKey());

        r2.setName("Test");
        assertEquals("Test name setter", "Test", r2.getName());

        r2.setDate(LocalDate.parse("2021-04-22"));
        assertEquals("Test date setter", "2021-04-22", r2.getDate());

        r2.setDesc("DesTest");
        assertEquals("Test des setter", "DesTest", r2.getDesc());

    }
}
