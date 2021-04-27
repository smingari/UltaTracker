package com.example.ultratracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    Button erase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        erase = findViewById(R.id.erase_button);

        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete all databases
                SettingsActivity.this.deleteDatabase("task.db");
                SettingsActivity.this.deleteDatabase("exercise.db");
                SettingsActivity.this.deleteDatabase("food.db");
                SettingsActivity.this.deleteDatabase("meal.db");
                SettingsActivity.this.deleteDatabase("notes.db");
            }
        });

    }


}
