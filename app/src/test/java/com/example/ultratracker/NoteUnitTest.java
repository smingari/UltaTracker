package com.example.ultratracker;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NoteUnitTest {

    private Note n1, n2;
    private LocalDate d1;
    private String name1, name2, des1, des2;
    private int key;

    @Before
    public void setUp(){
        d1 = LocalDate.of(2021,4,20); // format is 2021-04-20
        name1 = "note 1"; name2 = "note 2";
        des1 = "des1"; des2 = "des2";
        key = 5;
        n1 = new Note(name1, d1, des1);
        n2 = new Note(name2, d1.toString(), des2, key);

    }

    @Test
    public void testConstructors() {
        assertEquals("Test name on constructor", name1, n1.getName());
        assertEquals("Test date on constructor", d1.toString(), n1.getDate());
        assertEquals("Test description on constructor", des1, n1.getDesc());
        assertTrue("Test range of key",1 <= n1.getKey() && n1.getKey() <= 100000);


        assertEquals("Test name on string constructor", name2, n2.getName());
        assertEquals("Test date on string constructor", d1.toString(), n2.getDate());
        assertEquals("Test description on string constructor", des2, n2.getDesc());
        assertEquals("Test key on constructor", key, n2.getKey());
    }

    @Test
    public void testSetters() {
        assertEquals("Test name on string constructor", name2, n2.getName());
        assertEquals("Test date on string constructor", d1.toString(), n2.getDate());
        assertEquals("Test description on string constructor", des2, n2.getDesc());

//        n2.setKey(0);
//        assertEquals("Test key setter", 0, n2.getKey());

        n2.setName("Test");
        assertEquals("Test name setter", "Test", n2.getName());

        n2.setDate(LocalDate.parse("2021-04-22"));
        assertEquals("Test date setter", "2021-04-22", n2.getDate());

        n2.setDesc("DesTest");
        assertEquals("Test des setter", "DesTest", n2.getDesc());

    }
}
