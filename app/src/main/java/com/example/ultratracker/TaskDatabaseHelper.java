package com.example.ultratracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TaskDatabaseHelper extends SQLiteOpenHelper {

    public static final String TASK_TABLE = "TASK_TABLE";
    public static final String COLUMN_KEY = "KEYID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_ASSIGNED_DATE = "ASSIGNEDDATE";
    public static final String COLUMN_DUEDATE = "DUEDATE";
    public static final String COLUMN_DUETIME = "DUETIME";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String COLUMN_PRIORITY = "PRIORITY";
    public static final String COLUMN_COMPLETE = "COMPLETE";
    public static final String COLUMN_ID = "ID";

    public TaskDatabaseHelper(@Nullable Context context) {
        super(context, "task.db", null, 1);
    }

    // Called first time a database is accessed
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TASK_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_KEY + " INT, " + COLUMN_NAME + " TEXT, " + COLUMN_ASSIGNED_DATE + " TEXT, " + COLUMN_DUEDATE + " TEXT, " +
                COLUMN_DUETIME + " TEXT, " + COLUMN_DESCRIPTION + " TEXT, " + COLUMN_PRIORITY + " INT, " + COLUMN_COMPLETE + " BOOL)";
        db.execSQL(createTableStatement);
    }

    // this is called if the database version number changes. Prevents db from breaking
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne (Task task) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_KEY, task.getKey());
        cv.put(COLUMN_NAME, task.getName());
        cv.put(COLUMN_ASSIGNED_DATE, task.getAssignedDate());
        cv.put(COLUMN_DUEDATE, task.getDueDate());
        cv.put(COLUMN_DUETIME, task.getDueTime());
        cv.put(COLUMN_DESCRIPTION, task.getDescription());
        cv.put(COLUMN_PRIORITY, task.getPriority());
        cv.put(COLUMN_COMPLETE, task.isComplete());

        long insert = db.insert(TASK_TABLE, null, cv);
        if(insert == -1) return false;
        return true;
    }


    public boolean deleteTask(Task task) {
        // get data from the database
        String queryString = "DELETE FROM " + TASK_TABLE + " WHERE " + COLUMN_KEY + " = " + task.getKey();
        SQLiteDatabase db = this.getWritableDatabase();

       Cursor cursor = db.rawQuery(queryString, null);
       if(cursor.moveToFirst()){
           return true;
       }
       else {
           return false;
       }
    }

    public List<Task> getAll() {
        List<Task> returnList = new ArrayList<>();
        List<Integer> keyList = new ArrayList<>(); // so we don't store dupes

        // get data from the database
        String queryString = "SELECT * FROM " + TASK_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        // move to the first result. If it is true then there is at least 1 value
        if(cursor.moveToFirst()) {
            // loop through cursor and create new food objects and put in return list
            do{
                int key = cursor.getInt(1);
                String name = cursor.getString(2);
                String assignedDate = cursor.getString(3);
                String dueDate = cursor.getString(4);
                String dueTime = cursor.getString(5);
                String description = cursor.getString(6);
                int priority = cursor.getInt(7);
                boolean complete =  cursor.getInt(8) == 1 ? true : false;

                Task newTask = new Task(name, assignedDate, dueDate, dueTime, description, priority, complete, key);
                if (keyList.contains(newTask.getKey())) {
                    continue;
                }

                keyList.add(newTask.getKey());
                returnList.add(newTask);

            } while (cursor.moveToNext());
        }
        else {

        }
        cursor.close();
        db.close();
        return returnList;
    }

    public List<Task> getByDate(String date) {
        List<Task> returnList = new ArrayList<>();
        List<Integer> keyList = new ArrayList<>(); // so we don't store dupes

        // get data from the database
        String queryString = "SELECT * FROM " + TASK_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        int key;
        String name;
        String assignedDate;
        String dueDate;
        String dueTime;
        String description;
        int priority;
        boolean complete;
        // move to the first result. If it is true then there is at least 1 value
        if (cursor.moveToFirst()) {
            // loop through cursor and create new food objects and put in return list
            do {
                assignedDate = cursor.getString(3);
                if (assignedDate.equals(date)) {
                    key = cursor.getInt(1);
                    name = cursor.getString(2);
                    dueDate = cursor.getString(4);
                    dueTime = cursor.getString(5);
                    description = cursor.getString(6);
                    priority = cursor.getInt(7);
                    complete = cursor.getInt(8) == 1 ? true : false;

                    Task newTask = new Task(name, assignedDate, dueDate, dueTime, description, priority, complete, key);
                    if (keyList.contains(newTask.getKey())) { continue; }
                    keyList.add(newTask.getKey());
                    returnList.add(newTask);
                }

            } while (cursor.moveToNext());
        }
        else {

        }
        cursor.close();
        db.close();
        return returnList;
    }

    public List<Task> getByDateCompleted(String date) {
        List<Task> returnList = new ArrayList<>();
        List<Integer> keyList = new ArrayList<>(); // so we don't store dupes

        // get data from the database
        String queryString = "SELECT * FROM " + TASK_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        int key;
        String name;
        String assignedDate;
        String dueDate;
        String dueTime;
        String description;
        int priority;
        boolean complete;
        // move to the first result. If it is true then there is at least 1 value
        if (cursor.moveToFirst()) {
            // loop through cursor and create new food objects and put in return list
            do {
                assignedDate = cursor.getString(3);
                complete = cursor.getInt(8) == 1 ? true : false;
                if (assignedDate.equals(date) && complete) {
                    key = cursor.getInt(1);
                    name = cursor.getString(2);
                    dueDate = cursor.getString(4);
                    dueTime = cursor.getString(5);
                    description = cursor.getString(6);
                    priority = cursor.getInt(7);

                    Task newTask = new Task(name, assignedDate, dueDate, dueTime, description, priority, true, key);
                    if (keyList.contains(newTask.getKey())) { continue; }
                    keyList.add(newTask.getKey());
                    returnList.add(newTask);
                }

            } while (cursor.moveToNext());
        }
        else {

        }
        cursor.close();
        db.close();
        return returnList;
    }
}
