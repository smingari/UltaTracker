package com.example.ultratracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalDate;
import java.time.LocalTime;

public class AddRideActivity extends AppCompatActivity implements DateSelectorDialog.DateSelectorListener, TimeSelectorDialog.TimeSelectorListener {
    Button cancel_button, create_ride_button, edit_date_button, edit_time_button;
    EditText duration_entry, distance_entry, bw_entry;
    TextView date_display, completed_time_display;


    private int taskSelectedYear;
    private int taskSelectedMonth;
    private int taskSelectedDay;
    private int dueHour;
    private int dueMinute, caloriesBurned;
    private double bodyweight, pace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ride);

        cancel_button = findViewById(R.id.cancel_button);
        create_ride_button = findViewById(R.id.create_run_button);
        edit_date_button = findViewById(R.id.add_run_edit_date);
        edit_time_button = findViewById(R.id.edit_time);
        duration_entry = findViewById(R.id.add_run_duration);
        date_display = findViewById(R.id.add_run_completed_date_textview);
        completed_time_display = findViewById(R.id.add_run_completed_time_textview);
        distance_entry = findViewById(R.id.add_run_distance);
        bw_entry = findViewById(R.id.add_bw);

        ExerciseDatabaseHelper e_db = new ExerciseDatabaseHelper(AddRideActivity.this);

        applyTime(12,0);
        taskSelectedYear = MainActivity.selectedYear;
        taskSelectedMonth = MainActivity.selectedMonth;
        taskSelectedDay = MainActivity.selectedDay;
        date_display.setText(taskSelectedMonth + "/" + taskSelectedDay + "/" + taskSelectedYear);

        create_ride_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                    bodyweight = Double.parseDouble(bw_entry.getText().toString());
                    pace = Double.parseDouble(distance_entry.getText().toString()) / Double.parseDouble(duration_entry.getText().toString());
                    caloriesBurned = (int)(bodyweight * 5.5 * pace * 2);

                    LocalDate syn_date = LocalDate.of(taskSelectedYear,taskSelectedMonth,taskSelectedDay);
                    LocalTime syn_time = LocalTime.of(dueHour, dueMinute);

                    Ride ride =  new Ride(syn_date, syn_time, Integer.parseInt(duration_entry.getText().toString()), caloriesBurned,
                            Double.parseDouble(distance_entry.getText().toString()), pace);

                    e_db.addRide(ride);
                    toEDay(v);
                    Toast.makeText(AddRideActivity.this, "Successfully added ride.", Toast.LENGTH_SHORT).show();

                }catch(Exception e){
                    Toast.makeText(AddRideActivity.this, "Error creating ride.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        edit_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDateDialog();
            }
        });

        edit_time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimeDialog();
            }
        });

    }

    public void openDateDialog() {
        DateSelectorDialog dateDialog = new DateSelectorDialog();
        dateDialog.show(getSupportFragmentManager(), "date dialog");
    }

    public void openTimeDialog() {
        TimeSelectorDialog timeDialog = new TimeSelectorDialog();
        timeDialog.show(getSupportFragmentManager(), "time dialog");
    }

    @Override
    public void applyDate(int year, int month, int day) {
        taskSelectedYear = year;
        taskSelectedMonth = month;
        taskSelectedDay = day;
        date_display.setText(taskSelectedMonth + "/" + taskSelectedDay + "/" + taskSelectedYear);
    }

    @Override
    public void applyTime(int hour, int minute) {
        dueHour = hour;
        dueMinute = minute;
        int displayHour;
        String AMorPM;
        System.out.println(hour + ":" + minute);
        if(hour < 12) {
            AMorPM = "AM";
            displayHour = dueHour;
        } else {
            AMorPM = "PM";
            displayHour = dueHour-12;
        }
        if(displayHour == 0) displayHour = 12;
        completed_time_display.setText(String.format("%d:%02d %s", displayHour, minute, AMorPM));
    }

    public void toEDay(View view) {
        Intent intent = new Intent(AddRideActivity.this, EDayActivity.class);
        startActivity(intent);
    }

    public void toMainActivity(View view) {
        Intent intent = new Intent(AddRideActivity.this, MainActivity.class);
        startActivity(intent);
    }
}


