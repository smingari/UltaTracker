package com.example.ultratracker;

import org.junit.FixMethodOrder;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runners.MethodSorters;

import java.time.LocalDate;
import java.time.LocalTime;


@FixMethodOrder(MethodSorters.NAME_ASCENDING) // Needed to test order of the keys
public class TaskTests {

    private Task task;
    private LocalDate d1;
    private LocalDate a1;
    private LocalTime t1;

    @Before
    public void setUp() throws Exception {
        task = new Task();
        d1 = LocalDate.of(2021,4,20); // format is 2021-04-20
        a1 = LocalDate.of(2021,4,19); // format is 2021-04-19
        t1 = LocalTime.of(10,14); // format is 10:14
    }


    // WILL ALWAYS FAIL SINCE KEY IS NOW RANDOM
    // Name Alpha to run first
//    @Test
//    public void testAlphaCreateTaskWithEmptyConstructor() {
//        //assertEquals(0, task.getKey());


    /*
    Create a set of Task and check that the ID keys are correct and the Text field is correct
     */
    @Test
    public void testCreateTaskWithConstructor() {
        Task tt = new Task("Test 1", a1, d1, t1, "Creation", 1, false);

        // check that each task created as a unique key

        //assertEquals(2, tt.getKey()); // 1 since we have before each



        // check that each testTask is correct
        assertEquals("Test 1", tt.getName());
        assertEquals(a1.toString(), tt.getAssignedDate());
        assertEquals(d1.toString(), tt.getDueDate());
        assertEquals(t1.toString(), tt.getDueTime());
        assertEquals(1, tt.getPriority());
        assertEquals(false, tt.isComplete());
    }

    @Test
    public void testSetTaskText() {
        task.setDescription("setTaskTextTest");
        assertEquals("setTaskTextTest", task.getDescription());
    }

    @Test
    public void testSetName() {
        task.setName("testSetName");
        assertEquals("testSetName", task.getName());
    }

    @Test
    public void testEditTask() {
        task.setDescription("setTaskTextTest");
        assertEquals("setTaskTextTest", task.getDescription());
        task.setDescription("reset");
        assertEquals("reset", task.getDescription());
    }


    @Test
    public void testSetDueDate() {
        task.setDueDate(d1);
        assertEquals(d1.toString(), task.getDueDate());
    }

    @Test
    public void testSetDueTime() {
        task.setDueTime(t1);
        assertEquals(t1.toString(), task.getDueTime());
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

    @Test
    public void testSetPriority() {
        task.setPriority(0);
        assertEquals(0, task.getPriority());
        task.setPriority(5);
        assertEquals(5, task.getPriority());
    }
}
