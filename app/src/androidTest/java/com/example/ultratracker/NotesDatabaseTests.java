package com.example.ultratracker;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) // Needed to test order of the keys
@RunWith(AndroidJUnit4.class)
public class NotesDatabaseTests {

    private NotesDatabaseHelper db;
    private Context appContext;
    private Note n1, n2, n3, n4;
    private Reminder r1, r2, r3, r4;
    private String name1, name2, name3, name4;
    private LocalDate d1, d2, d3, d4;

    private LocalTime t1, t2, t3, t4;
    private String des1, des2, des3, des4;
    private List<Note> nl1;
    private List<Reminder> rl1;


    @Before
    public void setUp() throws Exception {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = new NotesDatabaseHelper(appContext);

        name1 = "note1"; name2 = "note2"; name3 = "note3";  name4 = "note4";
        d1 = LocalDate.of(2021, 4, 20); // format is 2021-04-20
        d2 = LocalDate.of(2021, 5, 21); // format is 2021-04-20
        d3 = LocalDate.of(2021, 6, 22); // format is 2021-04-20
        d4 = LocalDate.of(2021, 7, 23); // format is 2021-04-20


        t1 = LocalTime.of(10,14); // format is 10:14
        t2 = LocalTime.of(11,14); // format is 10:14
        t3 = LocalTime.of(12,14); // format is 10:14
        t4 = LocalTime.of(1,14); // format is 10:14


        des1 = "des1"; des2 = "des2"; des3 = "des3";  des4 = "des4";

        n1 = new Note(name1, d1, des1);
        n2 = new Note(name2, d2, des2);
        n3 = new Note(name3, d3, des3);
        n4 = new Note(name4, d4, des4);

        r1 = new Reminder(name1, d1, t1, des1);
        r2 = new Reminder(name2, d2, t2, des2);
        r3 = new Reminder(name3, d3, t3, des3);
        r4 = new Reminder(name4, d4, t4, des4);

    }

    @Test
    public void addANote() {
        tearDown();
        db.addNote(n1);
        nl1 = db.getAllNotes();
        assertEquals("Check size", 1,  nl1.size());
        assertEquals("Test Name is correct", n1.getName(), nl1.get(0).getName());
    }

    @Test
    public void addManyNote() {
        tearDown();
        db.addNote(n1);
        db.addNote(n4);
        db.addNote(n3);
        db.addNote(n2);

        nl1 = db.getAllNotes();
        assertEquals("Check size", 4,  nl1.size());
        assertEquals("Verify order", n1.getName(),  nl1.get(0).getName());
        assertEquals("Verify order", n2.getName(),  nl1.get(1).getName());
        assertEquals("Verify order", n3.getName(),  nl1.get(2).getName());
        assertEquals("Verify order", n4.getName(),  nl1.get(3).getName());
    }

    @Test
    public void editNote() {
        db.addNote(n1);

        nl1 = db.getAllNotes();
        assertEquals("Verify original", n1.getName(),  nl1.get(0).getName());
        assertEquals("Verify original", n1.getDate(),  nl1.get(0).getDate());
        assertEquals("Verify original", n1.getDesc(),  nl1.get(0).getDesc());

        Note newN = new Note("Edit", d2.toString(), "Edit", n1.getKey());
        db.editNote(newN);
        nl1 = db.getAllNotes();
        assertEquals("Verify original", newN.getName(),  nl1.get(0).getName());
        assertEquals("Verify original", d2.toString(),  nl1.get(0).getDate());
        assertEquals("Verify original", newN.getDesc(),  nl1.get(0).getDesc());
    }

    @Test
    public void editNoneNote() {
        assertFalse(db.editNote(n1));
    }

    @Test
    public void deleteOneNote() {
        db.addNote(n1);
        nl1 = db.getAllNotes();
        assertEquals("Verify original", n1.getName(),  nl1.get(0).getName());

        db.deleteNote(n1);
        nl1 = db.getAllNotes();
        assertEquals("Verify none", 0, nl1.size());
    }

    @Test
    public void deleteNoneNote() {
        assertFalse(db.deleteNote(n1));
    }

    @Test
    public void deleteManyNote() {
        db.addNote(n1);
        db.addNote(n2);
        db.addNote(n3);
        db.addNote(n4);
        nl1 = db.getAllNotes();
        assertEquals("Verify original size", 4,  nl1.size());

        db.deleteNote(n1);
        nl1 = db.getAllNotes();
        assertEquals("Verify new size", 3,  nl1.size());
        for(int i = 0; i < nl1.size(); i++) {
            if(nl1.get(i).getName().equals(name1)) Assert.fail();
        }

        db.deleteNote(n4);
        nl1 = db.getAllNotes();
        assertEquals("Verify new size", 2,  nl1.size());
        for(int i = 0; i < nl1.size(); i++) {
            if(nl1.get(i).getName().equals(name4)) Assert.fail();
        }
    }

    @Test
    public void addAReminder() {
        tearDown();
        db.addReminder(r1);
        rl1 = db.getAllReminders();
        assertEquals("Check size", 1,  rl1.size());
        assertEquals("Test Name is correct", r1.getName(), rl1.get(0).getName());
    }

    @Test
    public void addManyReminder() {
        tearDown();
        db.addReminder(r1);
        db.addReminder(r2);
        db.addReminder(r3);
        db.addReminder(r4);

        rl1 = db.getAllReminders();
        assertEquals("Check size", 4,  rl1.size());
    }

    @Test
    public void editReminder() {
        db.addReminder(r1);

        rl1 = db.getAllReminders();
        assertEquals("Verify original", r1.getName(),  rl1.get(0).getName());
        assertEquals("Verify original", r1.getDate(),  rl1.get(0).getDate());
        assertEquals("Verify original", r1.getTime(),  rl1.get(0).getTime());
        assertEquals("Verify original", r1.getDesc(),  rl1.get(0).getDesc());

        Reminder newN = new Reminder("Edit", d2.toString(), t1.toString(), "Edit", r1.getKey());

        db.editReminder(newN);
        rl1 = db.getAllReminders();
        assertEquals("Verify original", newN.getName(),  rl1.get(0).getName());
        assertEquals("Verify original", d2.toString(),  rl1.get(0).getDate());
        assertEquals("Verify original", t1.toString(),  rl1.get(0).getTime());
        assertEquals("Verify original", newN.getDesc(),  rl1.get(0).getDesc());
    }

    @Test
    public void editNoneReminder() {
        assertFalse(db.editReminder(r1));
    }

    @Test
    public void deleteOneReminder() {
        db.addReminder(r1);
        rl1 = db.getAllReminders();
        assertEquals("Verify original", r1.getName(),  rl1.get(0).getName());

        db.deleteReminder(r1);
        rl1 = db.getAllReminders();
        assertEquals("Verify none", 0, rl1.size());
    }

    @Test
    public void deleteNoneReminder() {
        assertFalse(db.deleteReminder(r1));
    }

    @Test
    public void deleteManyReminder() {
        db.addReminder(r1);
        db.addReminder(r2);
        db.addReminder(r3);
        db.addReminder(r4);

        rl1 = db.getAllReminders();
        assertEquals("Verify original size", 4,  rl1.size());

        db.deleteReminder(r1);
        rl1 = db.getAllReminders();
        assertEquals("Verify new size", 3,  rl1.size());
        for(int i = 0; i < rl1.size(); i++) {
            if(rl1.get(i).getName().equals(name1)) Assert.fail();
        }

        db.deleteReminder(r4);
        rl1 = db.getAllReminders();
        assertEquals("Verify new size", 2,  rl1.size());
        for(int i = 0; i < rl1.size(); i++) {
            if(rl1.get(i).getName().equals(name4)) Assert.fail();
        }
    }

    @Test
    public void testGetReminderByDate() {
        db.addReminder(r4);
        db.addReminder(r1);
        db.addReminder(r2);
        db.addReminder(r3);

        rl1 = db.getAllReminders();
        assertEquals("Verify order", r1.getName(),  rl1.get(0).getName());
        assertEquals("Verify order", r2.getName(),  rl1.get(1).getName());
        assertEquals("Verify order", r3.getName(),  rl1.get(2).getName());
        assertEquals("Verify order", r4.getName(),  rl1.get(3).getName());
    }


    @Test
    public void testGetAllRemindersByDate () {
        db.addReminder(r4);
        db.addReminder(r1);
        db.addReminder(r2);
        db.addReminder(r3);

        rl1 = db.getAllRemindersByDate(d3.toString());
        assertEquals("check size", 1, rl1.size());
        assertEquals("check value", name3, rl1.get(0).getName());

        r4.setDate(d3);
        r2.setDate(d3);
        db.editReminder(r4);
        db.editReminder(r2);
        rl1 = db.getAllRemindersByDate(d3.toString());

        assertEquals("check size", 3, rl1.size());
        for(int i = 0; i < rl1.size(); i++){
            if(!(rl1.get(i).getDate().equals(d3.toString()))) Assert.fail();
        }

    }


    @After
    public void tearDown() {
        db.close();
        appContext.deleteDatabase("notes.db");

    }
}
