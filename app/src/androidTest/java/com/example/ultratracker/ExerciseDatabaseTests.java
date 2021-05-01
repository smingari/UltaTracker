package com.example.ultratracker;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) // Needed to test order of the keys
@RunWith(AndroidJUnit4.class)
public class ExerciseDatabaseTests {

    private Run r1, r2, r3, r4;
    private Ride ri1, ri2, ri3, ri4;
    private Weightlifting w1, w2, w3, w4;
    private Weightlifting ww1, ww2, ww3, ww4;  // for workout
    private Weight weight1, weight2;
    private LocalDate d1, d2, d3, d4;
    private ExerciseDatabaseHelper db;
    private Context appContext;
    private int cals1, cals2, cals3, cals4;
    private int dur1, dur2, dur3, dur4;
    private double dis1, dis2, dis3, dis4;
    private double p1, p2, p3, p4;
    private int sets, reps, weight;
    private String exerType1, exerType2;
    private List<Exercise> list1, list2, list3, list4;
    private List<Weightlifting> wList1, wList2;
    private double weightD1, weightD2;
    private String woName1, woName2, woName3, woName4;
    private List<Workout> lw1, lw2;
    private List<Weight> fatty;
    private LocalTime t1;


    @Before
    public void setUp() throws Exception {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = new ExerciseDatabaseHelper(appContext);

        d1 = LocalDate.of(2021, 4, 20); // format is 2021-04-20
        d2 = LocalDate.of(2021, 5, 21); // format is 2021-04-20
        d3 = LocalDate.of(2021, 6, 22); // format is 2021-04-20
        d4 = LocalDate.of(2021, 7, 23); // format is 2021-04-20
        t1 = LocalTime.of(10,14); // format is 10:14

        wList1 = new ArrayList<Weightlifting>();
        wList2 = new ArrayList<Weightlifting>();

        exerType1 = "Bench"; exerType2 = "Squat";
        sets = 5; reps = 10; weight = 100;
        cals1 = 100; cals2 = 200; cals3 = 300; cals4 = 400;
        dur1 = 5; dur2 = 10; dur3 = 15; dur4 = 20;
        dis1 = 1.02; dis2 = 3.15; dis3 = 10.2; dis4 = 15.5;
        p1 = 4.10; p2 = 7.9; p3 = 10.1; p4 = 9.1;
        weightD1 = 180.75; weightD2 = 196.75;

        woName1 = "Beach"; woName2 = "T90X"; woName3 = "HotCarl"; woName4 = "DirtySanchez";

        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        list4 = new ArrayList<>();

        r1 = new Run(d1, t1, dur1, cals1, dis1, p1);
        r2 = new Run(d2, t1, dur2, cals2, dis2, p2);
        r3 = new Run(d3, t1, dur3, cals3, dis3, p3);
        r4 = new Run(d4, t1, dur4, cals4, dis4, p4);

        ri1 = new Ride(d1, t1, dur1, cals1, dis1, p1);
        ri2 = new Ride(d2, t1, dur2, cals2, dis2, p2);
        ri3 = new Ride(d3, t1, dur3, cals3, dis3, p3);
        ri4 = new Ride(d4, t1, dur4, cals4, dis4, p4);

        w1 = new Weightlifting(exerType1, sets, reps, weight, d1);
        w2 = new Weightlifting(exerType2, sets, reps, weight, d2);
        w3 = new Weightlifting(exerType1, sets, reps, weight, d3);
        w4 = new Weightlifting(exerType2, sets, reps, weight, d4);

        wList1.add(w1);
        wList1.add(w2);

        wList2.add(w3);
        wList2.add(w4);

        ww1 = new Weightlifting(exerType1, sets, reps, weight, woName1, d1);
        ww2 = new Weightlifting(exerType2, sets, reps, weight, woName2, d2);
        ww3 = new Weightlifting(exerType1, sets, reps, weight, woName3, d3);
        ww4 = new Weightlifting(exerType2, sets, reps, weight, woName4, d4);

        weight1 = new Weight(weightD1, d1.toString());
        weight2 = new Weight(weightD2, d1.toString());
    }

    @After
    public void tearDown() {
        db.close();
        appContext.deleteDatabase("exercise.db"); // Gotta reset the db after each test
    }

    @Test
    public void addARun() {
        tearDown();
        db.addRun(r1);

        list1 = db.getAll();
        assertEquals("Test there is 1 run", 1, list1.size());
        assertEquals("Test the calories burned is correct", cals1, list1.get(0).getCaloriesBurned());
        assertEquals("Test the duration is correct", dur1, list1.get(0).getDuration());

    }

    @Test
    public void addManyRun() {
        db.addRun(r1);
        db.addRun(r2);
        db.addRun(r3);
        db.addRun(r4);


        list1 = db.getAll();
        assertEquals("Test there is the correct number of run", 4, list1.size());

        assertEquals("Test the calories burned is correct", cals1, list1.get(0).getCaloriesBurned());
        assertEquals("Test the duration is correct", dur1, list1.get(0).getDuration());
        assertEquals("Test the calories burned is correct", cals2, list1.get(1).getCaloriesBurned());
        assertEquals("Test the duration is correct", dur2, list1.get(1).getDuration());
        assertEquals("Test the calories burned is correct", cals3, list1.get(2).getCaloriesBurned());
        assertEquals("Test the duration is correct", dur3, list1.get(2).getDuration());
        assertEquals("Test the calories burned is correct", cals4, list1.get(3).getCaloriesBurned());
        assertEquals("Test the duration is correct", dur4, list1.get(3).getDuration());
    }


    @Test
    public void testGetRunByDate() {
        db.addRun(r1);
        db.addRun(r2);
        db.addRun(r3);
        db.addRun(r4);

        list1 = db.getExercisesByDate(d1.toString());
        assertEquals("Test there is the correct number of run", 1, list1.size());
        assertEquals("Test the calories burned is correct", cals1, list1.get(0).getCaloriesBurned());
        assertEquals("Test the duration is correct", dur1, list1.get(0).getDuration());

        list2 = db.getExercisesByDate(d2.toString());
        assertEquals("Test there is the correct number of run", 1, list2.size());
        assertEquals("Test the calories burned is correct", cals2, list2.get(0).getCaloriesBurned());
        assertEquals("Test the duration is correct", dur2, list2.get(0).getDuration());

        list3 = db.getExercisesByDate(d3.toString());
        assertEquals("Test there is the correct number of run", 1, list3.size());
        assertEquals("Test the calories burned is correct", cals3, list3.get(0).getCaloriesBurned());
        assertEquals("Test the duration is correct", dur3, list3.get(0).getDuration());

        list4 = db.getExercisesByDate(d4.toString());
        assertEquals("Test there is the correct number of run", 1, list4.size());
        assertEquals("Test the calories burned is correct", cals4, list4.get(0).getCaloriesBurned());
        assertEquals("Test the duration is correct", dur4, list4.get(0).getDuration());
    }

    // TODO FIX EDITRIDE
    @Test
    public void testEditRun() {
        db.addRun(r1);
        db.addRun(r2);

        list1 = db.getAll();
        assertEquals("Test there is the correct number of run", 2, list1.size());
        assertEquals("Test the calories burned is correct", cals1, list1.get(0).getCaloriesBurned());
        assertEquals("Test the duration is correct", dur1, list1.get(0).getDuration());
        assertEquals("Test the calories burned is correct", cals2, list1.get(1).getCaloriesBurned());
        assertEquals("Test the duration is correct", dur2, list1.get(1).getDuration());

        r2.setCaloriesBurned(-1);
        r2.setDuration(100);
        db.editRun(r2);

        list1 = db.getAll();
        assertEquals("Test there is the correct number of run", 2, list1.size());
        assertEquals("Test the calories burned is correct", cals1, list1.get(0).getCaloriesBurned());
        assertEquals("Test the duration is correct", dur1, list1.get(0).getDuration());
        assertEquals("Test the calories burned is correct", -1, list1.get(1).getCaloriesBurned());
        assertEquals("Test the duration is correct", 100, list1.get(1).getDuration());
    }


    @Test
    public void testRemoveRun() {
        db.addRun(r1);
        db.addRun(r2);

        list1 = db.getAll();
        assertEquals("Test there is the correct number of run", 2, list1.size());

        db.removeRun(r1);
        list1 = db.getAll();
        assertEquals("Test there is the correct number of run", 1, list1.size());
        assertEquals("Test the run is correct", dur2, list1.get(0).getDuration());

    }

    @Test
    public void addRide() {
        db.addRide(ri1);

        list1 = db.getAll();
        assertEquals("Test there is 1 ride", 1, list1.size());
        assertEquals("Test the calories burned is correct", cals1, list1.get(0).getCaloriesBurned());
        assertEquals("Test the duration is correct", dur1, list1.get(0).getDuration());

    }

    @Test
    public void addManyRide() {
        db.addRide(ri1);
        db.addRide(ri2);
        db.addRide(ri3);
        db.addRide(ri4);


        list1 = db.getAll();
        assertEquals("Test there is the correct number of run", 4, list1.size());

        assertEquals("Test the calories burned is correct", cals1, list1.get(0).getCaloriesBurned());
        assertEquals("Test the duration is correct", dur1, list1.get(0).getDuration());
        assertEquals("Test the calories burned is correct", cals2, list1.get(1).getCaloriesBurned());
        assertEquals("Test the duration is correct", dur2, list1.get(1).getDuration());
        assertEquals("Test the calories burned is correct", cals3, list1.get(2).getCaloriesBurned());
        assertEquals("Test the duration is correct", dur3, list1.get(2).getDuration());
        assertEquals("Test the calories burned is correct", cals4, list1.get(3).getCaloriesBurned());
        assertEquals("Test the duration is correct", dur4, list1.get(3).getDuration());
    }


    @Test
    public void testGetRideByDate() {
        db.addRide(ri1);
        db.addRide(ri2);
        db.addRide(ri3);
        db.addRide(ri4);

        list1 = db.getExercisesByDate(d1.toString());
        assertEquals("Test there is the correct number of run", 1, list1.size());
        assertEquals("Test the calories burned is correct", cals1, list1.get(0).getCaloriesBurned());
        assertEquals("Test the duration is correct", dur1, list1.get(0).getDuration());

        list2 = db.getExercisesByDate(d2.toString());
        assertEquals("Test there is the correct number of run", 1, list2.size());
        assertEquals("Test the calories burned is correct", cals2, list2.get(0).getCaloriesBurned());
        assertEquals("Test the duration is correct", dur2, list2.get(0).getDuration());

        list3 = db.getExercisesByDate(d3.toString());
        assertEquals("Test there is the correct number of run", 1, list3.size());
        assertEquals("Test the calories burned is correct", cals3, list3.get(0).getCaloriesBurned());
        assertEquals("Test the duration is correct", dur3, list3.get(0).getDuration());

        list4 = db.getExercisesByDate(d4.toString());
        assertEquals("Test there is the correct number of run", 1, list4.size());
        assertEquals("Test the calories burned is correct", cals4, list4.get(0).getCaloriesBurned());
        assertEquals("Test the duration is correct", dur4, list4.get(0).getDuration());
    }


    @Test
    public void testEditRide() {
        db.addRide(ri1);
        db.addRide(ri2);

        list1 = db.getAll();
        assertEquals("Test there is the correct number of run", 2, list1.size());
        assertEquals("Test the calories burned is correct", cals1, list1.get(0).getCaloriesBurned());
        assertEquals("Test the duration is correct", dur1, list1.get(0).getDuration());
        assertEquals("Test the calories burned is correct", cals2, list1.get(1).getCaloriesBurned());
        assertEquals("Test the duration is correct", dur2, list1.get(1).getDuration());

        ri2.setCaloriesBurned(-1);
        ri2.setDuration(100);
        db.editRide(ri2);

        list1 = db.getAll();
        assertEquals("Test there is the correct number of run", 2, list1.size());
        assertEquals("Test the calories burned is correct", cals1, list1.get(0).getCaloriesBurned());
        assertEquals("Test the duration is correct", dur1, list1.get(0).getDuration());
        assertEquals("Test the calories burned is correct", -1, list1.get(1).getCaloriesBurned());
        assertEquals("Test the duration is correct", 100, list1.get(1).getDuration());
    }


    @Test
    public void testRemoveRide() {
        db.addRide(ri1);
        db.addRide(ri2);

        list1 = db.getAll();
        assertEquals("Test there is the correct number of run", 2, list1.size());

        db.removeRide(ri1);
        list1 = db.getAll();
        assertEquals("Test there is the correct number of run", 1, list1.size());
        assertEquals("Test the run is correct", dur2, list1.get(0).getDuration());
    }


    @Test
    public void addAWeightlifting() {
        db.addWeightlifting(w1, w1.getKey());

        wList1 = db.getAllWeightlifting();
        assertEquals("check size", 1, wList1.size());
        assertEquals("check value", exerType1, wList1.get(0).getName());
    }

    @Test
    public void editLift() {
        db.addWeightlifting(w1, 0);
        wList1 = db.getAllWeightlifting();
        assertEquals("check size", 1, wList1.size());
        assertEquals("check value", exerType1, wList1.get(0).getName());

        w1.setName(woName4);
        db.editLift(w1);
        wList1 = db.getAllWeightlifting();
        assertEquals("check size", 1, wList1.size());
        assertEquals("check value", woName4, wList1.get(0).getName());

    }

    @Test
    public void testGetWorkout () {
        int workoutKey = 69;
        db.addWeightlifting(w1, workoutKey);
        db.addWeightlifting(w2, workoutKey);
        db.addWeightlifting(w3, workoutKey);
        db.addWeightlifting(w4, workoutKey);

        wList1 = db.getWorkout(workoutKey);
        assertEquals("check size", 4, wList1.size());

    }

    @Test
    public void testGetWorkoutByDate() {
        int workoutKey = 69;
        int workoutKey2 = 96;
        w1.setDate(d1);
        w2.setDate(d1);

        w3.setDate(d2);
        w4.setDate(d2);

        db.addWeightlifting(w1, workoutKey);
        db.addWeightlifting(w2, workoutKey);
        db.addWeightlifting(w3, workoutKey2);
        db.addWeightlifting(w4, workoutKey2);

        lw1 = db.getWorkoutsByDate(d1.toString());
        assertEquals("check size", 2, wList1.size());
        assertEquals("check value", w1.getName(), wList1.get(0).getName());
        assertEquals("check value", w2.getName(), wList1.get(1).getName());

    }

    @Test
    public void testRemoveWorkoutEmpty() {
        Workout work = new Workout(woName1, wList1, d1);
        assertFalse(db.removeWorkout(work));
    }

    @Test
    public void testRemoveWorkout() {
        int workoutKey = 69;
        int workoutKey2 = 96;
        w1.setDate(d1);
        w2.setDate(d1);

        w3.setDate(d2);
        w4.setDate(d2);

        db.addWeightlifting(w1, workoutKey);
        db.addWeightlifting(w2, workoutKey);
        db.addWeightlifting(w3, workoutKey2);
        db.addWeightlifting(w4, workoutKey2);

        lw1 = db.getWorkoutsByDate(d1.toString());

        assertEquals("check size", 1, lw1.size());
        assertEquals("check value", w1.getName(), lw1.get(0).getName());

        lw2 = db.getWorkoutsByDate(d2.toString());

        assertEquals("check size", 1, lw2.size());
        assertEquals("check value", w3.getName(), lw2.get(0).getName());

        db.removeWorkout(lw2.get(0));

        lw1 = db.getWorkoutsByDate(d1.toString());
        lw2 = db.getWorkoutsByDate(d2.toString());
        assertEquals("Check Size", 1, lw1.size());
        assertEquals("Check Size", 0, lw2.size());
    }



    @Test
    public void testAddManyWeight(){
        db.addWeight(weight1);
        db.addWeight(weight2);
        db.addWeight(weight1);
        fatty = db.getAllWeights();
        assertEquals("Verify Size", 3, fatty.size());
    }

    @Test
    public void testGetAllWeight() {
        db.addWeight(weight1);
        fatty = db.getAllWeights();
        assertEquals("Verify Size", 1, fatty.size());
        assertEquals("Verify Index", weightD1, fatty.get(0).getWeight(), 0.01);
    }

    @Test
    public void deleteNoneWeight() {
        assertFalse(db.removeWeight(weight1));
    }

    @Test
    public void deleteWeight() {
        db.addWeight(weight1);
        db.addWeight(weight2);
        db.removeWeight(weight1);
        fatty = db.getAllWeights();
        assertEquals("Verify Size", 1, fatty.size());
        assertEquals("Verify Index", weightD2, fatty.get(0).getWeight(), 0.01);
    }
}
