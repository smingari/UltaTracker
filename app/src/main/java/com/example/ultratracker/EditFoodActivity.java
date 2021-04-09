package com.example.ultratracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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

public class EditFoodActivity extends AppCompatActivity {
    Button cancel_button, edit_food_button;

    EditText foodname_entry, calories_entry, protein_entry, fat_entry, carbs_entry, fiber_entry;

    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);

        FoodDatabaseHelper db = new FoodDatabaseHelper(EditFoodActivity.this);

        foodname_entry = findViewById(R.id.foodname_entry);
        calories_entry = findViewById(R.id.calories_entry);
        protein_entry = findViewById(R.id.protein_entry);
        carbs_entry = findViewById(R.id.carbs_entry);
        fiber_entry = findViewById(R.id.fiber_entry);
        fat_entry = findViewById(R.id.fat_entry);

        cancel_button = findViewById(R.id.cancel_button);
        edit_food_button = findViewById(R.id.edit_food_button);

        foodname_entry.setText(AddMealActivity.selectedFood.getName());
        calories_entry.setText(String.valueOf(AddMealActivity.selectedFood.getCals()));
        protein_entry.setText(String.valueOf(AddMealActivity.selectedFood.getProtein()));
        carbs_entry.setText(String.valueOf(AddMealActivity.selectedFood.getCarbs()));
        fat_entry.setText(String.valueOf(AddMealActivity.selectedFood.getFat()));
        fiber_entry.setText(String.valueOf(AddMealActivity.selectedFood.getFiber()));

        edit_food_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try{
                    String foodName = foodname_entry.getText().toString();

                    int calories = Integer.parseInt(calories_entry.getText().toString());
                    int protein = Integer.parseInt(protein_entry.getText().toString());
                    int carbs = Integer.parseInt(carbs_entry.getText().toString());
                    int fat = Integer.parseInt(fat_entry.getText().toString());
                    int fiber = Integer.parseInt(fiber_entry.getText().toString());

                    AddMealActivity.selectedFood.setName(foodName);
                    AddMealActivity.selectedFood.setCals(calories);
                    AddMealActivity.selectedFood.setProtein(protein);
                    AddMealActivity.selectedFood.setCarbs(carbs);
                    AddMealActivity.selectedFood.setFat(fat);
                    AddMealActivity.selectedFood.setFiber(fiber);

                    if(db.editFood(AddMealActivity.selectedFood)){
                        toAddMealActivity(v);
                        Toast.makeText(EditFoodActivity.this, "Successfully edited food.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(EditFoodActivity.this, "Error editing food.", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(EditFoodActivity.this, "Error editing food.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void toAddMealActivity(View view){
        Intent intent = new Intent(EditFoodActivity.this, AddMealActivity.class);
        startActivity(intent);
    }
}
