package com.example.ultratracker;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EDayActivity extends AppCompatActivity {
    Button addRunButton, addRideButton, addWWButton;
    Button editButton, editWWButton;
    Button deleteButton, deleteWWButton, viewButton;

    TableLayout exerciseTable;
    TableLayout strengthTable;
    TableRow selectedRow;
    public static Exercise selectedExercise;
    public static Workout selectedWorkout;
    ExerciseDatabaseHelper e_db;

    String currentDate;

    boolean exerciseSelected = false;
    boolean workoutSelected = false;
    public static boolean inEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_day);

        addRunButton = findViewById(R.id.add_running_button);
        addRideButton = findViewById(R.id.add_cycling_button);
        addWWButton = findViewById(R.id.add_weightlifting_button);
        editButton = findViewById(R.id.edit_exercise_button);
        editWWButton = findViewById(R.id.edit_lift_button);
        deleteButton = findViewById(R.id.delete_exercise_button);
        deleteWWButton = findViewById(R.id.delete_lift_button);
        viewButton = findViewById(R.id.eday_view_button2);

        //hideButtons();
        HDayActivity.inEdit = false;
        EDayActivity.inEdit = false;

        hideButtons();
        hideWWButtons();
        viewButton.setVisibility(View.INVISIBLE);

        e_db = new ExerciseDatabaseHelper(this);

        // Format selected date for task query
        String sMonth;
        String sDay;
        if (MainActivity.selectedMonth < 10) {
            sMonth = "0" + MainActivity.selectedMonth;
        } else { sMonth = String.valueOf(MainActivity.selectedMonth); }
        if (MainActivity.selectedDay < 10) {
            sDay = "0" + MainActivity.selectedDay;
        } else { sDay = String.valueOf(MainActivity.selectedDay); }
        currentDate = MainActivity.selectedYear + "-" + sMonth + "-" + sDay;

        List<Weightlifting> list = new ArrayList<>();
        MainActivity.newWo = new Workout("newWo", list, LocalDate.parse(currentDate));

        init_exercise_table();
        init_strength_table();

        TextView date = (TextView)findViewById(R.id.current_date_e);
        date.setText(MainActivity.selectedMonth + "/" + MainActivity.selectedDay + "/" + MainActivity.selectedYear);
    }

    public void init_exercise_table() {
        exerciseTable = findViewById(R.id.exercise_table);

        List<Exercise> exerciseList = e_db.getExercisesByDate(currentDate);
        int dbSize;
        if (exerciseList != null) { dbSize = exerciseList.size(); }
        else { dbSize = 0; }


        // Set up table header
        TableRow exerciseTableHeader = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        exerciseTableHeader.setLayoutParams(lp);

        // First column header
        TextView tv0 = new TextView(this);
        tv0.setPaintFlags(tv0.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv0.setText(" Exercise ");
        tv0.setGravity(Gravity.CENTER_HORIZONTAL);
        exerciseTableHeader.addView(tv0);

        // Second column header
        TextView tv1 = new TextView(this);
        tv1.setPaintFlags(tv1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv1.setText(" Time ");
        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
        exerciseTableHeader.addView(tv1);

        // Third column header
        TextView tv2 = new TextView(this);
        tv2.setPaintFlags(tv2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv2.setText(" Calories Burned ");
        tv2.setGravity(Gravity.CENTER_HORIZONTAL);
        exerciseTableHeader.addView(tv2);

        // Add header row to table
        exerciseTable.addView(exerciseTableHeader);

        // Add rows dynamically from database
        if (dbSize != 0) {
            for (int i = 0; i < dbSize; i++) {
                TableRow row = new TableRow(this);
                row.setId(i);
                row.setBackgroundResource(R.drawable.list_selector_background);
                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isComplete;
                        if (selectedRow != null) {
                            selectedRow.setSelected(false);
                        }
                        selectedRow = row;
                        row.setSelected(true);
                        exerciseSelected = true;
                        workoutSelected = false;
                        viewButton.setVisibility(View.VISIBLE);
                        showButtons();
                        hideWWButtons();
                        selectedExercise = exerciseList.get(row.getId());
                    }
                });

                TextView t1v = new TextView(this);
                String exerciseType = exerciseList.get(i).getExerciseType();
                if (exerciseType.length() > 12) {
                    exerciseType = (exerciseType.substring(0, Math.min(exerciseType.length(), 12))) + "..";
                }
                t1v.setText(exerciseType);
                t1v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t1v);

                TextView t2v = new TextView(this);
                t2v.setText(exerciseList.get(i).getCompletedTime());
                t2v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t2v);

                TextView t3v = new TextView(this);
                t3v.setText(String.valueOf(exerciseList.get(i).getCaloriesBurned()));
                t3v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t3v);
                exerciseTable.addView(row);
            }
        }
    }

    public void init_strength_table() {
        strengthTable = findViewById(R.id.strength_table);

        List<Workout> woList = e_db.getWorkoutsByDate(currentDate);
        int dbSize;
        if (woList != null) { dbSize = woList.size(); }
        else { dbSize = 0; }

        // Set up table header
        TableRow strengthTableHeader = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        strengthTableHeader.setLayoutParams(lp);

        // First column header
        TextView tv0 = new TextView(this);
        tv0.setPaintFlags(tv0.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv0.setText(" Name ");
        tv0.setGravity(Gravity.CENTER_HORIZONTAL);
        strengthTableHeader.addView(tv0);

        // Second column header
        TextView tv1 = new TextView(this);
        tv1.setPaintFlags(tv1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv1.setText(" # Sets ");
        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
        strengthTableHeader.addView(tv1);

        // Third column header
        TextView tv2 = new TextView(this);
        tv2.setPaintFlags(tv2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv2.setText(" # Reps ");
        tv2.setGravity(Gravity.CENTER_HORIZONTAL);
        strengthTableHeader.addView(tv2);

        // Add header row to table
        strengthTable.addView(strengthTableHeader);

        // Add rows dynamically from database
        if (dbSize != 0) {
            for (int i = 0; i < dbSize; i++) {
                TableRow row = new TableRow(this);
                row.setId(i);
                row.setBackgroundResource(R.drawable.list_selector_background);
                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isComplete;
                        if (selectedRow != null) {
                            selectedRow.setSelected(false);
                        }
                        selectedRow = row;
                        row.setSelected(true);
                        exerciseSelected = false;
                        workoutSelected = true;
                        viewButton.setVisibility(View.VISIBLE);
                        showWWButtons();
                        hideButtons();
                        selectedWorkout = woList.get(row.getId());
                    }
                });

                TextView t1v = new TextView(this);
                String name = woList.get(i).getName();
                if (name.length() > 12) {
                    name = (name.substring(0, Math.min(name.length(), 12))) + "..";
                }
                t1v.setText(name);
                t1v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t1v);

                TextView t2v = new TextView(this);
                t2v.setText(String.valueOf(woList.get(i).getSets()));
                t2v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t2v);

                TextView t3v = new TextView(this);
                t3v.setText(String.valueOf(woList.get(i).getReps()));
                t3v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t3v);
                strengthTable.addView(row);
            }
        }
    }

    public void deleteExercise (View view) {
        if (selectedExercise != null) {
            boolean success;
            //Toast.makeText(HDayActivity.this, String.valueOf(selectedMeal.getKey()), Toast.LENGTH_SHORT).show();
            if (selectedExercise.getExerciseType().equals("Run")) { success = e_db.removeRun((Run)selectedExercise); }
            if (selectedExercise.getExerciseType().equals("Ride")) { success = e_db.removeRide((Ride)selectedExercise); }
            exerciseTable.removeView(selectedRow);
        }
        if (exerciseSelected) { exerciseTable.removeView(selectedRow); }
        hideButtons();
        viewButton.setVisibility(View.INVISIBLE);
    }

    public void deleteWorkout (View view) {
        if (selectedWorkout != null) {
            //Toast.makeText(HDayActivity.this, String.valueOf(selectedMeal.getKey()), Toast.LENGTH_SHORT).show();
            boolean success = e_db.removeWorkout(selectedWorkout);
            strengthTable.removeView(selectedRow);
        }
        hideWWButtons();
        viewButton.setVisibility(View.INVISIBLE);
    }

    public void editExercise(View view) {
        Intent intent;
        if (selectedExercise.getExerciseType().equals("Run")) {
            intent = new Intent(EDayActivity.this, EditRunActivity.class);
            startActivity(intent);
        }

        if (selectedExercise.getExerciseType().equals("Ride")) {
            intent = new Intent(EDayActivity.this, EditRideActivity.class);
            startActivity(intent);
        }

        if (selectedExercise.getExerciseType().equals("Weightlifting")) {
            EDayActivity.inEdit = true;
            //MainActivity.newWo = selectedExercise;
            intent = new Intent(EDayActivity.this, AddWeightliftingActivity.class);
            startActivity(intent);
        }
    }

    public void editWorkout(View view) {
        EDayActivity.inEdit = true;
        MainActivity.newWo = selectedWorkout;
        Intent intent = new Intent(EDayActivity.this, AddWeightliftingActivity.class);
        startActivity(intent);
    }

    public void toMainActivity(View view) {
        Intent intent = new Intent(EDayActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void toAddRunActivity(View view) {
        Intent intent = new Intent(EDayActivity.this, AddRunActivity.class);
        startActivity(intent);
    }

    public void toAddRideActivity(View view){
        Intent intent = new Intent(EDayActivity.this, AddRideActivity.class);
        startActivity(intent);
    }

    public void toAddWeightliftingActivity(View view){
        Intent intent = new Intent(EDayActivity.this, AddWeightliftingActivity.class);
        startActivity(intent);
    }

    public void toTrackWeightActivity(View view){
        Intent intent = new Intent(EDayActivity.this, TrackWeightActivity.class);
        startActivity(intent);
    }


    public void openViewDialog(View view) {
        if (exerciseSelected && !workoutSelected) {
            if (selectedExercise.getExerciseType().equals("Run")) {
                ViewRunDialog viewRunDialog = new ViewRunDialog((Run) selectedExercise);
                viewRunDialog.show(getSupportFragmentManager(), "view run dialog");
            } else if (selectedExercise.getExerciseType().equals("Ride")) {
                ViewRideDialog viewRideDialog = new ViewRideDialog((Ride) selectedExercise);
                viewRideDialog.show(getSupportFragmentManager(), "view ride dialog");
            }
        } else if (!exerciseSelected && workoutSelected) {
            EDayActivity.inEdit = true;
            MainActivity.newWo = selectedWorkout;
            Intent intent = new Intent(EDayActivity.this, AddWeightliftingActivity.class);
            startActivity(intent);
        }
    }

    public void hideButtons() {
        editButton.setVisibility(View.INVISIBLE);
        deleteButton.setVisibility(View.INVISIBLE);
    }

    public void hideWWButtons() {
        editWWButton.setVisibility(View.INVISIBLE);
        deleteWWButton.setVisibility(View.INVISIBLE);
    }

    public void showButtons() {
        viewButton.setVisibility(View.VISIBLE);
        editButton.setVisibility(View.VISIBLE);
        deleteButton.setVisibility(View.VISIBLE);
    }

    public void showWWButtons() {
        viewButton.setVisibility(View.VISIBLE);
        editWWButton.setVisibility(View.VISIBLE);
        deleteWWButton.setVisibility(View.VISIBLE);
    }
}