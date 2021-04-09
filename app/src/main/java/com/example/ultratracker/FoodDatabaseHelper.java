package com.example.ultratracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FoodDatabaseHelper extends SQLiteOpenHelper {
    public static final String FOOD_TABLE = "FOOD_TABLE";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_CALS = "CALS";
    public static final String COLUMN_PROTEIN = "PROTEIN";
    public static final String COLUMN_CARBS = "CARBS";
    public static final String COLUMN_FAT = "FAT";
    public static final String COLUMN_FIBER = "FIBER";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_KEY = "KEYID";

    public FoodDatabaseHelper(@Nullable Context context) {
        super(context, "food.db", null, 1);
    }

    // Called first time a database is accessed
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + FOOD_TABLE + " (" + COLUMN_ID + " INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_KEY + " INT, " + COLUMN_NAME + " TEXT, " + COLUMN_CALS + " INT, " + COLUMN_PROTEIN + " INT, " + COLUMN_CARBS + " INT, " +
                COLUMN_FAT + " INT, " + COLUMN_FIBER + " INT, " + COLUMN_DATE + " Text) ";
        db.execSQL(createTableStatement);
    }

    // this is called if the database version number changes. Prevents db from breaking
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addFood (Food food) {
        String queryString = "SELECT * FROM " + FOOD_TABLE + " WHERE " + COLUMN_KEY + " = " + food.getKey();

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_KEY, food.getKey());
        cv.put(COLUMN_NAME, food.getName());
        cv.put(COLUMN_CALS, food.getCals());
        cv.put(COLUMN_PROTEIN, food.getProtein());
        cv.put(COLUMN_CARBS, food.getCarbs());
        cv.put(COLUMN_FAT, food.getFat());
        cv.put(COLUMN_FIBER, food.getFiber());
        cv.put(COLUMN_DATE, food.getDate());

        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return false;
        }
        cursor.close();
        long insert = db.insert(FOOD_TABLE, null, cv);
        return insert != -1;
    }

    public boolean deleteFood(Food food) {
        // get data from the database
        String queryString = "DELETE FROM " + FOOD_TABLE + " WHERE " + COLUMN_KEY + " = " + food.getKey();
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

    public Food[] getByDate(String date) {
        List<Food> returnList = new ArrayList<>();
        List<Integer> keyList = new ArrayList<>(); // so we don't store dupes

        // get data from the database
        String queryString = "SELECT * FROM " + FOOD_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        int key;
        String name;
        int cals;
        int protein;
        int carbs;
        int fat;
        int fiber;
        String assignedDate;
        // move to the first result. If it is true then there is at least 1 value
        if (cursor.moveToFirst()) {
            // loop through cursor and create new food objects and put in return list
            do {
                assignedDate = cursor.getString(8);
                if (assignedDate.equals(date)) {
                    key = cursor.getInt(1);
                    name = cursor.getString(2);
                    cals = cursor.getInt(3);
                    protein = cursor.getInt(4);
                    carbs = cursor.getInt(5);
                    fat = cursor.getInt(6);
                    fiber = cursor.getInt(7);

                    Food newFood = new Food(name, cals, protein, carbs, fat, fiber, date, key);
                    //if (keyList.contains(newTask.getKey())) { continue; }
                    keyList.add(newFood.getKey());
                    returnList.add(newFood);
                }

            } while (cursor.moveToNext());
        }
        else {

        }
        cursor.close();
        db.close();
        return sortList(returnList);
    }

    // Returns all Food items sorted alphabetically
    public Food[] getAllSorted() {
        ArrayList<Food> returnList = new ArrayList<>();
        List<Integer> keyList = new ArrayList<>(); // so we don't store dupes

        // get data from the database
        String queryString = "SELECT * FROM " + FOOD_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        // move to the first result. If it is true then there is at least 1 value
        if(cursor.moveToFirst()) {
            // loop through cursor and create new food objects and put in return list
            do{
                int key = cursor.getInt(1);
                String name = cursor.getString(2);
                int cals = cursor.getInt(3);
                int protein = cursor.getInt(4);
                int carbs = cursor.getInt(5);
                int fat = cursor.getInt(6);
                int fiber = cursor.getInt(7);
                String date = cursor.getString(8);

                Food newFood = new Food(name, cals, protein, carbs, fat, fiber, date, key);
                //if (keyList.contains(newTask.getKey())) {
                //    continue;
                //}

                keyList.add(newFood.getKey());
                returnList.add(newFood);
            } while (cursor.moveToNext());
        }
        else {

        }
        cursor.close();
        db.close();
        return sortList(returnList);
    }

    public Food[] sortList(List<Food> foodList) {
        // Check if foodList is empty
        if (foodList.isEmpty()) { return null; }

        // Sort the list alphabetically
        Food temp;
        Food[] sortedList = new Food[foodList.size()];
        int count = 0;
        for (Food food: foodList) {
            sortedList[count] = food;
            count++;
        }
        for (int i = 0; i < sortedList.length; i++) {
            for (int j = i + 1; j < sortedList.length; j++) {
                if (sortedList[i].getName().compareTo(sortedList[j].getName()) > 0) {
                    temp = sortedList[i];
                    sortedList[i] = sortedList[j];
                    sortedList[j] = temp;
                }
            }
        }
        return sortedList;
    }

    public boolean editFood(Food food){
        String queryString = "SELECT * FROM " + FOOD_TABLE + " WHERE " + COLUMN_KEY + " = " + food.getKey();
        SQLiteDatabase db = this.getReadableDatabase();

        String name;
        int cals;
        int protein;
        int carbs;
        int fat;
        int fiber;
        String date;

        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            name = food.getName();
            cals = food.getCals();
            protein = food.getProtein();
            carbs = food.getCarbs();
            fat = food.getFat();
            fiber = food.getFiber();
            date = food.getDate();

            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name);
            cv.put(COLUMN_CALS, cals);
            cv.put(COLUMN_PROTEIN, protein);
            cv.put(COLUMN_CARBS, carbs);
            cv.put(COLUMN_FAT, fat);
            cv.put(COLUMN_FIBER, fiber);
            cv.put(COLUMN_DATE, date);
            cv.put(COLUMN_KEY, food.getKey());

            String[] whereArgs = {String.valueOf(food.getKey())};
            int success = db.update(FOOD_TABLE, cv, "keyid=?", whereArgs);
            if(success > 0){
                db.close();
                cursor.close();
                return true;
            }
        }

        db.close();
        cursor.close();
        return  false;
    }

}
