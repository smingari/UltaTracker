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
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HDayActivity extends AppCompatActivity {
    Button addButton, editButton, deleteButton, viewButton;

    int totalCals = 0;
    int totalProtein = 0;
    int totalCarbs = 0;
    int totalFat = 0;
    int totalFiber = 0;

    TableLayout mealTable;
    TableRow selectedRow;
    Meal selectedMeal;
    FoodDatabaseHelper db;
    MealDatabaseHelper mdb;

    String curDate;

    boolean mealSelected;
    public static boolean inEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_day);

        addButton = findViewById(R.id.add_meal_button);
        editButton = findViewById(R.id.edit_meal_button);
        deleteButton = findViewById(R.id.delete_meal_button);
        viewButton = findViewById(R.id.hday_view_button2);

        hideButtons();
        inEdit = false;

        db = new FoodDatabaseHelper(this);
        mdb = new MealDatabaseHelper(this);

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

        List<Food> foodList = new ArrayList<>();
        MainActivity.newMeal = new Meal("newMeal", 0, 0, 0, 0, 0, LocalDate.parse(curDate), foodList);

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

        init_meal_table();

        TextView date = (TextView)findViewById(R.id.current_date_h);
        date.setText(MainActivity.selectedMonth + "/" + MainActivity.selectedDay + "/" + MainActivity.selectedYear);
        TextView cals = (TextView)findViewById(R.id.total_calories);
        cals.setText(String.valueOf(totalCals));
        TextView protein = (TextView)findViewById(R.id.total_protein);
        protein.setText(String.valueOf(totalProtein));
        TextView carbs = (TextView)findViewById(R.id.total_carbs);
        carbs.setText(String.valueOf(totalCarbs));
        TextView fat = (TextView)findViewById(R.id.total_fat);
        fat.setText(String.valueOf(totalFat));
        TextView fiber = (TextView)findViewById(R.id.total_fiber);
        fiber.setText(String.valueOf(totalFiber));
    }

    public void init_meal_table() {
        mealTable = findViewById(R.id.meal_table);

        List<Meal> foodList = mdb.getMealsByDate(curDate);
        int dbSize;
        if (foodList != null) { dbSize = foodList.size(); }
        else { dbSize = 0; }

        for (Meal meal: foodList) {
            totalCals += meal.getCals();
            totalProtein += meal.getProtein();
            totalCarbs += meal.getCarbs();
            totalFat += meal.getFat();
            totalFiber += meal.getFiber();
        }

        // Set up table header
        TableRow mealTableHeader = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        mealTableHeader.setLayoutParams(lp);

        // First column header
        TextView tv0 = new TextView(this);
        tv0.setPaintFlags(tv0.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv0.setText(" Meal ");
        tv0.setGravity(Gravity.CENTER_HORIZONTAL);
        mealTableHeader.addView(tv0);

        // Second column header
        TextView tv1 = new TextView(this);
        tv1.setPaintFlags(tv1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv1.setText(" Amount Eaten ");
        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
        mealTableHeader.addView(tv1);

        // Third column header
        TextView tv2 = new TextView(this);
        tv2.setPaintFlags(tv2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv2.setText(" Calories ");
        tv2.setGravity(Gravity.CENTER_HORIZONTAL);
        mealTableHeader.addView(tv2);

        // Add header row to table
        mealTable.addView(mealTableHeader);

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
                            row.setBackgroundColor(getResources().getColor(R.color.teal_200));
                        } else {
                            selectedRow.setBackgroundColor(getResources().getColor(R.color.white));
                            row.setBackgroundColor(getResources().getColor(R.color.teal_200));
                            selectedRow = row;
                        }
                        showButtons();
                        selectedMeal = foodList.get(row.getId());
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
                String mealName = foodList.get(i).getName();
                if (mealName.length() > 12) {
                    mealName = (mealName.substring(0, Math.min(mealName.length(), 12))) + "..";
                }
                t1v.setText(mealName);
                t1v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t1v);

                TextView t2v = new TextView(this);
                t2v.setText(String.valueOf(foodList.get(i).getFoodList().size()));
                t2v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t2v);

                TextView t3v = new TextView(this);
                t3v.setText(String.valueOf(foodList.get(i).getCals()));
                t3v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t3v);
                mealTable.addView(row);
            }
        }
    }

    public void deleteMeal(View view) {
        if (selectedMeal != null) {
            //Toast.makeText(HDayActivity.this, String.valueOf(selectedMeal.getKey()), Toast.LENGTH_SHORT).show();
            boolean success = mdb.deleteMeal(selectedMeal);
            mealTable.removeView(selectedRow);
        }
        if (mealSelected) { mealTable.removeView(selectedRow); }
        hideButtons();
    }

    public void toMainActivity(View view) {
        Intent intent = new Intent(HDayActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void editMeal(View view) {
        inEdit = true;
        MainActivity.newMeal = selectedMeal;
        Intent intent = new Intent(HDayActivity.this, AddMealActivity.class);
        startActivity(intent);
    }

    public void toAddMealActivity(View view) {
        Intent intent = new Intent(HDayActivity.this, AddMealActivity.class);
        startActivity(intent);
    }

    public void openViewDialog(View view) {
        ViewTaskDialog viewTaskDialog = new ViewTaskDialog(MainActivity.selectedTask);
        viewTaskDialog.show(getSupportFragmentManager(), "view task dialog");
    }

    public void hideButtons() {
        viewButton.setVisibility(View.INVISIBLE);
        editButton.setVisibility(View.INVISIBLE);
        deleteButton.setVisibility(View.INVISIBLE);
    }

    public void showButtons() {
        viewButton.setVisibility(View.VISIBLE);
        editButton.setVisibility(View.VISIBLE);
        deleteButton.setVisibility(View.VISIBLE);
    }
}