package com.example.ultratracker;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) // Needed to test order of the keys
@RunWith(AndroidJUnit4.class)
public class FoodDatabaseTests {


    private Food f1, f2, f3, f4;
    private LocalDate d1, d2, d3, d4;
    private FoodDatabaseHelper db;
    private Context appContext;
    private Food[] list;
    private String name1, name2, name3, name4;
    private int cals1, cals2, cals3, cals4;
    private int carbs1, carbs2, carbs3, carbs4;
    private int protein1, protein2, protein3, protein4;
    private int fat1, fat2, fat3, fat4;
    private int fiber1, fiber2, fiber3, fiber4;

    @Before
    public void setUp() throws Exception {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = new FoodDatabaseHelper(appContext);


        d1 = LocalDate.of(2021, 4, 20); // format is 2021-04-20
        d2 = LocalDate.of(2021, 5, 21); // format is 2021-04-20
        d3 = LocalDate.of(2021, 6, 22); // format is 2021-04-20
        d4 = LocalDate.of(2021, 7, 23); // format is 2021-04-20
        name1 = "Chicken"; name2 = "Pasta"; name3 = "Pizza"; name4 = "Vomit";

        cals1 = 300; carbs1 = 600; protein1 = 20; fat1 = 5; fiber1 = 15;
        cals2 = 300; carbs2 = 600; protein2 = 20; fat2 = 5; fiber2 = 15;
        cals3 = 300; carbs3 = 600; protein3 = 20; fat3 = 5; fiber3 = 15;
        cals4 = 300; carbs4 = 600; protein4 = 20; fat4 = 5; fiber4 = 15;

        f1 = new Food(name1, cals1, protein1, carbs1, fat1, fiber1, d1);
        f2 = new Food(name2, cals2, protein2, carbs2, fat2, fiber2, d2);
        f3 = new Food(name3, cals3, protein3, carbs3, fat3, fiber3, d3);
        f4 = new Food(name4, cals4, protein4, carbs4, fat4, fiber4, d4);
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.ultratracker", appContext.getPackageName());
    }

    @Test
    public void addATaskToDataBaseAndCheck() {
        db.addFood(f1);
        list =  db.getAllSorted();
        assertEquals(name1, list[0].getName());

    }

    @Test
    public void addMultiToDataBaseAndCheck() {
        db.addFood(f1);
        db.addFood(f2);
        db.addFood(f3);
        db.addFood(f4);
        list =  db.getAllSorted();
        assertEquals(name1, list[0].getName());
        assertEquals(name2, list[1].getName());
        assertEquals(name3, list[2].getName());
        assertEquals(name4, list[3].getName());
    }


    @Test
    public void deleteEmpty() {
        list =  db.getAllSorted();
        assertNull(list);
        assertFalse(db.deleteFood(f1));
        list = db.getAllSorted();
        assertNull(list);
    }



    @Test
    public void deleteOnly() {
        db.addFood(f1);
        list =  db.getAllSorted();
        assertEquals(1, list.length);
        assertEquals(name1, list[0].getName());
        db.deleteFood(f1);
        list = db.getAllSorted();
        assertNull( list);
    }


    @Test
    public void deleteOne() {
        db.addFood(f1);
        db.addFood(f2);
        list =  db.getAllSorted();
        assertEquals(2, list.length);
        assertEquals(name1, list[0].getName());
        assertEquals(name2, list[1].getName());
        db.deleteFood(f1);
        list = db.getAllSorted();
        assertEquals(1, list.length);
        assertEquals(name2, list[0].getName());
    }

    @Test
    public void deleteMany() {
        db.addFood(f1);
        db.addFood(f3);
        db.addFood(f4);
        db.addFood(f2);
        list =  db.getAllSorted();
        assertEquals(4, list.length);
        assertEquals(name1, list[0].getName());
        assertEquals(name2, list[1].getName());
        assertEquals(name3, list[2].getName());
        assertEquals(name4, list[3].getName());
        db.deleteFood(f1);
        db.deleteFood(f4);
        db.deleteFood(f2);

        list = db.getAllSorted();
        assertEquals(1, list.length);
        assertEquals(name3, list[0].getName());

    }

    @Test
    public void testGetByDate() {
        db.addFood(f1);
        db.addFood(f3);
        db.addFood(f2);
        db.addFood(f4);
        list =  db.getByDate(d3.toString());
        assertEquals(1, list.length);
        assertEquals(name3, list[0].getName());

    }


    @Test
    public void noDupesTest() {
        db.addFood(f1);
        db.addFood(f1);

        list =  db.getAllSorted();
        assertEquals(1, list.length);
    }

    @After
    public void tearDown() {
        db.close();
        appContext.deleteDatabase("food.db"); // Gotta reset the db after each test
    }
}
