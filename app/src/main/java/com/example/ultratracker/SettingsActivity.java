package com.example.ultratracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {

    Button erase;
    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        erase = findViewById(R.id.erase_button);
        home = findViewById(R.id.exit_button);
        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete all databases
                SettingsActivity.this.deleteDatabase("task.db");
                SettingsActivity.this.deleteDatabase("exercise.db");
                SettingsActivity.this.deleteDatabase("food.db");
                SettingsActivity.this.deleteDatabase("meal.db");
                SettingsActivity.this.deleteDatabase("notes.db");

                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        Switch toggle = (Switch) findViewById(R.id.dark_mode_settings);
        toggle.setChecked(sp.getBoolean("DarkMode", false));

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    // The toggle is enabled
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("DarkMode", true);
                    editor.commit();
                    Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                    startActivity(intent);

                } else {
                    // The toggle is disabled
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("DarkMode", false);
                    editor.commit();
                    Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                    startActivity(intent);

                }
                finish();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
