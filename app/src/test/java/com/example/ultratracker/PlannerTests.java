package com.example.ultratracker;

import androidx.test.InstrumentationRegistry;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.*;
public class PlannerTests {

    private Task task;
    private TaskDatabaseHelper db;

    @Before
    public void setUp() throws Exception {
        db = null;
        db = new TaskDatabaseHelper(InstrumentationRegistry.getTargetContext());
    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }


    @Test
    public void addTask() {
        Task t1 = new Task("name", LocalDate.now(), LocalDate.now(), LocalTime.now(), "test", 1, false);
        db.addOne(t1);
        List<Task> l = db.getAll();

    }
}
