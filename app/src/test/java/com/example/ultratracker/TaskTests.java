package com.example.ultratracker;

import org.junit.FixMethodOrder;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runners.MethodSorters;

import java.time.LocalDate;
import java.time.LocalDateTime;


@FixMethodOrder(MethodSorters.DEFAULT) // Needed to test order of the keys
public class TaskTests {

    private Task task;

    @Before
    public void setUp() throws Exception {
        task = new Task();
    }

    
    @Test
    public void testCreateTaskWithEmptyConstructor() {
        assertEquals(0, task.getKey());
    }

    /*
    Create a set of Task and check that the ID keys are correct and the Text field is correct
     */
    @Test
    public void testCreateTaskWithConstructor() {
        LocalDate d1 = LocalDate.now();
        LocalDateTime time = LocalDateTime.now();
        Task t1 = new Task("Test 1", d1, time, "Creation", 1, false);

        // check that each task created as a unique key
        assertEquals(1, t1.getKey()); // 1 since we have before each


        // check that each testTask is correct
        assertEquals("Test 1", t1.getName());
        assertEquals(d1.toString(), t1.getDueDate().toString());
        assertEquals(time.toString(), t1.getDueTime().toString());
        assertEquals(1, t1.getPriority());
        assertEquals(false, t1.isComplete());
    }

    @Test
    public void testSetTaskText() {
        task.setDescription("setTaskTextTest");
        assertEquals("setTaskTextTest", task.getDescription());
    }

    @Test
    public void testEditTask() {
        task.setDescription("setTaskTextTest");
        assertEquals("setTaskTextTest", task.getDescription());
        task.setDescription("reset");
        assertEquals("reset", task.getDescription());
    }


    // Can toggle completion
    @Test
    public void testMarkAsComplete() {
        assertEquals(false, task.isComplete());
        task.setComplete(true);
        assertEquals(true, task.isComplete());
        task.setComplete(false);
        assertEquals(false, task.isComplete());
    }


}
