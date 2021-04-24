package com.example.ultratracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MealDatabaseHelper extends SQLiteOpenHelper {
    public static final String MEAL_TABLE = "MEAL_TABLE";
    public static final String COLUMN_FOOD_NAME = "FOOD_NAME";
    public static final String COLUMN_MEAL_CALS = "MEAL_CALS";
    public static final String COLUMN_MEAL_PROTEIN = "MEAL_PROTEIN";
    public static final String COLUMN_MEAL_CARBS = "MEAL_CARBS";
    public static final String COLUMN_MEAL_FAT = "MEAL_FAT";
    public static final String COLUMN_MEAL_FIBER = "MEAL_FIBER";
    public static final String COLUMN_MEAL_DATE = "MEAL_DATE";
    public static final String COLUMN_MEAL_NAME = "MEAL_NAME";
    public static final String COLUMN_MEAL_ID = "MEAL_ID";
    public static final String COLUMN_MEAL_KEY = "MEAL_KEYID";
    public static final String COLUMN_FOOD_KEY = "FOOD_KEYID";

    public MealDatabaseHelper(@Nullable Context context) {
        super(context, "meal.db", null, 1);
    }

    // Called first time a database is accessed
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createMealTableStatement = "CREATE TABLE " + MEAL_TABLE + " (" + COLUMN_MEAL_ID + " INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MEAL_KEY + " INT, " + COLUMN_FOOD_KEY + " INT, " + COLUMN_FOOD_NAME + " TEXT, " + COLUMN_MEAL_CALS + " INT, " + COLUMN_MEAL_PROTEIN + " INT, " +
                COLUMN_MEAL_CARBS + " INT, " + COLUMN_MEAL_FAT + " INT, " + COLUMN_MEAL_FIBER + " INT, " + COLUMN_MEAL_DATE + " Text, " + COLUMN_MEAL_NAME + " TEXT) ";
        db.execSQL(createMealTableStatement);
    }

    // this is called if the database version number changes. Prevents db from breaking
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addMeal (Food food) {
        String queryString = "SELECT * FROM " + MEAL_TABLE + " WHERE " + COLUMN_FOOD_KEY + " = " + food.getKey();

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_MEAL_KEY, food.getMealKey());
        cv.put(COLUMN_FOOD_KEY, food.getKey());
        cv.put(COLUMN_FOOD_NAME, food.getName());
        cv.put(COLUMN_MEAL_CALS, food.getCals());
        cv.put(COLUMN_MEAL_PROTEIN, food.getProtein());
        cv.put(COLUMN_MEAL_CARBS, food.getCarbs());
        cv.put(COLUMN_MEAL_FAT, food.getFat());
        cv.put(COLUMN_MEAL_FIBER, food.getFiber());
        cv.put(COLUMN_MEAL_DATE, food.getDate());
        cv.put(COLUMN_MEAL_NAME, food.getMealName());

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return false;
        }
        cursor.close();
        long insert = db.insert(MEAL_TABLE, null, cv);
        return insert != -1;
    }

    public boolean deleteMeal(Meal meal) {
        // get data from the database
        String queryString = "DELETE FROM " + MEAL_TABLE + " WHERE " + COLUMN_MEAL_KEY + " = " + meal.getKey();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            cursor.close();
            return true;
        }
        else {
            cursor.close();
            return false;
        }
    }

    public List<Meal> getAll() {
        List<Food> foodList = new ArrayList<>();
        List<Meal> mealList;

        // get data from the database
        String queryString = "SELECT * FROM " + MEAL_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        // move to the first result. If it is true then there is at least 1 value
        if(cursor.moveToFirst()) {
            // loop through cursor and create new food objects and put in return list
            do{
                int mealKey = cursor.getInt(1);
                int foodKey = cursor.getInt(2);
                String name = cursor.getString(3);
                int cals = cursor.getInt(4);
                int protein = cursor.getInt(5);
                int carbs = cursor.getInt(6);
                int fat = cursor.getInt(7);
                int fiber = cursor.getInt(8);
                String date = cursor.getString(9);
                String mealName = cursor.getString(10);

                Food food = new Food(name, cals, protein, carbs, fat, fiber, date, mealName, foodKey, mealKey);
                foodList.add(food);
            } while (cursor.moveToNext());
        }

        // Send foodList to be organized into meals
        mealList = foodToMeals(foodList);
        mealList = sortMeals(mealList);
        cursor.close();
        db.close();
        return mealList;
    }

    public List<Meal> getMealsByDate(String date) {
        List<Food> foodList = new ArrayList<>();
        List<Meal> mealList;

        // get data from the database
        String queryString = "SELECT * FROM " + MEAL_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        int mealKey;
        int foodKey;
        String name;
        int cals;
        int protein;
        int carbs;
        int fat;
        int fiber;
        String assignedDate;
        String mealName;
        // move to the first result. If it is true then there is at least 1 value
        if (cursor.moveToFirst()) {
            // loop through cursor and add all entries with correct date to foodList
            do {
                assignedDate = cursor.getString(9);
                if (assignedDate.equals(date)) {
                    mealKey = cursor.getInt(1);
                    foodKey = cursor.getInt(2);
                    name = cursor.getString(3);
                    cals = cursor.getInt(4);
                    protein = cursor.getInt(5);
                    carbs = cursor.getInt(6);
                    fat = cursor.getInt(7);
                    fiber = cursor.getInt(8);
                    mealName = cursor.getString(10);

                    Food food = new Food(name, cals, protein, carbs, fat, fiber, date, mealName, foodKey, mealKey);
                    foodList.add(food);

                    //Meal newMeal = new Meal(name, cals, protein, carbs, fat, fiber, date, mealName, foodList, key);
                }

            } while (cursor.moveToNext());
        }
        else {

        }

        // Send foodList to be organized into meals
        mealList = foodToMeals(foodList);
        cursor.close();
        db.close();
        return mealList;
    }

    public boolean checkByDateAndName(String date, String name) {
        // get data from the database
        String queryString = "SELECT * FROM " + MEAL_TABLE + " WHERE " + COLUMN_MEAL_DATE + " = ? AND " + COLUMN_MEAL_NAME + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, new String[] {date, name});

        // move to the first result. If it is true then there is at least 1 value
        if (cursor.moveToFirst()) {
            return false;
        } else {
            return true;
        }
    }

    public List<Meal> foodToMeals (List<Food> foodList) {
        HashMap<Integer, List<Food>> hashMap = new HashMap<Integer, List<Food>>();
        List<Meal> mealList = new ArrayList<>();
        List<Integer> mealKeys = new ArrayList<>();

        for (Food food: foodList) {
            if (!hashMap.containsKey(food.getMealKey())) {
                List<Food> list = new ArrayList<>();
                list.add(food);
                hashMap.put(food.getMealKey(), list);
                mealKeys.add(food.getMealKey());
            } else {
                hashMap.get(food.getMealKey()).add(food);
            }
        }

        for (int i = 0; i < mealKeys.size(); i++) {
            List<Food> foods = hashMap.get(mealKeys.get(i));
            int totalCals = 0;
            int totalProtein = 0;
            int totalCarbs = 0;
            int totalFat = 0;
            int totalFiber = 0;

            for (Food food: foods) {
                totalCals += food.getCals();
                totalProtein += food.getProtein();
                totalCarbs += food.getCarbs();
                totalFat += food.getFat();
                totalFiber += food.getFiber();
            }

            Meal meal= new Meal(foods.get(0).getMealName(), totalCals, totalProtein, totalCarbs, totalFat, totalFiber, foods.get(0).getDate(), foods.get(0).getMealName(), foods, foods.get(0).getMealKey());
            mealList.add(meal);
        }

        return mealList;
    }

    public List<Meal> sortMeals (List<Meal> mealList) {
        HashMap<Integer, List<Meal>> hashMap = new HashMap<Integer, List<Meal>>(500000);
        List<Meal> returnList = new ArrayList<>();
        //List<Integer> taskKeys = new ArrayList<>();

        for (Meal meal: mealList) {
            if (!hashMap.containsKey(meal.getKey())) {
                List<Meal> list = new ArrayList<>();
                list.add(meal);
                hashMap.put(meal.getKey(), list);
                // taskKeys.add(task.getKey());
            } else {
                hashMap.get(meal.getKey()).add(meal);
            }
        }

        for (int i = 0; i < 500000; i++) {
            if (hashMap.get(i) != null) {
                List<Meal> meals = hashMap.get(i);
                for (int j = 0; j < meals.size(); j++) {
                    returnList.add(meals.get(j));
                }
            }
        }
        return returnList;
    }
}
