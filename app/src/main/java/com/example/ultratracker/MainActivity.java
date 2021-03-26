package com.example.ultratracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    Boolean plannerMode;
    Boolean healthMode;
    Boolean exerciseMode;
    Button addPlanner;
    Button addHealth;
    Button addExercise;
    CalendarView cal;
    public static int selectedYear;
    public static int selectedMonth;
    public static int selectedDay;

    public static Task selectedTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addPlanner = findViewById(R.id.addPlanner);
        addHealth = findViewById(R.id.addHealth);
        addExercise = findViewById(R.id.addExercise);

        cal = (CalendarView) findViewById(R.id.calendarView4);

        plannerMode = true; // Main screen defaults to planner mode upon startup
        healthMode = false;
        exerciseMode = false;
        addPlanner.setBackgroundColor(getResources().getColor(R.color.purple_500));
        addHealth.setBackgroundColor(getResources().getColor(R.color.teal_200));
        addExercise.setBackgroundColor(getResources().getColor(R.color.teal_200));

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedYear = year;
                selectedMonth = month+1;
                selectedDay = dayOfMonth;
                if (plannerMode) {
                    toPDay(view);
                }
                else if (healthMode) {
                }
                else if (exerciseMode) {
                }
            }
        });

        addPlanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plannerMode = true; healthMode = false; exerciseMode = false;
                addPlanner.setBackgroundColor(getResources().getColor(R.color.purple_500));
                addHealth.setBackgroundColor(getResources().getColor(R.color.teal_200));
                addExercise.setBackgroundColor(getResources().getColor(R.color.teal_200));
                //Toast.makeText(MainActivity.this, "Select A Date", Toast.LENGTH_SHORT).show();
            }
        });
        addHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plannerMode = false; healthMode = true; exerciseMode = false;
                addPlanner.setBackgroundColor(getResources().getColor(R.color.teal_200));
                addHealth.setBackgroundColor(getResources().getColor(R.color.purple_500));
                addExercise.setBackgroundColor(getResources().getColor(R.color.teal_200));
                //Toast.makeText(MainActivity.this, "Select A Date", Toast.LENGTH_SHORT).show();
            }
        });
        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plannerMode = false; healthMode = false; exerciseMode = true;
                addPlanner.setBackgroundColor(getResources().getColor(R.color.teal_200));
                addHealth.setBackgroundColor(getResources().getColor(R.color.teal_200));
                addExercise.setBackgroundColor(getResources().getColor(R.color.purple_500));
                //Toast.makeText(MainActivity.this, "Select A Date", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void toPDay(View view) {
        Intent intent = new Intent(MainActivity.this, PDayActivity.class);
        startActivity(intent);
    }

    public void toTodo(View view) {
        Intent intent = new Intent(MainActivity.this, TodoActivity.class);
        startActivity(intent);
    }
}