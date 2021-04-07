package com.example.ultratracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddMealActivity extends AppCompatActivity {
    Button addButton, editButton, deleteButton, viewButton, addToMealButton;

    TableLayout createTable;
    TableLayout listTable;
    TableRow selectedRow;
    Food selectedFood;
    List<Food> foodList;
    FoodDatabaseHelper db;
    MealDatabaseHelper mdb;

    String curDate;

    boolean mealSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        // Format selected date
        String sMonth;
        String sDay;
        if (MainActivity.selectedMonth < 10) {
            sMonth = "0" + MainActivity.selectedMonth;
        } else { sMonth = String.valueOf(MainActivity.selectedMonth); }
        if (MainActivity.selectedDay < 10) {
            sDay = "0" + MainActivity.selectedDay;
        } else { sDay = String.valueOf(MainActivity.selectedDay); }
        curDate = MainActivity.selectedYear + "-" + sMonth + "-" + sDay;

        addButton = findViewById(R.id.add_food_button);
        editButton = findViewById(R.id.edit_food_button);
        deleteButton = findViewById(R.id.delete_food_button);
        viewButton = findViewById(R.id.add_meal_view_button);
        addToMealButton = findViewById(R.id.add_to_meal_button);

        viewButton.setVisibility(View.INVISIBLE);
        addToMealButton.setVisibility(View.INVISIBLE);

        foodList = new ArrayList<>();

        db = new FoodDatabaseHelper(this);
        mdb = new MealDatabaseHelper(this);

        init_list_table();
    }

    public void init_list_table() {
        listTable = findViewById(R.id.list_table);

        Food[] foods = db.getAllSorted();
        int dbSize;
        if (foods != null && foods.length != 0) { dbSize = foods.length; }
        else { dbSize = 0; }

        // Set up table header
        TableRow listTableHeader = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        listTableHeader.setLayoutParams(lp);

        // First column header
        TextView tv0 = new TextView(this);
        tv0.setPaintFlags(tv0.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv0.setText(" Name ");
        tv0.setGravity(Gravity.CENTER_HORIZONTAL);
        listTableHeader.addView(tv0);

        // Second column header
        TextView tv1 = new TextView(this);
        tv1.setPaintFlags(tv1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv1.setText(" Calories (g) ");
        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
        listTableHeader.addView(tv1);

        // Third column header
        TextView tv2 = new TextView(this);
        tv2.setPaintFlags(tv2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv2.setText(" Fat (g) ");
        tv2.setGravity(Gravity.CENTER_HORIZONTAL);
        listTableHeader.addView(tv2);

        // Add header row to table
        listTable.addView(listTableHeader);

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
                        selectedFood = foods[row.getId()];
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
                String foodName = foods[i].getName();
                if (foodName.length() > 12) {
                    foodName = (foodName.substring(0, Math.min(foodName.length(), 12))) + "..";
                }
                t1v.setText(foodName);
                t1v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t1v);

                TextView t2v = new TextView(this);
                t2v.setText(foods[i].getCals());
                t2v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t2v);

                TextView t3v = new TextView(this);
                t3v.setText(foods[i].getFat());
                t3v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t3v);
                listTable.addView(row);
            }
        }
    }

    public void deleteFood(View view) {
        /**if (selectedMeal != null) {
            //Toast.makeText(HDayActivity.this, String.valueOf(selectedMeal.getKey()), Toast.LENGTH_SHORT).show();
            boolean success = mdb.deleteMeal(selectedMeal);
            mealTable.removeView(selectedRow);
        }
        if (mealSelected) { mealTable.removeView(selectedRow); }
        viewButton.setVisibility(View.INVISIBLE);;**/
    }

    public void toHDayActivity(View view) {
        Intent intent = new Intent(AddMealActivity.this, HDayActivity.class);
        startActivity(intent);
    }

    public void toAddFoodActivity(View view) {
        Intent intent = new Intent(AddMealActivity.this, AddFoodActivity.class);
        startActivity(intent);
    }

    public void openViewDialog(View view) {
        ViewTaskDialog viewTaskDialog = new ViewTaskDialog(MainActivity.selectedTask);
        viewTaskDialog.show(getSupportFragmentManager(), "view task dialog");
    }
}