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

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class EDayActivity extends AppCompatActivity {
    Button addRunButton, addRideButton, addWWButton;
    Button editButton;
    Button deleteButton, viewButton;

    TableLayout exerciseTable;
    TableRow selectedRow;
    Exercise selectedExercise;
    ExerciseDatabaseHelper e_db;

    boolean exerciseSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_day);

        addRunButton = findViewById(R.id.add_running_button);
        addRideButton = findViewById(R.id.add_cycling_button);
        addWWButton = findViewById(R.id.add_weightlifting_button);
        //editButton = findViewById(R.id.edit_meal_button);
        //deleteButton = findViewById(R.id.delete_meal_button);
        viewButton = findViewById(R.id.eday_view_button2);

        viewButton.setVisibility(View.INVISIBLE);

        e_db = new ExerciseDatabaseHelper(this);

        // Testing meal database retrieval
        /**addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sMonth;
                String sDay;
                if (MainActivity.selectedMonth < 10) {
                    sMonth = "0" + MainActivity.selectedMonth;
                } else { sMonth = String.valueOf(MainActivity.selectedMonth); }
                if (MainActivity.selectedDay < 10) {
                    sDay = "0" + MainActivity.selectedDay;
                } else { sDay = String.valueOf(MainActivity.selectedDay); }
                LocalDate thisDate = LocalDate.parse(MainActivity.selectedYear + "-" + sMonth + "-" + sDay);

                List<Food> firstList = new ArrayList<>();
                Meal meal1 = new Meal("test1", 0, 0, 0, 0, 0, thisDate, firstList);
                Food apple = new Food("apple", 0, 0, 0, 0, 0, thisDate.toString(), meal1.getName(), meal1.getKey());
                Food banana = new Food("banana", 0, 0, 0, 0, 0, thisDate.toString(), meal1.getName(), meal1.getKey());
                meal1.addToMeal(apple);
                meal1.addToMeal(banana);
                for (Food food: meal1.getFoodList()) {
                    Toast.makeText(HDayActivity.this, food.getName(), Toast.LENGTH_SHORT).show();
                    mdb.addMeal(food);
                }

                List<Food> secondList = new ArrayList<>();
                Meal meal2 = new Meal("test2", 0, 0, 0, 0, 0, thisDate, secondList);
                Food pear = new Food("pear", 0, 0, 0, 0, 0, thisDate.toString(), meal2.getName(), meal2.getKey());
                Food grape = new Food("grape", 0, 0, 0, 0, 0, thisDate.toString(), meal2.getName(), meal2.getKey());
                meal2.getFoodList().add(pear);
                meal2.getFoodList().add(grape);
                for (Food food: meal2.getFoodList()) { mdb.addMeal(food); }

                List<Food> secondList = new ArrayList<>();
                Meal meal2 = new Meal("test3", 0, 0, 0, 0, 0, thisDate, secondList);
                Food pear = new Food("chez", 300, 25, 15, 30, 3, thisDate.toString(), meal2.getName(), meal2.getKey());
                Food grape = new Food("pitsa", 700, 60, 72, 80, 12, thisDate.toString(), meal2.getName(), meal2.getKey());
                meal2.getFoodList().add(pear);
                meal2.getFoodList().add(grape);
                for (Food food: meal2.getFoodList()) { mdb.addMeal(food); }

                //Toast.makeText(HDayActivity.this, "about to query database", Toast.LENGTH_SHORT).show();
                List<Meal> meals = mdb.getMealsByDate(thisDate.toString());
            }
        });**/

        init_exercise_table();

        TextView date = (TextView)findViewById(R.id.current_date_e);
        date.setText(MainActivity.selectedMonth + "/" + MainActivity.selectedDay + "/" + MainActivity.selectedYear);
    }

    public void init_exercise_table() {

        exerciseTable = findViewById(R.id.exercise_table);

        // Format selected date for task query
        String sMonth;
        String sDay;
        if (MainActivity.selectedMonth < 10) {
            sMonth = "0" + MainActivity.selectedMonth;
        } else { sMonth = String.valueOf(MainActivity.selectedMonth); }
        if (MainActivity.selectedDay < 10) {
            sDay = "0" + MainActivity.selectedDay;
        } else { sDay = String.valueOf(MainActivity.selectedDay); }

        // TODO FIX THIS
        
        List<Exercise> exerciseList = e_db.getExercisesByDate(MainActivity.selectedYear + "-" + sMonth + "-" + sDay);
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
        tv1.setText(" Duration ");
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

                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isComplete;
                        if (selectedRow == null) {
                            selectedRow = row;
                            //completedTable.setBackgroundColor(getResources().getColor(R.color.white));
                            row.setBackgroundColor(getResources().getColor(R.color.teal_200));
                        } else {
                            selectedRow.setBackgroundColor(getResources().getColor(R.color.white));
                            row.setBackgroundColor(getResources().getColor(R.color.teal_200));
                            selectedRow = row;
                        }
                        viewButton.setVisibility(View.VISIBLE);
                        selectedExercise = exerciseList.get(row.getId());
                        //Toast.makeText(HDayActivity.this, selectedMeal.getName(), Toast.LENGTH_SHORT).show();
                        //isComplete = MainActivity.selectedTask.isComplete();
                        //foodSelected = !isComplete;
                        //completedTaskSelected = isComplete;
                        //showButtons();
                        //if(isComplete) {
                        //btn_moveToTasks.setVisibility(View.VISIBLE);
                        //} else {
                        //btn_moveToTasks.setVisibility(View.INVISIBLE);
                        //}
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
                //t2v.setText(String.valueOf(exerciseList.get(i).getFoodList().size()));
                t2v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t2v);

                TextView t3v = new TextView(this);
                //t3v.setText(String.valueOf(foodList.get(i).getCals()));
                t3v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t3v);
                exerciseTable.addView(row);
            }
        }

    }

    public void deleteMeal(View view) {
        /*
        if (selectedMeal != null) {
            //Toast.makeText(HDayActivity.this, String.valueOf(selectedMeal.getKey()), Toast.LENGTH_SHORT).show();
            boolean success = mdb.deleteMeal(selectedMeal);
            mealTable.removeView(selectedRow);
        }
        if (mealSelected) { mealTable.removeView(selectedRow); }
        viewButton.setVisibility(View.INVISIBLE);
         */
    }

    public void toMainActivity(View view) {
        Intent intent = new Intent(EDayActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void toAddRunActivity(View view) {
        Intent intent = new Intent(EDayActivity.this, AddRunActivity.class);
        startActivity(intent);
    }

    public void openViewDialog(View view) {
        ViewTaskDialog viewTaskDialog = new ViewTaskDialog(MainActivity.selectedTask);
        viewTaskDialog.show(getSupportFragmentManager(), "view task dialog");
    }
}