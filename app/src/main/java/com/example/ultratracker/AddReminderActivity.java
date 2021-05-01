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

public class AddReminderActivity extends AppCompatActivity implements DateSelectorDialog.DateSelectorListener, TimeSelectorDialog.TimeSelectorListener {
    Button cancel_button, create_reminder_button, edit_date_button, edit_time_button;
    EditText name_entry, description_entry;
    TextView date_display, time_display;

    private int reminderSelectedYear;
    private int reminderSelectedMonth;
    private int reminderSelectedDay;
    private int dueHour;
    private int dueMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        cancel_button = findViewById(R.id.reminder_cancel_button);
        create_reminder_button = findViewById(R.id.create_reminder_button);
        edit_date_button = findViewById(R.id.edit_reminder_date);
        edit_time_button = findViewById(R.id.edit_reminder_time);
        name_entry = findViewById(R.id.reminder_name_entry);
        date_display = findViewById(R.id.reminder_date_display);
        time_display = findViewById(R.id.reminder_time_display);
        description_entry = findViewById(R.id.reminder_description_entry);

        NotesDatabaseHelper db = new NotesDatabaseHelper(AddReminderActivity.this);

        applyTime(12,0);
        reminderSelectedYear = MainActivity.selectedYear;
        reminderSelectedMonth = MainActivity.selectedMonth;
        reminderSelectedDay = MainActivity.selectedDay;
        date_display.setText(reminderSelectedMonth + "/" + reminderSelectedDay + "/" + reminderSelectedYear);

        create_reminder_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                    String name = name_entry.getText().toString();
                    if (name.equals("")) {
                        Toast.makeText(AddReminderActivity.this, "Please provide a name for the reminder.", Toast.LENGTH_SHORT).show();
                    } else {
                        String description = description_entry.getText().toString();

                        LocalDate syn_date = LocalDate.of(reminderSelectedYear, reminderSelectedMonth, reminderSelectedDay);
                        LocalTime syn_time = LocalTime.of(dueHour, dueMinute);
                        Reminder reminder = new Reminder(name, syn_date, syn_time, description);
                        db.addReminder(reminder);
                        cancelPressed(v);
                        Toast.makeText(AddReminderActivity.this, "Successfully made reminder.", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e) {
                    Toast.makeText(AddReminderActivity.this, "Error creating reminder.", Toast.LENGTH_SHORT).show();
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
        reminderSelectedYear = year;
        reminderSelectedMonth = month;
        reminderSelectedDay = day;
        date_display.setText(reminderSelectedMonth + "/" + reminderSelectedDay + "/" + reminderSelectedYear);
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
        time_display.setText(String.format("%d:%02d %s", displayHour, minute, AMorPM));
    }

    public void cancelPressed(View view) {
        Intent intent = new Intent(AddReminderActivity.this, RemindersActivity.class);
        startActivity(intent);
    }

    public void toPDay(View view) {
        Intent intent = new Intent(AddReminderActivity.this, PDayActivity.class);
        startActivity(intent);
    }

    public void toMainActivity(View view) {
        Intent intent = new Intent(AddReminderActivity.this, MainActivity.class);
        startActivity(intent);
    }
}