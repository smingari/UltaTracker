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

    private LocalDate date;

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

        date = LocalDate.now();

        FoodDatabaseHelper db = new FoodDatabaseHelper(AddFoodActivity.this);

        create_food_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                    String foodName = foodname_entry.getText().toString();

                    int calories = Integer.parseInt(calories_entry.getText().toString());
                    int protein = Integer.parseInt(protein_entry.getText().toString());
                    int carbs = Integer.parseInt(carbs_entry.getText().toString());
                    int fat = Integer.parseInt(fat_entry.getText().toString());
                    int fiber = Integer.parseInt(fiber_entry.getText().toString());

                    Food newFood = new Food(foodName, calories, protein, carbs, fat, fiber, date);
                    db.addFood(newFood);
                    toAddMealActivity(v);

                    Toast.makeText(AddFoodActivity.this, "Successfully created food.", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(AddFoodActivity.this, "Error creating food.", Toast.LENGTH_SHORT).show();
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


