package com.example.ultratracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalDate;

public class AddFoodActivity extends AppCompatActivity {
    Button cancel_button, create_food_button;

    EditText foodname_entry, calories_entry, protein_entry, fat_entry, carbs_entry, fiber_entry;

    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        foodname_entry = findViewById(R.id.foodname_entry);
        calories_entry = findViewById(R.id.calories_entry);
        protein_entry = findViewById(R.id.protein_entry);
        carbs_entry = findViewById(R.id.carbs_entry);
        fiber_entry = findViewById(R.id.fiber_entry);
        fat_entry = findViewById(R.id.fat_entry);

        cancel_button = findViewById(R.id.cancel_button);
        create_food_button = findViewById(R.id.create_food_button);

        String sMonth;
        String sDay;
        if (MainActivity.selectedMonth < 10) {
            sMonth = "0" + MainActivity.selectedMonth;
        } else { sMonth = String.valueOf(MainActivity.selectedMonth); }
        if (MainActivity.selectedDay < 10) {
            sDay = "0" + MainActivity.selectedDay;
        } else { sDay = String.valueOf(MainActivity.selectedDay); }
        date = (MainActivity.selectedYear + "-" + sMonth + "-" + sDay);

        FoodDatabaseHelper db = new FoodDatabaseHelper(AddFoodActivity.this);

        create_food_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                    String foodName = foodname_entry.getText().toString();
                    if (foodName.equals("")) {
                        Toast.makeText(AddFoodActivity.this, "Please provide a name.", Toast.LENGTH_SHORT).show();
                    } else {
                        int calories = Integer.parseInt(calories_entry.getText().toString());
                        int protein = Integer.parseInt(protein_entry.getText().toString());
                        int carbs = Integer.parseInt(carbs_entry.getText().toString());
                        int fat = Integer.parseInt(fat_entry.getText().toString());
                        int fiber = Integer.parseInt(fiber_entry.getText().toString());

                        Food newFood = new Food(foodName, calories, protein, carbs, fat, fiber, LocalDate.parse(date));
                        MainActivity.newMeal.getFoodList().add(newFood);
                        db.addFood(newFood);
                        toAddMealActivity(v);

                        Toast.makeText(AddFoodActivity.this, "Successfully created food.", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(AddFoodActivity.this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { toAddMealActivity(v);}
        });

    }

    public void toAddMealActivity(View view){
        Intent intent = new Intent(AddFoodActivity.this, AddMealActivity.class);
        startActivity(intent);
    }

}


