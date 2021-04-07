package com.example.ultratracker;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.when;



@RunWith(MockitoJUnitRunner.class)
public class PlannerTests {

    @Mock
    private Planner planner;

    public TaskDatabaseHelper db;
    private LocalTime testTime;

    @Before
    public void setUp() throws Exception {
         db = Mockito.mock(TaskDatabaseHelper.class);
         planner = new Planner(db);
         testTime = LocalTime.now();
    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void testPlannerConstructor() {
        Planner newPlanner = new Planner(db);
        List<Task>newTasks = new ArrayList<Task>();
        assertEquals(newTasks, newPlanner.getAllTasks());
    }

    @Test
    public void testAddTask(){
        Task t1 = new Task("name", LocalDate.now(), LocalDate.now(), testTime, "test", 1, false);
        when(db.addOne(t1)).thenReturn(true);
        assertEquals(true, planner.addTask(t1));
        Task t2 = new Task("name2", LocalDate.now(), LocalDate.now(), testTime, "test", 1, false);
        when(db.addOne(t2)).thenReturn(false);
        assertEquals(false, planner.addTask(t2));
    }

    @Test
    public void testGetAllTasks() {
        LocalTime testTime = LocalTime.now();
        Task t1 = new Task("name", LocalDate.now(), LocalDate.now(), testTime, "test", 1, false);
        boolean temp = db.addOne(t1);
        when(db.addOne(t1)).thenReturn(true);
        planner.addTask(t1);
        List<Task> l = db.getAll();
        assertEquals(l, planner.getAllTasks());
    }

    @Test
    public void testEditTask(){
        Task t1 = new Task("name", LocalDate.now(), LocalDate.now(), testTime, "test", 1, false);
        when(db.addOne(t1)).thenReturn(true);
        planner.addTask(t1);
        when(db.updateAll(t1)).thenReturn(true);
        assertEquals(true, planner.editTask(t1));
        Task t2 = new Task("name2", LocalDate.now(), LocalDate.now(), testTime, "test", 1, false);
        when(db.addOne(t2)).thenReturn(true);
        planner.addTask(t2);
        when(db.updateAll(t2)).thenReturn(false);
        assertEquals(false, planner.editTask(t2));

    }

    @Test
    public void testRemoveTask(){
        Task t1 = new Task("name", LocalDate.now(), LocalDate.now(), testTime, "test", 1, false);
        when(db.addOne(t1)).thenReturn(true);
        when(db.deleteTask(t1)).thenReturn(true);
        planner.addTask(t1);
        assertEquals(true, planner.removeTask(t1));
        Task t2 = new Task("name2", LocalDate.now(), LocalDate.now(), testTime, "test", 1, false);
        when(db.addOne(t2)).thenReturn(true);
        when(db.deleteTask(t2)).thenReturn(false);
        planner.addTask(t2);
        assertEquals(false, planner.removeTask(t2));
    }

    @Test
    public void testMarkAsComplete(){
        Task t1 = new Task("name", LocalDate.now(), LocalDate.now(), testTime, "test", 1, false);
        planner.addTask(t1);
        when(db.addOne(t1)).thenReturn(true);
        when(db.updateAll(t1)).thenReturn(true);
        planner.addTask(t1);
        assertEquals(true, planner.markAsComplete(t1));
        Task t2 = new Task("name2", LocalDate.now(), LocalDate.now(), testTime, "test", 1, false);
        when(db.addOne(t2)).thenReturn(true);
        when(db.updateAll(t2)).thenReturn(false);
        planner.addTask(t2);
        assertEquals(false, planner.markAsComplete(t2));
    }



}

