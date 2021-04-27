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

public class AddReminderActivity extends AppCompatActivity implements DateSelectorDialog.DateSelectorListener {
    Button cancel_button, create_reminder_button, edit_date_button;
    EditText name_entry, description_entry;
    TextView date_display;

    private int reminderSelectedYear;
    private int reminderSelectedMonth;
    private int reminderSelectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        cancel_button = findViewById(R.id.reminder_cancel_button);
        create_reminder_button = findViewById(R.id.create_reminder_button);
        edit_date_button = findViewById(R.id.edit_reminder_date);
        name_entry = findViewById(R.id.reminder_name_entry);
        date_display = findViewById(R.id.reminder_date_display);
        description_entry = findViewById(R.id.reminder_description_entry);

        //remindersDatabaseHelper db = new remindersDatabaseHelper(AddReminderActivity.this);

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

                        Reminder reminder = new Reminder(name, syn_date, description);
                        //db.addReminder(reminder);
                        toMainActivity(v);
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
    }

    public void openDateDialog() {
        DateSelectorDialog dateDialog = new DateSelectorDialog();
        dateDialog.show(getSupportFragmentManager(), "date dialog");
    }

    @Override
    public void applyDate(int year, int month, int day) {
        reminderSelectedYear = year;
        reminderSelectedMonth = month;
        reminderSelectedDay = day;
        date_display.setText(reminderSelectedMonth + "/" + reminderSelectedDay + "/" + reminderSelectedYear);
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