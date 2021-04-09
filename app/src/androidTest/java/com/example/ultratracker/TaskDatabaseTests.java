package com.example.ultratracker;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // Needed to test order of the keys
@RunWith(AndroidJUnit4.class)
public class TaskDatabaseTests {


    private Task tt, tt2, tt3, tt4;
    private LocalDate d1, d2, d3, d4;
    private LocalDate a1;
    private LocalTime t1;
    private TaskDatabaseHelper db;
    private Context appContext;
    private List<Task> list;

    @Before
    public void setUp() throws Exception {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = new TaskDatabaseHelper(appContext);


        d1 = LocalDate.of(2021,4,20); // format is 2021-04-20
        d2 = LocalDate.of(2021,5,21); // format is 2021-04-20
        d3 = LocalDate.of(2021,6,22); // format is 2021-04-20
        d4 = LocalDate.of(2021,7,23); // format is 2021-04-20

        a1 = LocalDate.of(2021,4,19); // format is 2021-04-19
        t1 = LocalTime.of(10,14); // format is 10:14

        tt = new Task("Test 1", a1, d1, t1, "Creation", 1, false);
        tt2 = new Task("Test 2", a1, d2, t1, "Creation", 1, false);
        tt3 = new Task("Test 3", a1, d3, t1, "Creation", 1, false);
        tt4 = new Task("Test 4", a1, d4, t1, "Creation", 1, false);

        list = null;
    }


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.ultratracker", appContext.getPackageName());
    }

    @Test
    public void addATaskToDataBaseAndCheck() {
        db.addOne(tt);
        List<Task> list =  db.getAll();
        assertEquals("Test 1", list.get(0).getName());

    }

    @Test
    public void addMultiToDataBaseAndCheck() {
        db.addOne(tt);
        db.addOne(tt2);
        db.addOne(tt3);
        db.addOne(tt4);
        List<Task> list =  db.getAll();
        assertEquals("Test 1", list.get(0).getName());
        assertEquals("Test 2", list.get(1).getName());
        assertEquals("Test 3", list.get(2).getName());
        assertEquals("Test 4", list.get(3).getName());
    }


    @Test
    public void deleteOne() {
        db.addOne(tt);
        db.addOne(tt2);
        list =  db.getAll();
        assertEquals(2, list.size());
        assertEquals("Test 1", list.get(0).getName());
        assertEquals("Test 2", list.get(1).getName());
        db.deleteTask(tt);
        list = db.getAll();
        assertEquals(1, list.size());
        assertEquals("Test 2", list.get(0).getName());
    }


    @Test
    public void deleteMany() {
        db.addOne(tt);
        db.addOne(tt2);
        db.addOne(tt3);
        db.addOne(tt4);
        list =  db.getAll();
        assertEquals(4, list.size());
        assertEquals("Test 1", list.get(0).getName());
        assertEquals("Test 2", list.get(1).getName());
        assertEquals("Test 3", list.get(2).getName());
        assertEquals("Test 4", list.get(3).getName());
        db.deleteTask(tt);
        db.deleteTask(tt2);
        db.deleteTask(tt3);

        list = db.getAll();
        assertEquals(1, list.size());
        assertEquals("Test 4", list.get(0).getName());

    }

// getbydate is gone
    @Test
    public void testGetByDate() {
        db.addOne(tt);
        db.addOne(tt2);
        db.addOne(tt3);
        db.addOne(tt4);
        list =  db.getAll();
        assertEquals(4, list.size());
        assertEquals("Test 1", list.get(0).getName());
        assertEquals("Test 2", list.get(1).getName());
        assertEquals("Test 3", list.get(2).getName());
        assertEquals("Test 4", list.get(3).getName());

        list = db.getByDateAndCompletion(d3.toString(), 0);
        assertEquals(1, list.size());
        assertEquals("Test 3", list.get(0).getName());

    }


    @Test
    public void testModifyComplete() {
        db.addOne(tt);
        list =  db.getAll();
        assertEquals(false, list.get(0).isComplete());
        db.modifyComplete(tt, true);

        list = db.getAll();
        assertEquals(true, list.get(0).isComplete());

    }

    @Test
    public void testEditTask() {
        db.addOne(tt);
        db.addOne(tt2);
        db.addOne(tt3);
        db.addOne(tt4);
        list =  db.getAll();

        // Test default description
        assertEquals(4, list.size());
        assertEquals("Creation", list.get(0).getDescription());
        assertEquals("Creation", list.get(1).getDescription());
        assertEquals("Creation", list.get(2).getDescription());
        assertEquals("Creation", list.get(3).getDescription());

        // Change the description and call the method to update
        tt.setDescription("Aliens");
        tt2.setDescription("Do");
        tt3.setDescription("Exist");
        tt4.setDescription("!");
        db.editTask(tt);
        db.editTask(tt2);
        db.editTask(tt3);
        db.editTask(tt4);
        list = db.getAll();

        // Check that we updated
        assertEquals("Aliens", list.get(0).getDescription());
        assertEquals("Do", list.get(1).getDescription());
        assertEquals("Exist", list.get(2).getDescription());
        assertEquals("!", list.get(3).getDescription());

    }


    @Test
    public void testGetByDateCompleted() {
        db.addOne(tt);
        db.addOne(tt2);
        db.addOne(tt3);
        db.addOne(tt4);
        list =  db.getAll();
        assertEquals(4, list.size());
        assertEquals("Test 1", list.get(0).getName());
        assertEquals("Test 2", list.get(1).getName());
        assertEquals("Test 3", list.get(2).getName());
        assertEquals("Test 4", list.get(3).getName());
        db.deleteTask(tt);
        db.deleteTask(tt2);
        db.deleteTask(tt3);

        list = db.getAll();
        assertEquals(1, list.size());
        assertEquals("Test 4", list.get(0).getName());

    }

// method removed
//    @Test
//    public void testSortByComplete() {
//        db.addOne(tt);
//        db.addOne(tt2);
//        db.addOne(tt3);
//        db.addOne(tt4);
//        list =  db.getAll();
//        assertEquals(4, list.size());
//        assertEquals("Test 1", list.get(0).getName());
//        assertEquals("Test 2", list.get(1).getName());
//        assertEquals("Test 3", list.get(2).getName());
//        assertEquals("Test 4", list.get(3).getName());
//
//
//        db.modifyComplete(tt, true);
//        db.modifyComplete(tt4, true);
//
//        list = db.getByDateCompleted(d4.toString());
//
//        assertEquals("Test 4", list.get(0).getName());
//    }


    @Test
    public void testGetTodoList() {
        db.addOne(tt);
        db.addOne(tt2);
        db.addOne(tt3);
        db.addOne(tt4);
        list =  db.getAll();
        assertEquals(4, list.size());
        assertEquals("Test 1", list.get(0).getName());
        assertEquals("Test 2", list.get(1).getName());
        assertEquals("Test 3", list.get(2).getName());
        assertEquals("Test 4", list.get(3).getName());


        db.modifyComplete(tt, true);
        db.modifyComplete(tt4, true);

        list = db.getTodoList();

        assertEquals("Test 2", list.get(0).getName());
        assertEquals("Test 3", list.get(1).getName());
    }


    @Test
    public void noDupesTest() {
        db.addOne(tt);
        db.addOne(tt);

        list =  db.getAll();
        assertEquals(1, list.size());
    }


    @After
    public void tearDown() {
        db.close();
        appContext.deleteDatabase("task.db"); // Gotta reset the db after each test
    }
}