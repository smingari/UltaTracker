package com.example.ultratracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddMealActivity extends AppCompatActivity {
    Button addButton, editButton, deleteButton, viewButton, addToMealButton, createButton;
    TextView mealName;
    EditText mealEntry;

    TableLayout createTable;
    TableLayout listTable;
    TableRow selectedRow;
    public static Food selectedFood;
    List<Food> foodList;
    FoodDatabaseHelper db;
    MealDatabaseHelper mdb;

    String curDate;

    boolean mealSelected;
    boolean bankSelected;
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
        createButton = findViewById(R.id.create_meal_button);
        mealName = findViewById(R.id.meal_name_text);
        mealEntry = findViewById(R.id.meal_name_entry);

        hideButtons();
        addToMealButton.setVisibility(View.INVISIBLE);
        editButton.setVisibility(View.INVISIBLE);
        displayCreate();

        foodList = new ArrayList<>();

        db = new FoodDatabaseHelper(this);
        mdb = new MealDatabaseHelper(this);

        init_create_table();
        init_list_table();

    }

    public void init_create_table() {
        createTable = findViewById(R.id.create_table);

        Meal meal = MainActivity.newMeal;
        int dbSize;
        if (meal != null && meal.getFoodList().size() != 0) {
            dbSize = meal.getFoodList().size();
        } else { dbSize = 0; }
        //Toast.makeText(AddMealActivity.this, String.valueOf(dbSize), Toast.LENGTH_SHORT).show();

        // Set up table header
        TableRow createTableHeader = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        createTableHeader.setLayoutParams(lp);

        // First column header
        TextView tv0 = new TextView(this);
        tv0.setPaintFlags(tv0.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv0.setText(" Name ");
        tv0.setGravity(Gravity.CENTER_HORIZONTAL);
        createTableHeader.addView(tv0);

        // Second column header
        TextView tv1 = new TextView(this);
        tv1.setPaintFlags(tv1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv1.setText(" Calories (g) ");
        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
        createTableHeader.addView(tv1);

        // Third column header
        TextView tv2 = new TextView(this);
        tv2.setPaintFlags(tv2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv2.setText(" Fat (g) ");
        tv2.setGravity(Gravity.CENTER_HORIZONTAL);
        createTableHeader.addView(tv2);

        // Add header row to table
        createTable.addView(createTableHeader);

        // Add rows dynamically from database
        if (dbSize != 0) {
            for (int i = 0; i < dbSize; i++) {
                TableRow row = new TableRow(this);
                row.setId(i);

                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (selectedRow == null) {
                            selectedRow = row;
                            row.setBackgroundColor(getResources().getColor(R.color.teal_200));
                        } else {
                            selectedRow.setBackgroundColor(getResources().getColor(R.color.white));
                            row.setBackgroundColor(getResources().getColor(R.color.teal_200));
                            selectedRow = row;
                        }
                        bankSelected = false;
                        mealSelected = true;
                        showButtons();
                        addToMealButton.setVisibility(View.INVISIBLE);
                        editButton.setVisibility(View.INVISIBLE);
                        selectedFood = meal.getFoodList().get(row.getId());
                    }
                });

                TextView t1v = new TextView(this);
                String foodName = meal.getFoodList().get(i).getName();
                if (foodName.length() > 12) {
                    foodName = (foodName.substring(0, Math.min(foodName.length(), 12))) + "..";
                }
                t1v.setText(foodName);
                t1v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t1v);

                TextView t2v = new TextView(this);
                t2v.setText(String.valueOf(meal.getFoodList().get(i).getCals()));
                t2v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t2v);

                TextView t3v = new TextView(this);
                t3v.setText(String.valueOf(meal.getFoodList().get(i).getFat()));
                t3v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t3v);
                createTable.addView(row);
                //Toast.makeText(AddMealActivity.this, "Initialized create table", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void init_list_table() {
        listTable = findViewById(R.id.list_table);

        Food[] foods = db.getByDate(curDate);
        int dbSize;
        if (foods != null && foods.length != 0) { dbSize = foods.length; }
        else { dbSize = 0; }
        //Toast.makeText(AddMealActivity.this, String.valueOf(dbSize), Toast.LENGTH_SHORT).show();

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
                        if (selectedRow == null) {
                            selectedRow = row;
                            row.setBackgroundColor(getResources().getColor(R.color.teal_200));
                        } else {
                            selectedRow.setBackgroundColor(getResources().getColor(R.color.white));
                            row.setBackgroundColor(getResources().getColor(R.color.teal_200));
                            selectedRow = row;
                        }
                        hideButtons();
                        bankSelected = true;
                        mealSelected = false;
                        addToMealButton.setVisibility(View.VISIBLE);
                        editButton.setVisibility(View.VISIBLE);
                        selectedFood = foods[row.getId()];
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
                t2v.setText(String.valueOf(foods[i].getCals()));
                t2v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t2v);

                TextView t3v = new TextView(this);
                t3v.setText(String.valueOf(foods[i].getFat()));
                t3v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t3v);
                listTable.addView(row);
            }
        }
    }

    public void addToMeal(View view) {
        if (bankSelected) {
            TableRow newRow = new TableRow(this);
            newRow.setId(MainActivity.newMeal.getFoodList().size());
            MainActivity.newMeal.getFoodList().add(selectedFood);

            newRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedRow == null) {
                        selectedRow = newRow;
                        newRow.setBackgroundColor(getResources().getColor(R.color.teal_200));
                    } else {
                        selectedRow.setBackgroundColor(getResources().getColor(R.color.white));
                        newRow.setBackgroundColor(getResources().getColor(R.color.teal_200));
                        selectedRow = newRow;
                    }
                    bankSelected = false;
                    mealSelected = true;
                    showButtons();
                    addToMealButton.setVisibility(View.INVISIBLE);
                    editButton.setVisibility(View.INVISIBLE);
                    selectedFood = MainActivity.newMeal.getFoodList().get(newRow.getId());
                }
            });

            TextView t1v = new TextView(this);
            String foodName = selectedFood.getName();
            if (foodName.length() > 12) { foodName = (foodName.substring(0, Math.min(foodName.length(), 12))) + ".."; }
            t1v.setText(foodName);
            t1v.setGravity(Gravity.CENTER_HORIZONTAL);
            newRow.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText(String.valueOf(selectedFood.getCals()));
            t2v.setGravity(Gravity.CENTER_HORIZONTAL);
            newRow.addView(t2v);

            TextView t3v = new TextView(this);
            t3v.setText(String.valueOf(selectedFood.getFat()));
            t3v.setGravity(Gravity.CENTER_HORIZONTAL);
            newRow.addView(t3v);
            createTable.addView(newRow);

            selectedRow.setBackgroundColor(getResources().getColor(R.color.white));
            selectedRow = null;
            selectedFood = null;
            addToMealButton.setVisibility(View.INVISIBLE);
            editButton.setVisibility(View.INVISIBLE);
            displayCreate();
        }
    }

    public void deleteFood(View view) {
        if (selectedFood != null) {
            createTable.removeView(selectedRow);
            MainActivity.newMeal.getFoodList().remove(selectedFood);
            selectedRow = null;
            selectedFood = null;
            hideButtons();
            displayCreate();
        }
    }

    public void createNewMeal(View view) {
        // Check if meal name was entered
        String newMealName = mealEntry.getText().toString();
        if (newMealName.matches("")) {
            Toast.makeText(this, "You did not name your meal.", Toast.LENGTH_SHORT).show();
        } else {
            // Add food items to meal database
            for (Food food : MainActivity.newMeal.getFoodList()) {
                Food newFood = new Food(food.getName(), food.getCals(), food.getProtein(), food.getCarbs(), food.getFat(), food.getFiber(), curDate, mealEntry.getText().toString(), MainActivity.newMeal.getKey());
                mdb.addMeal(newFood);
            }
            Intent intent = new Intent(AddMealActivity.this, HDayActivity.class);
            startActivity(intent);
        }
    }

    public void toHDayActivity(View view) {
        Intent intent = new Intent(AddMealActivity.this, HDayActivity.class);
        startActivity(intent);
    }

    public void toAddFoodActivity(View view) {
        Intent intent = new Intent(AddMealActivity.this, AddFoodActivity.class);
        startActivity(intent);
    }


    public void toEditFoodActivity(View view){
        Intent intent = new Intent(AddMealActivity.this, EditFoodActivity.class);
        startActivity(intent);
    }

    public void openViewDialog(View view) {
        ViewTaskDialog viewTaskDialog = new ViewTaskDialog(MainActivity.selectedTask);
        viewTaskDialog.show(getSupportFragmentManager(), "view task dialog");
    }

    public void displayCreate() {
        if (MainActivity.newMeal.getFoodList().isEmpty()) {
            createButton.setVisibility(View.INVISIBLE);
            mealName.setVisibility(View.INVISIBLE);
            mealEntry.setVisibility(View.INVISIBLE);
        } else {
            createButton.setVisibility(View.VISIBLE);
            mealName.setVisibility(View.VISIBLE);
            mealEntry.setVisibility(View.VISIBLE);
        }
    }

    public void hideButtons() {
        viewButton.setVisibility(View.INVISIBLE);
        deleteButton.setVisibility(View.INVISIBLE);
    }

    public void showButtons() {
        viewButton.setVisibility(View.VISIBLE);
        deleteButton.setVisibility(View.VISIBLE);
    }
}