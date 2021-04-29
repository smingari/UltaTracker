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
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) // Needed to test order of the keys
@RunWith(AndroidJUnit4.class)
public class MealDatabaseTests {

    private Meal m1, m2, m3, m4;
    private Food f1, f2, f3, f4, f5, f6, f7, f8;
    private LocalDate d1, d2, d3, d4;
    private MealDatabaseHelper db;
    private Context appContext;
    private String name1, name2, name3, name4;
    private int cals1, cals2, cals3, cals4;
    private int carbs1, carbs2, carbs3, carbs4;
    private int protein1, protein2, protein3, protein4;
    private int fat1, fat2, fat3, fat4;
    private int fiber1, fiber2, fiber3, fiber4;
    private String mn1, mn2, mn3, mn4;  // MealNames
    private List<Food> list1, list2, list3, list4;
    private List<Meal> ml1, ml2, ml3, ml4;

    @Before
    public void setUp() throws Exception {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = new MealDatabaseHelper(appContext);

        d1 = LocalDate.of(2021, 4, 20); // format is 2021-04-20
        d2 = LocalDate.of(2021, 5, 21); // format is 2021-04-20
        d3 = LocalDate.of(2021, 6, 22); // format is 2021-04-20
        d4 = LocalDate.of(2021, 7, 23); // format is 2021-04-20
        name1 = "Chicken"; name2 = "Pasta"; name3 = "Pizza"; name4 = "Vomit";
        mn1 = "Dinner"; mn2 = "Dinner"; mn3 = "Breakfast"; mn4 = "Lunch";
        cals1 = 300; carbs1 = 600; protein1 = 20; fat1 = 5; fiber1 = 15;
        cals2 = 150; carbs2 = 600; protein2 = 20; fat2 = 5; fiber2 = 15;
        cals3 = 302; carbs3 = 700; protein3 = 20; fat3 = 20; fiber3 = 1;
        cals4 = 30; carbs4 = -1; protein4 = 5; fat4 = 50; fiber4 = 1;

        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        list4 = new ArrayList<>();

        m1 = new Meal(mn1, 0, 0, 0, 0, 0, d1, list1);
        m2 = new Meal(mn2, 0, 0, 0, 0, 0, d2, list2);
        m3 = new Meal(mn3, 0, 0, 0, 0, 0, d3, list3);
        m4 = new Meal(mn4, 0, 0, 0, 0, 0, d3, list4);

        f1 = new Food(name1, cals1, protein1, carbs1, fat1, fiber1, d1.toString(), m1.getName(), m1.getKey());
        f2 = new Food(name2, cals2, protein2, carbs2, fat2, fiber2, d1.toString(), m1.getName(), m1.getKey());
        f3 = new Food(name3, cals3, protein3, carbs3, fat3, fiber3, d2.toString(), m2.getName(), m2.getKey());
        f4 = new Food(name4, cals4, protein4, carbs4, fat4, fiber4, d2.toString(), m2.getName(), m2.getKey());
        f5 = new Food(name1, cals1, protein1, carbs1, fat1, fiber1, d3.toString(), m3.getName(), m3.getKey());
        f6 = new Food(name2, cals2, protein2, carbs2, fat2, fiber2, d3.toString(), m3.getName(), m3.getKey());
        f7 = new Food(name3, cals3, protein3, carbs3, fat3, fiber3, d4.toString(), m4.getName(), m4.getKey());
        f8 = new Food(name4, cals4, protein4, carbs4, fat4, fiber4, d4.toString(), m4.getName(), m4.getKey());
    }

    @After
    public void tearDown() {
        db.close();
        appContext.deleteDatabase("meal.db"); // Gotta reset the db after each test
    }

    @Test
    public void addAMeal() {
        tearDown();
        m1.addToMeal(f1);
        m1.addToMeal(f2);
        for(Food food : m1.getFoodList()) { db.addMeal(food); }

        ml1 = db.getMealsByDate(d1.toString());
        assertEquals(m1.getName(), ml1.get(0).getMealName());
        list1 = ml1.get(0).getFoodList();
        assertEquals("Test the food list in meal is correct", f1.getName(), list1.get(0).getName());  // Check food list is good
        assertEquals("Test the food list in meal is correct", f2.getName(), list1.get(1).getName());  // Check food list is good

    }

    @Test
    public void addManyMeal() {

        m1.addToMeal(f1);
        m1.addToMeal(f2);
        for(Food food : m1.getFoodList()) { db.addMeal(food); }

        m2.addToMeal(f3);
        m2.addToMeal(f4);
        for(Food food : m2.getFoodList()) { db.addMeal(food); }

        m3.addToMeal(f5);
        m3.addToMeal(f6);
        for(Food food : m3.getFoodList()) { db.addMeal(food); }

        m4.addToMeal(f7);
        m4.addToMeal(f8);
        for(Food food : m4.getFoodList()) { db.addMeal(food); }

        ml1 = db.getMealsByDate(d1.toString());
        assertEquals(m1.getName(), ml1.get(0).getMealName());
        assertEquals(1, ml1.size());
        list1 = ml1.get(0).getFoodList();
        assertEquals("Test the food list in meal is correct", f1.getName(), list1.get(0).getName());  // Check food list is good
        assertEquals("Test the food list in meal is correct", f2.getName(), list1.get(1).getName());  // Check food list is good

        ml2 = db.getMealsByDate(d2.toString());
        assertEquals(m2.getName(), ml2.get(0).getMealName());
        assertEquals(1, ml2.size());  // Make sure we are not having multiple meals
        list2 = ml2.get(0).getFoodList();
        assertEquals("Test the food list in meal is correct", f3.getName(), list2.get(0).getName());  // Check food list is good
        assertEquals("Test the food list in meal is correct", f4.getName(), list2.get(1).getName());  // Check food list is good

        ml3 = db.getMealsByDate(d3.toString());
        assertEquals(m3.getName(), ml3.get(0).getMealName());
        assertEquals(1, ml3.size());
        list3 = ml3.get(0).getFoodList();
        assertEquals("Test the food list in meal is correct", f5.getName(), list3.get(0).getName());  // Check food list is good
        assertEquals("Test the food list in meal is correct", f6.getName(), list3.get(1).getName());  // Check food list is good

        ml4 = db.getMealsByDate(d4.toString());
        assertEquals(m4.getName(), ml4.get(0).getMealName());
        assertEquals(1, ml4.size());
        list4 = ml4.get(0).getFoodList();
        assertEquals("Test the food list in meal is correct", f7.getName(), list4.get(0).getName());  // Check food list is good
        assertEquals("Test the food list in meal is correct", f8.getName(), list4.get(1).getName());  // Check food list is good
    }


    @Test
    public void getAllMeal() {

        m1.addToMeal(f1);
        m1.addToMeal(f2);
        for(Food food : m1.getFoodList()) { db.addMeal(food); }

        m2.addToMeal(f3);
        m2.addToMeal(f4);
        for(Food food : m2.getFoodList()) { db.addMeal(food); }

        m3.addToMeal(f5);
        m3.addToMeal(f6);
        for(Food food : m3.getFoodList()) { db.addMeal(food); }

        m4.addToMeal(f7);
        m4.addToMeal(f8);
        for(Food food : m4.getFoodList()) { db.addMeal(food); }

        ml1 = db.getAll();
        assertEquals(4, ml1.size());

//        assertTrue(ml1.contains(m1));

    }


    @Test
    public void deleteNone() {
        assertFalse(db.deleteMeal(m1));
    }


    @Test
    public void deleteOne() {
        m1.addToMeal(f1);
        m1.addToMeal(f2);
        for(Food food : m1.getFoodList()) { db.addMeal(food); }
        ml1 = db.getMealsByDate(d1.toString());

        //assertTrue(db.deleteMeal(m1));
        db.deleteMeal(ml1.get(0));
        ml1 = db.getMealsByDate(d1.toString());
        assertEquals(0, ml1.size());
    }


    @Test
    public void DeleteManyMeal() {
        m1.addToMeal(f1);
        m1.addToMeal(f2);
        for(Food food : m1.getFoodList()) { db.addMeal(food); }

        m2.addToMeal(f3);
        m2.addToMeal(f4);
        for(Food food : m2.getFoodList()) { db.addMeal(food); }

        m3.addToMeal(f5);
        m3.addToMeal(f6);
        for(Food food : m3.getFoodList()) { db.addMeal(food); }

        m4.addToMeal(f7);
        m4.addToMeal(f8);
        for(Food food : m4.getFoodList()) { db.addMeal(food); }

        ml1 = db.getMealsByDate(d1.toString());
        assertEquals(m1.getName(), ml1.get(0).getMealName());
        assertEquals(1, ml1.size());
        list1 = ml1.get(0).getFoodList();
        assertEquals("Test the food list in meal is correct", f1.getName(), list1.get(0).getName());  // Check food list is good
        assertEquals("Test the food list in meal is correct", f2.getName(), list1.get(1).getName());  // Check food list is good

        ml2 = db.getMealsByDate(d2.toString());
        assertEquals(m2.getName(), ml2.get(0).getMealName());
        assertEquals(1, ml2.size());  // Make sure we are not having multiple meals
        list2 = ml2.get(0).getFoodList();
        assertEquals("Test the food list in meal is correct", f3.getName(), list2.get(0).getName());  // Check food list is good
        assertEquals("Test the food list in meal is correct", f4.getName(), list2.get(1).getName());  // Check food list is good

        ml3 = db.getMealsByDate(d3.toString());
        assertEquals(m3.getName(), ml3.get(0).getMealName());
        assertEquals(1, ml3.size());
        list3 = ml3.get(0).getFoodList();
        assertEquals("Test the food list in meal is correct", f5.getName(), list3.get(0).getName());  // Check food list is good
        assertEquals("Test the food list in meal is correct", f6.getName(), list3.get(1).getName());  // Check food list is good

        ml4 = db.getMealsByDate(d4.toString());
        assertEquals(m4.getName(), ml4.get(0).getMealName());
        assertEquals(1, ml4.size());
        list4 = ml4.get(0).getFoodList();
        assertEquals("Test the food list in meal is correct", f7.getName(), list4.get(0).getName());  // Check food list is good
        assertEquals("Test the food list in meal is correct", f8.getName(), list4.get(1).getName());  // Check food list is good

        // perform the actual delete and check the deleted meals are not in the database
        db.deleteMeal(ml1.get(0));
        ml1 = db.getMealsByDate(d1.toString());
        assertEquals(0, ml1.size());

        db.deleteMeal(ml4.get(0));
        ml4 = db.getMealsByDate(d4.toString());
        assertEquals(0, ml4.size());

        // Check that non deleted meals still exist
        ml2 = db.getMealsByDate(d2.toString());
        assertEquals(1, ml2.size());
        assertEquals(mn2, ml2.get(0).getMealName());

        ml3 = db.getMealsByDate(d3.toString());
        assertEquals(1, ml3.size());
        assertEquals(mn3, ml3.get(0).getMealName());
    }


    @Test
    public void testCheckByDateAndName() {
        m1.addToMeal(f1);
        m1.addToMeal(f2);
        assertTrue(db.checkByDateAndName(m1.getDate(), m1.getName()));
        for(Food food : m1.getFoodList()) { db.addMeal(food); }

        assertFalse(db.checkByDateAndName(m1.getDate(), m1.getName()));
    }

}
