package com.example.ultratracker;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FoodUnitTest {

    private Food f1, f2, f3, f4;
    private String name1, name2;
    private String mealName;
    private int cals, protein, carbs, fat, fiber;
    private LocalTime t1;
    private LocalDate d1;
    private int mealKey, key;

    @Before
    public void setUp(){
        name1 = "Pasta";
        name2 = "Potatoes";
        mealName = "Dinner";
        cals = 400;
        carbs = 200;
        protein = 5;
        fat = 1;
        fiber = 3;
        mealKey = 1;
        key = 0;

        d1 = LocalDate.of(2021,4,20); // format is 2021-04-20
        t1 = LocalTime.of(10,14); // format is 10:14

        // gui provide info
        f1 = new Food(name1, cals, protein, carbs, fat, fiber, d1);

        f2 = new Food(name1, cals, protein, carbs, fat, fiber, d1.toString(), key);

        // meal database pass in
        f3 = new Food(name1, cals, protein, carbs, fat, fiber, d1.toString(), mealName, mealKey);

        // meal database retrieval
        f4 = new Food(name1, cals, protein, carbs, fat, fiber, d1.toString(), mealName, key, mealKey);
    }

    @Test
    public void testGUIConstructor(){
        assertEquals("Test GUI name", name1, f1.getName());
        assertEquals("Test GUI cals", cals, f1.getCals());
        assertEquals("Test GUI protein", protein, f1.getProtein());
        assertEquals("Test GUI carbs", carbs, f1.getCarbs());
        assertEquals("Test GUI fat", fat, f1.getFat());
        assertEquals("Test GUI fiber", fiber, f1.getFiber());
        assertEquals("Test GUI date", d1.toString(), f1.getDate());
    }

    @Test
    public void testConstructor(){
        assertEquals("Test name", name1, f2.getName());
        assertEquals("Test cals", cals, f2.getCals());
        assertEquals("Test protein", protein, f2.getProtein());
        assertEquals("Test carbs", carbs, f2.getCarbs());
        assertEquals("Test fat", fat, f2.getFat());
        assertEquals("Test fiber", fiber, f2.getFiber());
        assertEquals("Test date", d1.toString(), f2.getDate());
        assertEquals("Test key", key, f2.getKey());
    }

    @Test
    public void testDatabaseInConstructor(){
        assertEquals("Test DatabaseIn name", name1, f3.getName());
        assertEquals("Test DatabaseIn cals", cals, f3.getCals());
        assertEquals("Test DatabaseIn protein", protein, f3.getProtein());
        assertEquals("Test DatabaseIn carbs", carbs, f3.getCarbs());
        assertEquals("Test DatabaseIn fat", fat, f3.getFat());
        assertEquals("Test DatabaseIn fiber", fiber, f3.getFiber());
        assertEquals("Test DatabaseIn date", d1.toString(), f3.getDate());
        assertEquals("Test DatabaseIn mealName", mealName, f3.getMealName());
        assertEquals("Test DatabaseIn mealKey", mealKey, f3.getMealKey());
    }

    @Test
    public void testDatabaseOutConstructor(){
        assertEquals("Test DatabaseOut name", name1, f4.getName());
        assertEquals("Test DatabaseOut cals", cals, f4.getCals());
        assertEquals("Test DatabaseOut protein", protein, f4.getProtein());
        assertEquals("Test DatabaseOut carbs", carbs, f4.getCarbs());
        assertEquals("Test DatabaseOut fat", fat, f4.getFat());
        assertEquals("Test DatabaseOut fiber", fiber, f4.getFiber());
        assertEquals("Test DatabaseOut date", d1.toString(), f4.getDate());
        assertEquals("Test DatabaseOut mealName", mealName, f4.getMealName());
        assertEquals("Test DatabaseOut mealKey", key, f4.getKey());
        assertEquals("Test DatabaseOut mealKey", mealKey, f4.getMealKey());
    }

    @Test
    public void testSetters(){
        // Initial verify
        assertEquals("Initial verify of name", name1, f4.getName());
        assertEquals("TInitial verify of cals", cals, f4.getCals());
        assertEquals("Initial verify of protein", protein, f4.getProtein());
        assertEquals("Initial verify of carbs", carbs, f4.getCarbs());
        assertEquals("Initial verify of fat", fat, f4.getFat());
        assertEquals("Initial verify of fiber", fiber, f4.getFiber());
        assertEquals("Initial verify of date", d1.toString(), f4.getDate());
        assertEquals("Initial verify of mealName", mealName, f4.getMealName());
        assertEquals("Initial verify of mealKey", key, f4.getKey());
        assertEquals("Initial verify of mealKey", mealKey, f4.getMealKey());

        f4.setName(name2);
        f4.setCals(500);
        f4.setProtein(10);
        f4.setCarbs(300);
        f4.setFat(20);
        f4.setFiber(12);
        f4.setDate(LocalDate.parse("2021-04-22"));
        f4.setMealName("Lunch");
        f4.setMealKey(2);


        assertEquals("verify setting of name", name2, f4.getName());
        assertEquals("verify setting of cals", 500, f4.getCals());
        assertEquals("verify setting of protein", 10, f4.getProtein());
        assertEquals("verify setting of carbs", 300, f4.getCarbs());
        assertEquals("verify setting of fat", 20, f4.getFat());
        assertEquals("verify setting of fiber", 12, f4.getFiber());
        assertEquals("verify setting of date", "2021-04-22", f4.getDate());
        assertEquals("verify setting of mealName", "Lunch", f4.getMealName());
        assertEquals("verify setting of mealKey", 2, f4.getMealKey());
    }

    @Test
    public void testToString() {
        String expected = name1 + ", " + cals + ", " + protein + ", " + carbs + ", " + fat + ", " + fiber;
        assertEquals("verify toString works", expected, f1.toString());
        assertEquals("verify toString works", expected, f2.toString());
        assertEquals("verify toString works", expected, f3.toString());
        assertEquals("verify toString works", expected, f4.toString());

    }

}
