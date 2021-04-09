package com.example.ultratracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.List;

public class ExerciseDatabaseHelper extends SQLiteOpenHelper {
    public static final String EXERCISE_TABLE = "EXERCISE_TABLE";
    public static final String COLUMN_EXERCISE_ID = "EXERCISE_ID";
    public static final String COLUMN_EXERCISE_KEY = "EXERCISE_KEY";
    public static final String COLUMN_EXERCISE_TYPE = "EXERCISE_TYPE";
    public static final String COLUMN_EXERCISE_DATE = "EXERCISE_DATE";
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
                COLUMN_EXERCISE_KEY + " INT, " + COLUMN_EXERCISE_TYPE + " TEXT, " + COLUMN_EXERCISE_DATE + " Text, " + COLUMN_EXERCISE_DURATION + " Text, " +
                COLUMN_EXERCISE_CALS + " INT) ";
        String createWeightliftingTableStatement = "CREATE TABLE " + WEIGHTLIFTING_TABLE + " (" + COLUMN_WEIGHTLIFTING_ID + " INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_WEIGHTLIFTING_KEY + " INT, " + COLUMN_WEIGHTLIFTING_NAME + " TEXT, " + COLUMN_WEIGHTLIFTING_SETS + " INT, " + COLUMN_WEIGHTLIFTING_REPS + " INT, " +
                COLUMN_WEIGHTLIFTING_WEIGHT + " INT " + ")";
        String createRideTableStatement = "CREATE TABLE " + RIDE_TABLE + " (" + COLUMN_RIDE_ID + " INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RIDE_KEY + " INT, " + COLUMN_RIDE_DISTANCE + " INT, " + COLUMN_RIDE_PACE + " INT " + ")";
        String createRunTableStatement = "CREATE TABLE " + RUN_TABLE + " (" + COLUMN_RUN_ID + " INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RUN_KEY + " INT, " + COLUMN_RUN_DISTANCE + " INT, " + COLUMN_RUN_PACE + " INT " + ")";
        db.execSQL(createExerciseTableStatement);
        db.execSQL(createWeightliftingTableStatement);
        db.execSQL(createRideTableStatement);
        db.execSQL(createRunTableStatement);
    }

    // this is called if the database version number changes. Prevents db from breaking
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}


    public List<Exercise> getExercisesByDate(String date) {
        return null;
    }

    public boolean addWeightliftingWorkout(WeightliftingWorkout ww) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EXERCISE_KEY, ww.getKey());
        cv.put(COLUMN_EXERCISE_TYPE, "Weightlifting");
        cv.put(COLUMN_EXERCISE_DATE, ww.getCompletedDate());
        cv.put(COLUMN_EXERCISE_DURATION, ww.getCompletedTime());
        cv.put(COLUMN_EXERCISE_CALS, ww.getCaloriesBurned());
        for(Weightlifting w : ww.getExerciseList()) {
            addWeightlifting(w, ww.getKey());
        }
        long insert = db.insert(EXERCISE_TABLE, null, cv);
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
        cv1.put(COLUMN_EXERCISE_DURATION, ride.getCompletedTime());
        cv1.put(COLUMN_EXERCISE_CALS, ride.getCaloriesBurned());

        return db.insert(EXERCISE_TABLE, null, cv) != -1;
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
        cv1.put(COLUMN_EXERCISE_DURATION, run.getCompletedTime());
        cv1.put(COLUMN_EXERCISE_CALS, run.getCaloriesBurned());

        return db.insert(EXERCISE_TABLE, null, cv) != -1;
    }

    public boolean removeWeightliftingWorkout(WeightliftingWorkout ww) {
        return false;
    }

    public boolean removeRide(Ride ride) {
        return false;
    }

    public boolean removeRun(Run run) {
        return false;
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
        return null;
    }
}