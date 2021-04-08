package com.example.ultratracker;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MealUnitTest {

    private int carb, cal, protein, fat, fiber;
    private LocalDate d1;
    private String name, mealName;
    private List<Food> foodList;
    private Meal m1, m2;

    @Before
    public void setup() throws Exception {
        d1 = LocalDate.of(2021,4,20); // format is 2021-04-20
        carb = 20;
        cal = 700;
        protein = 10;
        fat = 2;
        fiber = 15;
        name = "Dinner";
        mealName = "Lunch";
        foodList = new ArrayList<Food>();
        // no key arg
        m1 = new Meal(name, cal, protein, carb, fat, fiber, d1, foodList);
        // with key arg
        m2 = new Meal(name, cal, protein, carb, fat, fiber, d1.toString(), mealName, foodList, 2);
    }

    @Test
    public void testConstructors() {
        assertEquals("Test name on non arg constructor", name, m1.getName());
        assertEquals("Test cal on non arg constructor", cal, m1.getCals());
        assertEquals("Test protein on non arg constructor", protein, m1.getProtein());
        assertEquals("Test carb on non arg constructor", carb, m1.getCarbs());
        assertEquals("Test fat on non arg constructor", fat, m1.getFat());
        assertEquals("Test fiber on non arg constructor", fiber, m1.getFiber());
        assertEquals("Test d1 on non arg constructor", d1.toString(), m1.getDate());
        assertEquals("Test foodList on non arg constructor", foodList, m1.getFoodList());

        assertEquals("Test name on regular constructor", name, m2.getName());
        assertEquals("Test cal on regular constructor", cal, m2.getCals());
        assertEquals("Test protein regular arg constructor", protein, m2.getProtein());
        assertEquals("Test carb on regular constructor", carb, m2.getCarbs());
        assertEquals("Test fat on regular constructor", fat, m2.getFat());
        assertEquals("Test fiber on regular constructor", fiber, m2.getFiber());
        assertEquals("Test d1 on regular constructor", d1.toString(), m2.getDate());
        assertEquals("Test mealName on regular constructor", m2.getMealName(), m2.getMealName());
        assertEquals("Test foodList regular constructor", foodList, m2.getFoodList());
        assertEquals("Test key on regular constructor", 2, m2.getKey());
    }

    @Test
    public void testRideSetter() {

        assertEquals("Test name on regular constructor", name, m2.getName());
        assertEquals("Test cal on regular constructor", cal, m2.getCals());
        assertEquals("Test protein regular arg constructor", protein, m2.getProtein());
        assertEquals("Test carb on regular constructor", carb, m2.getCarbs());
        assertEquals("Test fat on regular constructor", fat, m2.getFat());
        assertEquals("Test fiber on regular constructor", fiber, m2.getFiber());
        assertEquals("Test d1 on regular constructor", d1.toString(), m2.getDate());
        assertEquals("Test mealName on regular constructor", mealName, m2.getMealName());
        assertEquals("Test foodList regular constructor", foodList, m2.getFoodList());
        assertEquals("Test key on regular constructor", 2, m2.getKey());

        String name1 = "name1";
        String mealName1 = "nameMeal1";
        int cal1 = 1;
        int protein1 = 1;
        int carb1 = 1;
        int fat1 = 1;
        int fiber1 = 1;
        int key1 = 1;

        m2.setName(name1);
        m2.setCals(cal1);
        m2.setProtein(protein1);
        m2.setCarbs(carb1);
        m2.setFat(fat1);
        m2.setFiber(fiber1);
        m2.setDate(LocalDate.parse("2021-04-22"));
        m2.setMealName(mealName1);

        assertEquals("Test name on regular constructor", name1, m2.getName());
        assertEquals("Test cal on regular constructor", cal1, m2.getCals());
        assertEquals("Test protein regular arg constructor", protein1, m2.getProtein());
        assertEquals("Test carb on regular constructor", carb1, m2.getCarbs());
        assertEquals("Test fat on regular constructor", fat1, m2.getFat());
        assertEquals("Test fiber on regular constructor", fiber1, m2.getFiber());
        assertEquals("Test d1 on regular constructor", "2021-04-22", m2.getDate());
        assertEquals("Test mealName on regular constructor", mealName1, m2.getMealName());

    }

    @Test
    public void testMealToString() {
        assertEquals("test toString on regular constructor", "Dinner, 700, 10, 20, 2, 15", m1.toString());
        assertEquals("test toString on regular constructor", "Dinner, 700, 10, 20, 2, 15", m2.toString());
    }
}
