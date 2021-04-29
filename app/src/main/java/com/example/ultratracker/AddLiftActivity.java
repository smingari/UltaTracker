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

public class AddLiftActivity extends AppCompatActivity {
    Button cancel_button, create_lift_button;

    EditText liftname_entry, sets_entry, reps_entry, weight_entry;

    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lift);

        liftname_entry = findViewById(R.id.liftname_entry);
        sets_entry = findViewById(R.id.sets_entry);
        reps_entry = findViewById(R.id.reps_entry);
        weight_entry = findViewById(R.id.weight_entry);

        cancel_button = findViewById(R.id.cancel_button);
        create_lift_button = findViewById(R.id.create_food_button);

        String sMonth;
        String sDay;
        if (MainActivity.selectedMonth < 10) {
            sMonth = "0" + MainActivity.selectedMonth;
        } else { sMonth = String.valueOf(MainActivity.selectedMonth); }
        if (MainActivity.selectedDay < 10) {
            sDay = "0" + MainActivity.selectedDay;
        } else { sDay = String.valueOf(MainActivity.selectedDay); }
        date = (MainActivity.selectedYear + "-" + sMonth + "-" + sDay);

        ExerciseDatabaseHelper db = new ExerciseDatabaseHelper(AddLiftActivity.this);

        create_lift_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                    String liftName = liftname_entry.getText().toString();
                    if (liftName.equals("")) {
                        Toast.makeText(AddLiftActivity.this, "Please provide a name.", Toast.LENGTH_SHORT).show();
                    } else {
                        int sets = Integer.parseInt(sets_entry.getText().toString());
                        int reps = Integer.parseInt(reps_entry.getText().toString());
                        int weight = Integer.parseInt(weight_entry.getText().toString());

                        Weightlifting newLift = new Weightlifting(liftName, sets, reps, weight LocalDate.parse(date));
                        MainActivity.newWo.getLiftList().add(newLift);
                        db.addFood(newLift);
                        toAddMealActivity(v);

                        Toast.makeText(AddLiftActivity.this, "Successfully created food.", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(AddLiftActivity.this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { toAddMealActivity(v);}
        });

    }

    public void toAddMealActivity(View view){
        Intent intent = new Intent(AddLiftActivity.this, AddMealActivity.class);
        startActivity(intent);
    }

}


