package com.example.ultratracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
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
    public static final String COLUMN_WORKOUT_KEY = "WORKOUT_KEY";
    public static final String COLUMN_WEIGHTLIFTING_KEY = "WEIGHTLIFTING_KEY";
    public static final String COLUMN_WEIGHTLIFTING_NAME = "WEIGHTLIFTING_NAME";
    public static final String COLUMN_WORKOUT_NAME = "WORKOUT_NAME";
    public static final String COLUMN_WEIGHTLIFTING_SETS = "WEIGHTLIFTING_SETS";
    public static final String COLUMN_WEIGHTLIFTING_REPS = "WEIGHTLIFTING_REPS";
    public static final String COLUMN_WEIGHTLIFTING_WEIGHT = "WEIGHTLIFTING_WEIGHT";
    public static final String COLUMN_WEIGHTLIFTING_DATE = "WEIGHTLIFTING_DATE";

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

    public static final String WEIGHT_TABLE = "WEIGHT_TABLE";
    public static final String COLUMN_WEIGHT_ID = "WEIGHT_ID";
    public static final String COLUMN_WEIGHT_KEY = "WEIGHT_KEY";
    public static final String COLUMN_WEIGHT_DATE = "WEIGHT_DATE";
    public static final String COLUMN_WEIGHT_VALUE = "WEIGHT_VALUE";



    public ExerciseDatabaseHelper(@Nullable Context context) {
        super(context, "exercise.db", null, 1);
    }

    // Called first time a database is accessed
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createExerciseTableStatement = "CREATE TABLE IF NOT EXISTS " + EXERCISE_TABLE + " (" + COLUMN_EXERCISE_ID + " INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EXERCISE_KEY + " INT, " + COLUMN_EXERCISE_TYPE + " TEXT, " + COLUMN_EXERCISE_DATE + " Text, " + COLUMN_EXERCISE_TIME + " Text, " +
                COLUMN_EXERCISE_DURATION + " INT, " + COLUMN_EXERCISE_CALS + " INT) ";

        String createWeightliftingTableStatement = "CREATE TABLE IF NOT EXISTS " + WEIGHTLIFTING_TABLE + " (" + COLUMN_WEIGHTLIFTING_ID + " INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_WEIGHTLIFTING_KEY + " INT, " + COLUMN_WORKOUT_KEY + " INT, " + COLUMN_WEIGHTLIFTING_NAME + " TEXT, " + COLUMN_WORKOUT_NAME + " TEXT, " + COLUMN_WEIGHTLIFTING_SETS + " INT, " +
                COLUMN_WEIGHTLIFTING_REPS + " INT, " + COLUMN_WEIGHTLIFTING_WEIGHT + " INT, " + COLUMN_WEIGHTLIFTING_DATE + " INT " + ")";


        String createRideTableStatement = "CREATE TABLE IF NOT EXISTS " + RIDE_TABLE + " (" + COLUMN_RIDE_ID + " INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RIDE_KEY + " INT, " + COLUMN_RIDE_DISTANCE + " DOUBLE, " + COLUMN_RIDE_PACE + " DOUBLE " + ")";
        String createRunTableStatement = "CREATE TABLE IF NOT EXISTS " + RUN_TABLE + " (" + COLUMN_RUN_ID + " INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RUN_KEY + " INT, " + COLUMN_RUN_DISTANCE + " DOUBLE, " + COLUMN_RUN_PACE + " DOUBLE " + ")";
        String createWeightTableStatement = "CREATE TABLE IF NOT EXISTS " + WEIGHT_TABLE + " (" + COLUMN_WEIGHT_ID+ " INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_WEIGHT_KEY + " INT, " + COLUMN_WEIGHT_DATE + " TEXT, " + COLUMN_WEIGHT_VALUE + " DOUBLE " + ")";

        db.execSQL(createExerciseTableStatement);
        db.execSQL(createWeightliftingTableStatement);
        db.execSQL(createRideTableStatement);
        db.execSQL(createRunTableStatement);
        db.execSQL(createWeightTableStatement);
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
                        //WeightliftingWorkout ww = getWeightliftingWorkout(key, completedTime, completedDate, duration, caloriesBurned);
                       // returnList.add(ww);
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

    // TODO Fix in Iteration 3
//    public WeightliftingWorkout getWeightliftingWorkout(int key, String completedDate, String completedTime, int duration, int caloriesBurned) {
//        WeightliftingWorkout ww;
//        List<Weightlifting> wlList = getWeightlifting(key);
//        ww = new WeightliftingWorkout(completedDate, completedTime, duration, caloriesBurned, wlList);
//        ww.setKey(key);
//        return ww;
//    }
//
    public List<Weightlifting> getWorkout(int workout_key) {
        List<Weightlifting> wlList = new ArrayList<>();
        Weightlifting curWeightlifting;
        String queryString = "SELECT * FROM " + WEIGHTLIFTING_TABLE + " WHERE " + COLUMN_WORKOUT_KEY + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, new String[] {String.valueOf(workout_key)});

        int key;
        String name;
        int sets;
        int reps;
        int weight;
        String date;

        if (cursor.moveToFirst()) {
            do {
                key = cursor.getInt(1);
                name = cursor.getString(3);
                sets = cursor.getInt(5);
                reps = cursor.getInt(6);
                weight = cursor.getInt(7);
                date = cursor.getString(8);

                curWeightlifting = new Weightlifting(name, sets, reps, weight, date, key);
                wlList.add(curWeightlifting);
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return wlList;
    }

    public List<Workout> getWorkoutsByDate(String qDate) {
        List<Weightlifting> liftList = new ArrayList<>();
        List<Workout> woList;

        // get data from the database
        String queryString = "SELECT * FROM " + WEIGHTLIFTING_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        int key;
        int wKey;
        String name;
        String woName;
        int sets;
        int reps;
        int weight;
        String date;
        // move to the first result. If it is true then there is at least 1 value
        if (cursor.moveToFirst()) {
            // loop through cursor and add all entries with correct date to foodList
            do {
                date = cursor.getString(8);
                if (date.equals(qDate)) {
                    key = cursor.getInt(1);
                    wKey = cursor.getInt(2);
                    name = cursor.getString(3);
                    woName = cursor.getString(4);
                    sets = cursor.getInt(5);
                    reps = cursor.getInt(6);
                    weight = cursor.getInt(7);

                    Weightlifting lift = new Weightlifting(name, sets, reps, weight, woName, date, key, wKey);
                    liftList.add(lift);
                }
            } while (cursor.moveToNext());
        }
        else { }

        // Send liftList to be organized into workouts
        woList = liftToWorkout(liftList);
        cursor.close();
        db.close();
        return woList;
    }

    public List<Workout> liftToWorkout (List<Weightlifting> liftList) {
        HashMap<Integer, List<Weightlifting>> hashMap = new HashMap<Integer, List<Weightlifting>>();
        List<Workout> woList = new ArrayList<>();
        List<Integer> woKeys = new ArrayList<>();

        for (Weightlifting lift: liftList) {
            if (!hashMap.containsKey(lift.getWorkoutKey())) {
                List<Weightlifting> list = new ArrayList<>();
                list.add(lift);
                hashMap.put(lift.getWorkoutKey(), list);
                woKeys.add(lift.getWorkoutKey());
            } else {
                hashMap.get(lift.getWorkoutKey()).add(lift);
            }
        }

        for (int i = 0; i < woKeys.size(); i++) {
            // Skip if key = 0 (this is part of the lift bank)
            if (woKeys.get(i) == 0) { continue; }

            List<Weightlifting> lifts = hashMap.get(woKeys.get(i));

            int totalSets = 0;
            int totalReps = 0;
            for (Weightlifting lift: lifts) {
                totalSets += lift.getSets();
                totalReps += lift.getReps();
            }

            Workout wo = new Workout(lifts.get(0).getWorkoutName(), lifts, totalSets, totalReps, lifts.get(0).getDate(), lifts.get(0).getWorkoutKey());
            woList.add(wo);
        }

        return woList;
    }

    public List<Weightlifting> getAllWeightlifting() {
        List<Weightlifting> wlList = new ArrayList<>();
        Weightlifting curWeightlifting;
        String queryString = "SELECT * FROM " + WEIGHTLIFTING_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        int key;
        String name;
        int sets;
        int reps;
        int weight;
        String date;

        if (cursor.moveToFirst()) {
            do {
                key = cursor.getInt(1);
                name = cursor.getString(3);
                sets = cursor.getInt(5);
                reps = cursor.getInt(6);
                weight = cursor.getInt(7);
                date = cursor.getString(8);
                curWeightlifting = new Weightlifting(name, sets, reps, weight, date, key);
                wlList.add(curWeightlifting);
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return wlList;
    }

    public List<Weightlifting> getLiftBank() {
        List<Weightlifting> wlList = new ArrayList<>();
        Weightlifting curWeightlifting;
        String queryString = "SELECT * FROM " + WEIGHTLIFTING_TABLE  + " WHERE " + COLUMN_WORKOUT_KEY + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, new String[] {String.valueOf(0)});

        int key;
        String name;
        int sets;
        int reps;
        int weight;
        String date;

        if (cursor.moveToFirst()) {
            do {
                key = cursor.getInt(1);
                name = cursor.getString(3);
                sets = cursor.getInt(5);
                reps = cursor.getInt(6);
                weight = cursor.getInt(7);
                date = cursor.getString(8);
                curWeightlifting = new Weightlifting(name, sets, reps, weight, date, key);
                wlList.add(curWeightlifting);
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return wlList;
    }
//
//    public boolean addWeightliftingWorkout(WeightliftingWorkout ww) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put(COLUMN_EXERCISE_KEY, ww.getKey());
//        cv.put(COLUMN_EXERCISE_TYPE, "Weightlifting");
//        cv.put(COLUMN_EXERCISE_DATE, ww.getCompletedDate());
//        cv.put(COLUMN_EXERCISE_TIME, ww.getCompletedTime());
//        cv.put(COLUMN_EXERCISE_DURATION, ww.getDuration());
//        cv.put(COLUMN_EXERCISE_CALS, ww.getCaloriesBurned());
//        for(Weightlifting w : ww.getExerciseList()) {
//            addWeightlifting(w, ww.getKey());
//        }
//        long insert = db.insert(EXERCISE_TABLE, null, cv);
//        db.close();
//        return insert != -1;
//    }
//

    public boolean checkByNameRepsWeight(String name, int reps, int weight, int key) {
        // get data from the database
        String queryString = "SELECT * FROM " + WEIGHTLIFTING_TABLE + " WHERE " + COLUMN_WEIGHTLIFTING_NAME + " = ? AND "
                + COLUMN_WEIGHTLIFTING_REPS + " = ? AND " + COLUMN_WEIGHTLIFTING_WEIGHT + " = ? AND " + COLUMN_WORKOUT_KEY + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, new String[] {name, String.valueOf(reps), String.valueOf(weight), String.valueOf(key)});

        // move to the first result. If it is true then there is at least 1 value
        if (cursor.moveToFirst()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addWeightlifting(Weightlifting w, int key) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_WEIGHTLIFTING_KEY, w.getKey());
        cv.put(COLUMN_WORKOUT_KEY, key);
        cv.put(COLUMN_WEIGHTLIFTING_NAME, w.getName());
        cv.put(COLUMN_WORKOUT_NAME, w.getWorkoutName());
        cv.put(COLUMN_WEIGHTLIFTING_SETS, w.getSets());
        cv.put(COLUMN_WEIGHTLIFTING_REPS, w.getReps());
        cv.put(COLUMN_WEIGHTLIFTING_WEIGHT, w.getWeight());
        cv.put(COLUMN_WEIGHTLIFTING_DATE, w.getDate());

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

        long insert = db.insert(EXERCISE_TABLE, null, cv1);
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

        long insert = db.insert(EXERCISE_TABLE, null, cv1);
        db.close();
        return insert != -1;
    }

    public boolean addWeight(Weight w){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_WEIGHT_KEY, w.getKey());
        cv.put(COLUMN_WEIGHT_DATE, String.valueOf(w.getDate()));
        cv.put(COLUMN_WEIGHT_VALUE, w.getWeight());
        long insert = db.insert(WEIGHT_TABLE, null, cv);

        db.close();
        return insert != -1;
    }

    public List<Weight> getAllWeights() {
        String queryString = "SELECT * FROM " + WEIGHT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        int key;
        String date;
        double weightValue;
        ArrayList<Weight> weightList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                key = cursor.getInt(1);
                date = cursor.getString(2);
                weightValue = cursor.getDouble(3);
                weightList.add(new Weight(weightValue, date, key));
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return weightList;
    }

    public boolean removeWeight(Weight w){
        String whereClause = COLUMN_WEIGHT_KEY + "=?";
        SQLiteDatabase db = this.getWritableDatabase();
        final int delete = db.delete(WEIGHT_TABLE, whereClause, new String[] {String.valueOf(w.getKey())});
        db.close();
        return delete > 0;
    }

    public boolean removeWorkout(Workout wo) {
        // get data from the database
        String queryString = "DELETE FROM " + WEIGHTLIFTING_TABLE + " WHERE " + COLUMN_WORKOUT_KEY + " = " + wo.getKey();
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

    public boolean removeRide(Ride ride) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(RIDE_TABLE, COLUMN_RIDE_KEY + "=?", new String[]{String.valueOf(ride.getKey())});
        final int delete = db.delete(EXERCISE_TABLE, COLUMN_EXERCISE_KEY + "=?", new String[] {String.valueOf(ride.getKey())});
        db.close();
        return delete > 0;
    }

    public boolean removeRun(Run run) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(RUN_TABLE, COLUMN_RUN_KEY + "=?", new String[]{String.valueOf(run.getKey())});
        final int delete = db.delete(EXERCISE_TABLE, COLUMN_EXERCISE_KEY + "=?", new String[] {String.valueOf(run.getKey())});
        db.close();
        return delete > 0;
    }


    // TODO Fix in Iteration 3
//    public boolean editWeightliftingWorkout(WeightliftingWorkout ww) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(WEIGHTLIFTING_TABLE, COLUMN_WORKOUT_KEY + "=?", new String[]{String.valueOf(ww.getKey())});
//
//        for(Weightlifting w : ww.getExerciseList()) {
//            addWeightlifting(w, ww.getKey());
//        }
//
//        ContentValues cv = new ContentValues();
//
//        cv.put(COLUMN_EXERCISE_KEY, ww.getKey());
//        cv.put(COLUMN_EXERCISE_TYPE, "Weightlifting");
//        cv.put(COLUMN_EXERCISE_DATE, ww.getCompletedDate());
//        cv.put(COLUMN_EXERCISE_TIME, ww.getCompletedTime());
//        cv.put(COLUMN_EXERCISE_DURATION, ww.getDuration());
//        cv.put(COLUMN_EXERCISE_CALS, ww.getCaloriesBurned());
//
//        long update = db.update(EXERCISE_TABLE, cv, COLUMN_EXERCISE_KEY + "=?", new String[]{String.valueOf(ww.getKey())});
//        db.close();
//        return update != -1;
//    }

    public boolean editRide(Ride ride) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_RIDE_KEY, ride.getKey());
        cv.put(COLUMN_RIDE_DISTANCE, ride.getDistance());
        cv.put(COLUMN_RIDE_PACE, ride.getPace());
        if(db.update(RIDE_TABLE, cv, COLUMN_RIDE_KEY + "=?", new String[]{String.valueOf(ride.getKey())}) == -1) return false;

        ContentValues cv1 = new ContentValues();

        cv1.put(COLUMN_EXERCISE_KEY, ride.getKey());
        cv1.put(COLUMN_EXERCISE_TYPE, "Ride");
        cv1.put(COLUMN_EXERCISE_DATE, ride.getCompletedDate());
        cv1.put(COLUMN_EXERCISE_TIME, ride.getCompletedTime());
        cv1.put(COLUMN_EXERCISE_DURATION, ride.getDuration());
        cv1.put(COLUMN_EXERCISE_CALS, ride.getCaloriesBurned());

        long update = db.update(EXERCISE_TABLE, cv1, COLUMN_EXERCISE_KEY + "=?", new String[]{String.valueOf(ride.getKey())});
        db.close();
        return update != -1;
    }

    public boolean editRun(Run run) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_RUN_KEY, run.getKey());
        cv.put(COLUMN_RUN_DISTANCE, run.getDistance());
        cv.put(COLUMN_RUN_PACE, run.getPace());
        if(db.update(RUN_TABLE, cv, COLUMN_RUN_KEY + "=?", new String[]{String.valueOf(run.getKey())}) == -1) return false;

        ContentValues cv1 = new ContentValues();

        cv1.put(COLUMN_EXERCISE_KEY, run.getKey());
        cv1.put(COLUMN_EXERCISE_TYPE, "Run");
        cv1.put(COLUMN_EXERCISE_DATE, run.getCompletedDate());
        cv1.put(COLUMN_EXERCISE_TIME, run.getCompletedTime());
        cv1.put(COLUMN_EXERCISE_DURATION, run.getDuration());
        cv1.put(COLUMN_EXERCISE_CALS, run.getCaloriesBurned());

        long update = db.update(EXERCISE_TABLE, cv1, COLUMN_EXERCISE_KEY + "=?", new String[]{String.valueOf(run.getKey())});
        db.close();
        return update != -1;
    }

    public boolean editLift(Weightlifting wl) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_WEIGHTLIFTING_KEY, wl.getKey());
        cv.put(COLUMN_WORKOUT_KEY, wl.getWorkoutKey());
        cv.put(COLUMN_WEIGHTLIFTING_NAME, wl.getName());
        cv.put(COLUMN_WORKOUT_NAME, wl.getName());
        cv.put(COLUMN_WEIGHTLIFTING_SETS, wl.getSets());
        cv.put(COLUMN_WEIGHTLIFTING_REPS, wl.getReps());
        cv.put(COLUMN_WEIGHTLIFTING_WEIGHT, wl.getWeight());
        cv.put(COLUMN_WEIGHTLIFTING_DATE, wl.getDate());

        long update = db.update(WEIGHTLIFTING_TABLE, cv, COLUMN_WEIGHTLIFTING_KEY + "=?", new String[]{String.valueOf(wl.getKey())});
        db.close();
        return update != -1;
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
//                        WeightliftingWorkout ww = getWeightliftingWorkout(key, completedTime, completedDate, duration, caloriesBurned);
//                        returnList.add(ww);
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