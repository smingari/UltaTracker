package com.example.ultratracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    Boolean isFABOpen;
    Boolean plannerMode;
    Boolean healthMode;
    Boolean exerciseMode;
    FloatingActionButton addEntryButton;
    FloatingActionButton addPlanner;
    FloatingActionButton addHealth;
    FloatingActionButton addExercise;
    LinearLayout addPlannerContainer;
    LinearLayout addHealthContainer;
    LinearLayout addExerciseContainer;
    TextView addPlannerText;
    TextView addHealthText;
    TextView addExerciseText;
    CalendarView cal;
    public static String selectedDate; // syntax - month/day/year

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addEntryButton = (FloatingActionButton) findViewById(R.id.addEntryButton);
        addPlanner = (FloatingActionButton) findViewById(R.id.addPlanner);
        addHealth = (FloatingActionButton) findViewById(R.id.addHealth);
        addExercise = (FloatingActionButton) findViewById(R.id.addExercise);
        addPlannerContainer = findViewById(R.id.addPlannerContainer);
        addHealthContainer = findViewById(R.id.addHealthContainer);
        addExerciseContainer = findViewById(R.id.addExerciseContainer);
        addPlannerText = findViewById(R.id.addPlannerText);
        addHealthText = findViewById(R.id.addHealthText);
        addExerciseText = findViewById(R.id.addExerciseText);
        cal = (CalendarView) findViewById(R.id.calendarView4);
        isFABOpen = false;
        plannerMode = true; // Main screen defaults to planner mode upon startup
        healthMode = false;
        exerciseMode = false;

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = (month+1 + "/" + dayOfMonth + "/" + year);
                if (plannerMode) {
                    closeFABMenu();
                    toPDay(view);
                }
                else if (healthMode) {
                    closeFABMenu();
                }
                else if (exerciseMode) {
                    closeFABMenu();
                }
            }
        });

        addEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });
        addPlanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plannerMode = true; healthMode = false; exerciseMode = false;
                addPlanner.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                addHealth.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal_700)));
                addExercise.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal_700)));
                Toast.makeText(MainActivity.this, "Select A Date", Toast.LENGTH_SHORT).show();
            }
        });
        addHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plannerMode = false; healthMode = true; exerciseMode = false;
                addPlanner.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal_700)));
                addHealth.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                addExercise.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal_700)));
                Toast.makeText(MainActivity.this, "Select A Date", Toast.LENGTH_SHORT).show();
            }
        });
        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plannerMode = false; healthMode = false; exerciseMode = true;
                addPlanner.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal_700)));
                addHealth.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal_700)));
                addExercise.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                Toast.makeText(MainActivity.this, "Select A Date", Toast.LENGTH_SHORT).show();
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

    private void showFABMenu(){
        isFABOpen=true;
        addEntryButton.animate().rotation(45.0F).withLayer().setDuration(300).start();
        addPlanner.setClickable(true);
        addHealth.setClickable(true);
        addExercise.setClickable(true);
        addPlannerText.setVisibility(View.VISIBLE);
        addHealthText.setVisibility(View.VISIBLE);
        addExerciseText.setVisibility(View.VISIBLE);
        addPlannerContainer.animate().translationY(-440);
        addHealthContainer.animate().translationY(-300);
        addExerciseContainer.animate().translationY(-160);
    }

    private void closeFABMenu(){
        plannerMode = false; healthMode = false; exerciseMode = false;
        addPlanner.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal_700)));
        addHealth.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal_700)));
        addExercise.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal_700)));
        isFABOpen=false;
        addEntryButton.animate().rotation(0.0F).withLayer().setDuration(300).start();
        addPlanner.setClickable(false);
        addHealth.setClickable(false);
        addExercise.setClickable(false);
        addPlannerText.setVisibility(View.INVISIBLE);
        addHealthText.setVisibility(View.INVISIBLE);
        addExerciseText.setVisibility(View.INVISIBLE);
        addPlannerContainer.animate().translationY(0);
        addHealthContainer.animate().translationY(0);
        addExerciseContainer.animate().translationY(0);
    }

    public void toPDay(View view) {
        Intent intent = new Intent(MainActivity.this, PDayActivity.class);
        startActivity(intent);
    }
}