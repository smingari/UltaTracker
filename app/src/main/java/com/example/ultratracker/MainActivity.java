package com.example.ultratracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    Boolean isFABOpen;
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
        isFABOpen = false;
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


}