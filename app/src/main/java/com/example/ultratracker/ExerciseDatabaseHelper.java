package com.example.ultratracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
        db.execSQL(createExerciseTableStatement);
        db.execSQL(createWeightliftingTableStatement);
    }

    // this is called if the database version number changes. Prevents db from breaking
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public boolean addWeightliftingWorkout(WeightliftingWorkout ww) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EXERCISE_KEY, ww.getKey());
        cv.put(COLUMN_EXERCISE_TYPE, "Weightlifting");
        cv.put(COLUMN_EXERCISE_DATE, ww.getCompletedDate());
        cv.put(COLUMN_EXERCISE_DURATION, ww.getDuration());
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
}