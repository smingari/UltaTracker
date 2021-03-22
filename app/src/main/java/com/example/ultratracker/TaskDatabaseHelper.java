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
    public static final String COLUMN_KEY = "KEY";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_DUEDATE = "DUEDATE";
    public static final String COLUMN_DUETIME = "DUETIME";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String COLUMN_PRIORITY = "PRIORITY";
    public static final String COLUMN_COMPLETE = "COMPLETE";


    public TaskDatabaseHelper(@Nullable Context context) {
        super(context, "task.db", null, 1);
    }

    // Called first time a database is accessed
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TASK_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_KEY + " INT, "+ COLUMN_NAME + " TEXT, " + COLUMN_DUEDATE + " TEXT, " +
                COLUMN_DUETIME + " TEXT, " + COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_PRIORITY + " INT, " + COLUMN_COMPLETE + " BOOL)";
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
        cv.put(COLUMN_NAME, task.getKey());
        cv.put(COLUMN_DUEDATE, task.getKey());
        cv.put(COLUMN_DUETIME, task.getKey());
        cv.put(COLUMN_DESCRIPTION, task.getKey());
        cv.put(COLUMN_PRIORITY, task.getKey());
        cv.put(COLUMN_PRIORITY, task.getKey());
        cv.put(COLUMN_COMPLETE, task.getKey());

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
                String dueDate = cursor.getString(2);
                String dueTime = cursor.getString(3);
                String description = cursor.getString(4);
                int priority = cursor.getInt(5);
                boolean complete =  cursor.getInt(6) == 1 ? true : false;

                Task newTask = new Task(name, dueDate, dueTime, description, priority, complete, key);
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


}
