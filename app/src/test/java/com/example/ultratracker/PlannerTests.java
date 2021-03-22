package com.example.ultratracker;
import org.junit.Test;
import static org.junit.Assert.*;
public class PlannerTests {

    /*
    Create a set of Task and check that the ID keys are correct and the Text field is correct
     */
    @Test
    public void testCreateTask() {
        Task t1 = new Task("Test 1");
        Task t2 = new Task("Test 2");
        Task t3 = new Task("Test 3");
        Task t4 = new Task("Test 4");

        // check that each task created as a unique key
        assertEquals(t1.getKey(), 0);
        assertEquals(t2.getKey(), 1);
        assertEquals(t3.getKey(), 2);
        assertEquals(t4.getKey(), 3);

        // check that each testTask is correct
        assertEquals(t1.getTaskText(), "Test 1");
        assertEquals(t2.getTaskText(), "Test 2");
        assertEquals(t3.getTaskText(), "Test 3");
        assertEquals(t4.getTaskText(), "Test 4");
    }

    @Test
    public void testSetTaskText() {
        Task t1 = new Task(null);
        t1.setTaskText("setTaskTextTest");
        assertEquals(t1.getTaskText(),  "setTaskTextTest");
    }

    @Test
    public void testCreatePlanner() {
        Planner p1 = new Planner();
        Task t1 = new Task("Test 1");
        Task t2 = new Task("Test 2");
        Task t3 = new Task("Test 3");
        Task t4 = new Task("Test 4");

        
    }
    @Test
    public void testEditTask() {

    }
    @Test
    public void testMarkAsComplete() {

    }

}
