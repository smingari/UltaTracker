package com.example.ultratracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//import com.example.ultratracker.MainActivity;

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
        return insert != -1;
    }


    public boolean deleteTask(Task task) {
        // get data from the database
        String queryString = "DELETE FROM " + TASK_TABLE + " WHERE " + COLUMN_KEY + " = " + task.getKey();
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

    // MAYBE REMOVE THIS
    public boolean updateAll(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put(COLUMN_NAME, task.getName());
        cv.put(COLUMN_ASSIGNED_DATE, task.getAssignedDate());
        cv.put(COLUMN_DUEDATE, task.getDueDate());
        cv.put(COLUMN_DUETIME, task.getDueTime());
        cv.put(COLUMN_DESCRIPTION, task.getDescription());
        cv.put(COLUMN_PRIORITY, task.getPriority());
        cv.put(COLUMN_COMPLETE, task.isComplete());

        int success = db.update(TASK_TABLE, cv, "key=?", new String[]{String.valueOf(task.getKey())});
        db.close();
        return success > 0;
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
                boolean complete = cursor.getInt(8) == 1;

                Task newTask = new Task(name, assignedDate, dueDate, dueTime, description, priority, complete, key);
                //if (keyList.contains(newTask.getKey())) {
                //    continue;
                //}

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

    public List<Task> getTodoList() {
        List<Task> returnList = new ArrayList<>();
        List<Integer> keyList = new ArrayList<>(); // so we don't store dupes

        // get data from the database
        String queryString = "SELECT * FROM " + TASK_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        Calendar date = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String curDate = sdf.format(date.getTime());
        String[] cDate = curDate.split("-");
        int curYear = Integer.parseInt(cDate[0]);
        int curMonth = Integer.parseInt(cDate[1]);
        int curDay = Integer.parseInt(cDate[2]);

        // move to the first result. If it is true then there is at least 1 value
        if(cursor.moveToFirst()) {
            // loop through cursor and create new food objects and put in return list
            do{
                boolean complete = cursor.getInt(8) == 1;
                String dueDate = cursor.getString(4);
                String[] dDate = dueDate.split("-");
                int dueYear = Integer.parseInt(dDate[0]);
                int dueMonth = Integer.parseInt(dDate[1]);
                int dueDay = Integer.parseInt(dDate[2]);
                if (!complete && (dueYear >= curYear)) { // Check if task is not complete and at least the current year
                    if ((dueMonth > curMonth) || (dueMonth == curMonth && dueDay >= curDay)) { // Check if task is at least the current date
                        int key = cursor.getInt(1);
                        String name = cursor.getString(2);
                        String assignedDate = cursor.getString(3);
                        String dueTime = cursor.getString(5);
                        String description = cursor.getString(6);
                        int priority = cursor.getInt(7);

                        Task newTask = new Task(name, assignedDate, dueDate, dueTime, description, priority, complete, key);
                        //if (keyList.contains(newTask.getKey())) {
                        //    continue;
                        //}

                        keyList.add(newTask.getKey());
                        returnList.add(newTask);
                    }
                }
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
                dueDate = cursor.getString(4);
                complete = cursor.getInt(8) == 1;
                if (dueDate.equals(date) && !complete) {
                    key = cursor.getInt(1);
                    name = cursor.getString(2);
                    assignedDate = cursor.getString(3);
                    dueTime = cursor.getString(5);
                    description = cursor.getString(6);
                    priority = cursor.getInt(7);

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
                dueDate = cursor.getString(4);
                complete = cursor.getInt(8) == 1;
                if (dueDate.equals(date) && complete) {
                    key = cursor.getInt(1);
                    name = cursor.getString(2);
                    assignedDate = cursor.getString(3);
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

    public boolean modifyComplete(Task task, boolean bool) {
        // get data from the database
        String queryString = "SELECT * FROM " + TASK_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();

        // Find user
        int key;
        String name;
        String assignedDate;
        String dueDate;
        String dueTime;
        String description;
        int priority;
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            // Loop through results and create new customer objects
            do {
                key = cursor.getInt(1);
                if (key == task.getKey()) {
                    name = cursor.getString(2);
                    assignedDate = cursor.getString(3);
                    dueDate = cursor.getString(4);
                    dueTime = cursor.getString(5);
                    description = cursor.getString(6);
                    priority = cursor.getInt(7);

                    ContentValues cv = new ContentValues();
                    cv.put(COLUMN_KEY, key);
                    cv.put(COLUMN_NAME, name);
                    cv.put(COLUMN_ASSIGNED_DATE, assignedDate);
                    cv.put(COLUMN_DUEDATE, dueDate);
                    cv.put(COLUMN_DUETIME, dueTime);
                    cv.put(COLUMN_DESCRIPTION, description);
                    cv.put(COLUMN_PRIORITY, priority);
                    cv.put(COLUMN_COMPLETE, bool);

                    String[] whereArgs = {String.valueOf(task.getKey())};
                    int success = db.update(TASK_TABLE, cv, "keyid=?", whereArgs);
                    if (success > 0) {
                        db.close();
                        cursor.close();
                        return true;
                    }
                    //break;
                }
            } while (cursor.moveToNext());
        } else {
            // Failed
        }
        db.close();
        cursor.close();
        return false;
    }
}
