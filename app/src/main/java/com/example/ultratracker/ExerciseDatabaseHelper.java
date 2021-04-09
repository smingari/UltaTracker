package com.example.ultratracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ExerciseDatabaseHelper extends SQLiteOpenHelper {
    public static final String EXERCISE_TABLE = "EXERCISE_TABLE";
    public static final String COLUMN_EXERCISE_ID = "EXERCISE_ID";
    public static final String COLUMN_EXERCISE_KEY = "EXERCISE_KEY";
    public static final String COLUMN_EXERCISE_TYPE = "EXERCISE_TYPE";
    public static final String COLUMN_EXERCISE_DATE = "EXERCISE_DATE";
    public static final String COLUMN_EXERCISE_TIME = "EXERCISE_TIME";
    public static final String COLUMN_EXERCISE_DURATION = "EXERCISE_DURATION";
    public static final String COLUMN_EXERCISE_CALS = "EXERCISE_CALS";

    public static final String WEIGHTLIFTING_TABLE = "WEIGHTLIFTING_TABLE";
    public static final String COLUMN_WEIGHTLIFTING_ID = "WEIGHTLIFTING_ID";
    public static final String COLUMN_WEIGHTLIFTING_KEY = "WEIGHTLIFTING_KEY";
    public static final String COLUMN_WEIGHTLIFTING_NAME = "WEIGHTLIFTING_NAME";
    public static final String COLUMN_WEIGHTLIFTING_SETS = "WEIGHTLIFTING_SETS";
    public static final String COLUMN_WEIGHTLIFTING_REPS = "WEIGHTLIFTING_REPS";
    public static final String COLUMN_WEIGHTLIFTING_WEIGHT = "WEIGHTLIFTING_WEIGHT";

    public static final String RIDE_TABLE = "RIDE_TABLE";
    public static final String COLUMN_RIDE_ID = "RIDE_ID";
    public static final String COLUMN_RIDE_KEY = "RIDE_KEY";
    public static final String COLUMN_RIDE_DISTANCE = "RIDE_DISTANCE";
    public static final String COLUMN_RIDE_PACE = "RIDE_PACE";

    public static final String RUN_TABLE = "RUN_TABLE";
    public static final String COLUMN_RUN_ID = "RUN_ID";
    public static final String COLUMN_RUN_KEY = "RUN_KEY";
    public static final String COLUMN_RUN_DISTANCE = "RUN_DISTANCE";
    public static final String COLUMN_RUN_PACE = "RUN_PACE";


    public ExerciseDatabaseHelper(@Nullable Context context) {
        super(context, "exercise.db", null, 1);
    }

    // Called first time a database is accessed
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createExerciseTableStatement = "CREATE TABLE " + EXERCISE_TABLE + " (" + COLUMN_EXERCISE_ID + " INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EXERCISE_KEY + " INT, " + COLUMN_EXERCISE_TYPE + " TEXT, " + COLUMN_EXERCISE_DATE + " Text, " + COLUMN_EXERCISE_TIME + " Text, " +
                COLUMN_EXERCISE_TIME + " INT, " + COLUMN_EXERCISE_CALS + " INT) ";
        String createWeightliftingTableStatement = "CREATE TABLE " + WEIGHTLIFTING_TABLE + " (" + COLUMN_WEIGHTLIFTING_ID + " INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_WEIGHTLIFTING_KEY + " INT, " + COLUMN_WEIGHTLIFTING_NAME + " TEXT, " + COLUMN_WEIGHTLIFTING_SETS + " INT, " + COLUMN_WEIGHTLIFTING_REPS + " INT, " +
                COLUMN_WEIGHTLIFTING_WEIGHT + " INT " + ")";
        String createRideTableStatement = "CREATE TABLE " + RIDE_TABLE + " (" + COLUMN_RIDE_ID + " INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RIDE_KEY + " INT, " + COLUMN_RIDE_DISTANCE + " DOUBLE, " + COLUMN_RIDE_PACE + " DOUBLE " + ")";
        String createRunTableStatement = "CREATE TABLE " + RUN_TABLE + " (" + COLUMN_RUN_ID + " INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RUN_KEY + " INT, " + COLUMN_RUN_DISTANCE + " DOUBLE, " + COLUMN_RUN_PACE + " DOUBLE " + ")";
        db.execSQL(createExerciseTableStatement);
        db.execSQL(createWeightliftingTableStatement);
        db.execSQL(createRideTableStatement);
        db.execSQL(createRunTableStatement);
    }

    // this is called if the database version number changes. Prevents db from breaking
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}


    public List<Exercise> getExercisesByDate(String date) {
        List<Exercise> returnList = new ArrayList<>();

        // get data from the database
        String queryString = "SELECT * FROM " + EXERCISE_TABLE + " WHERE " + COLUMN_EXERCISE_DATE + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, new String[] {date});

        int key;
        String exerciseType;
        String completedDate;
        String completedTime;
        int duration;
        int caloriesBurned;

        // move to the first result. If it is true then there is at least 1 value
        if (cursor.moveToFirst()) {
            do {
                key = cursor.getInt(1);
                exerciseType = cursor.getString(2);
                completedDate = cursor.getString(3);
                completedTime = cursor.getString(4);
                duration = cursor.getInt(5);
                caloriesBurned = cursor.getInt(6);

                switch (exerciseType) {
                    case "Ride":
                        Ride ride = getRide(key, completedDate, completedTime, duration, caloriesBurned);
                        returnList.add(ride);
                        break;
                    case "Run":
                        Run run = getRun(key, completedDate, completedTime, duration, caloriesBurned);
                        returnList.add(run);
                        break;
                    case "Weightlifting":
                        WeightliftingWorkout ww = getWeightliftingWorkout(key, completedTime, completedDate, duration, caloriesBurned);
                        returnList.add(ww);
                        break;
                    default:
                        Exercise e = new Exercise(exerciseType, completedDate, completedTime, duration, caloriesBurned);
                        e.setKey(key);
                        break;
                }
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnList;
    }

    public Ride getRide(int key, String completedDate, String completedTime, int duration, int caloriesBurned) {
        Ride ride = null;
        String queryString = "SELECT * FROM " + RIDE_TABLE + " WHERE " + COLUMN_RIDE_KEY + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, new String[] {String.valueOf(key)});

        double distance;
        double pace;

        if (cursor.moveToFirst()) {
            distance = cursor.getDouble(2);
            pace = cursor.getDouble(3);
            ride = new Ride(completedDate, completedTime, duration, caloriesBurned, distance, pace);
            ride.setKey(key);
        }

        cursor.close();
        db.close();
        return ride;
    }

    public Run getRun(int key, String completedDate, String completedTime, int duration, int caloriesBurned) {
        Run run = null;
        String queryString = "SELECT * FROM " + RUN_TABLE + " WHERE " + COLUMN_RUN_KEY + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, new String[] {String.valueOf(key)});

        double distance;
        double pace;

        if (cursor.moveToFirst()) {
            distance = cursor.getDouble(2);
            pace = cursor.getDouble(3);
            run = new Run(completedDate, completedTime, duration, caloriesBurned, distance, pace);
            run.setKey(key);
        }

        cursor.close();
        db.close();
        return run;
    }

    public WeightliftingWorkout getWeightliftingWorkout(int key, String completedDate, String completedTime, int duration, int caloriesBurned) {
        WeightliftingWorkout ww;
        List<Weightlifting> wlList = getWeightlifting(key);
        ww = new WeightliftingWorkout(completedDate, completedTime, duration, caloriesBurned, wlList);
        ww.setKey(key);
        return ww;
    }

    public List<Weightlifting> getWeightlifting(int key) {
        ArrayList<Weightlifting> wlList = new ArrayList<>();
        Weightlifting curWeightlifting;
        String queryString = "SELECT * FROM " + WEIGHTLIFTING_TABLE + " WHERE " + COLUMN_WEIGHTLIFTING_KEY + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, new String[] {String.valueOf(key)});

        String name;
        int sets;
        int reps;
        int weight;

        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(2);
                sets = cursor.getInt(3);
                reps = cursor.getInt(4);
                weight = cursor.getInt(5);
                curWeightlifting = new Weightlifting(name, sets, reps, weight);
                wlList.add(curWeightlifting);
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return wlList;
    }

    public boolean addWeightliftingWorkout(WeightliftingWorkout ww) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EXERCISE_KEY, ww.getKey());
        cv.put(COLUMN_EXERCISE_TYPE, "Weightlifting");
        cv.put(COLUMN_EXERCISE_DATE, ww.getCompletedDate());
        cv.put(COLUMN_EXERCISE_TIME, ww.getCompletedTime());
        cv.put(COLUMN_EXERCISE_DURATION, ww.getDuration());
        cv.put(COLUMN_EXERCISE_CALS, ww.getCaloriesBurned());
        for(Weightlifting w : ww.getExerciseList()) {
            addWeightlifting(w, ww.getKey());
        }
        long insert = db.insert(EXERCISE_TABLE, null, cv);
        db.close();
        return insert != -1;
    }

    public boolean addWeightlifting(Weightlifting w, int key) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_WEIGHTLIFTING_KEY, key);
        cv.put(COLUMN_WEIGHTLIFTING_NAME, w.getExerciseName());
        cv.put(COLUMN_WEIGHTLIFTING_SETS, w.getSets());
        cv.put(COLUMN_WEIGHTLIFTING_REPS, w.getReps());
        cv.put(COLUMN_WEIGHTLIFTING_WEIGHT, w.getWeight());

        long insert = db.insert(WEIGHTLIFTING_TABLE, null, cv);
        db.close();
        return insert != -1;
    }

    public boolean addRide(Ride ride) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_RIDE_KEY, ride.getKey());
        cv.put(COLUMN_RIDE_DISTANCE, ride.getDistance());
        cv.put(COLUMN_RIDE_PACE, ride.getPace());
        if(db.insert(RIDE_TABLE, null, cv) == -1) return false;

        ContentValues cv1 = new ContentValues();

        cv1.put(COLUMN_EXERCISE_KEY, ride.getKey());
        cv1.put(COLUMN_EXERCISE_TYPE, "Ride");
        cv1.put(COLUMN_EXERCISE_DATE, ride.getCompletedDate());
        cv1.put(COLUMN_EXERCISE_TIME, ride.getCompletedTime());
        cv1.put(COLUMN_EXERCISE_DURATION, ride.getDuration());
        cv1.put(COLUMN_EXERCISE_CALS, ride.getCaloriesBurned());

        long insert = db.insert(EXERCISE_TABLE, null, cv);
        db.close();
        return insert != -1;
    }

    public boolean addRun(Run run) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_RUN_KEY, run.getKey());
        cv.put(COLUMN_RUN_DISTANCE, run.getDistance());
        cv.put(COLUMN_RUN_PACE, run.getPace());
        if(db.insert(RUN_TABLE, null, cv) == -1) return false;

        ContentValues cv1 = new ContentValues();

        cv1.put(COLUMN_EXERCISE_KEY, run.getKey());
        cv1.put(COLUMN_EXERCISE_TYPE, "Run");
        cv1.put(COLUMN_EXERCISE_DATE, run.getCompletedDate());
        cv1.put(COLUMN_EXERCISE_TIME, run.getCompletedTime());
        cv1.put(COLUMN_EXERCISE_DURATION, run.getDuration());
        cv1.put(COLUMN_EXERCISE_CALS, run.getCaloriesBurned());

        long insert = db.insert(EXERCISE_TABLE, null, cv);
        db.close();
        return insert != -1;
    }

    public boolean removeWeightliftingWorkout(WeightliftingWorkout ww) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(WEIGHTLIFTING_TABLE, COLUMN_WEIGHTLIFTING_KEY + "=", new String[]{String.valueOf(ww.getKey())});
        final int delete = db.delete(EXERCISE_TABLE, COLUMN_EXERCISE_KEY + "=", new String[] {String.valueOf(ww.getKey())});
        db.close();
        return delete > 0;
    }

    public boolean removeRide(Ride ride) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(RIDE_TABLE, COLUMN_RIDE_KEY + "=", new String[]{String.valueOf(ride.getKey())});
        final int delete = db.delete(EXERCISE_TABLE, COLUMN_EXERCISE_KEY + "=", new String[] {String.valueOf(ride.getKey())});
        db.close();
        return delete > 0;
    }

    public boolean removeRun(Run run) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(RUN_TABLE, COLUMN_RUN_KEY + "=", new String[]{String.valueOf(run.getKey())});
        final int delete = db.delete(EXERCISE_TABLE, COLUMN_EXERCISE_KEY + "=", new String[] {String.valueOf(run.getKey())});
        db.close();
        return delete > 0;
    }

    public boolean editWeightliftingWorkout(WeightliftingWorkout ww) {
        return false;
    }

    public boolean editRide(Ride ride) {
        return false;
    }

    public boolean editRun(Run run) {
        return false;
    }

    public List<Exercise> getAll() {
        List<Exercise> returnList = new ArrayList<>();

        // get data from the database
        String queryString = "SELECT * FROM " + EXERCISE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        int key;
        String exerciseType;
        String completedDate;
        String completedTime;
        int duration;
        int caloriesBurned;

        // move to the first result. If it is true then there is at least 1 value
        if (cursor.moveToFirst()) {
            do {
                key = cursor.getInt(1);
                exerciseType = cursor.getString(2);
                completedDate = cursor.getString(3);
                completedTime = cursor.getString(4);
                duration = cursor.getInt(5);
                caloriesBurned = cursor.getInt(6);

                switch (exerciseType) {
                    case "Ride":
                        Ride ride = getRide(key, completedDate, completedTime, duration, caloriesBurned);
                        returnList.add(ride);
                        break;
                    case "Run":
                        Run run = getRun(key, completedDate, completedTime, duration, caloriesBurned);
                        returnList.add(run);
                        break;
                    case "Weightlifting":
                        WeightliftingWorkout ww = getWeightliftingWorkout(key, completedTime, completedDate, duration, caloriesBurned);
                        returnList.add(ww);
                        break;
                    default:
                        Exercise e = new Exercise(exerciseType, completedDate, completedTime, duration, caloriesBurned);
                        e.setKey(key);
                        break;
                }
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnList;
    }
}