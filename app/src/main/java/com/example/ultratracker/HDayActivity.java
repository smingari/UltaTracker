package com.example.ultratracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HDayActivity extends AppCompatActivity {
    Button testButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_day);

        testButton = findViewById(R.id.test_merge);
        FoodDatabaseHelper db = new FoodDatabaseHelper(HDayActivity.this);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Food> test = db.getAll();
                //Toast.makeText(HDayActivity.this, "Created Successfully", Toast.LENGTH_SHORT).show();
                if (test.isEmpty() == false) {
                    for (Food food : test) {
                        db.deleteFood(food);
                    }
                }
                //Toast.makeText(HDayActivity.this, "Made it through", Toast.LENGTH_SHORT).show();
                Food food = new Food("Banana", 0, 0, 0, 0, 0);
                db.addFood(food);
                food = new Food("Abbbbb", 0, 0, 0, 0, 0);
                db.addFood(food);
                food = new Food("Apple", 0, 0, 0, 0, 0);
                db.addFood(food);
                food = new Food("Crab", 0, 0, 0, 0, 0);
                db.addFood(food);
                food = new Food("Aaaaaaa", 0, 0, 0, 0, 0);
                db.addFood(food);

                Food[] sorted = db.getAllSorted();
                for (Food i : sorted) {
                    Toast.makeText(HDayActivity.this, i.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}