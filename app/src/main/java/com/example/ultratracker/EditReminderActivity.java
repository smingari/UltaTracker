package com.example.ultratracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalTime;

public class EditReminderActivity extends AppCompatActivity implements DateSelectorDialog.DateSelectorListener {
    Button cancel_button, update_reminder_button, edit_date_button;
    EditText name_entry, description_entry;
    TextView date_display;

    private int reminderSelectedYear;
    private int reminderSelectedMonth;
    private int reminderSelectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reminder);

        cancel_button = findViewById(R.id.edit_reminder_cancel_button);
        update_reminder_button = findViewById(R.id.edit_reminder_button);
        edit_date_button = findViewById(R.id.edit_reminder_date2);
        name_entry = findViewById(R.id.edit_reminder_name_entry);
        date_display = findViewById(R.id.edit_reminder_date_display);
        description_entry = findViewById(R.id.edit_reminder_description_entry);

        name_entry.setText(RemindersActivity.selectedReminder.getName());
        date_display.setText(RemindersActivity.selectedReminder.getDate());
        description_entry.setText(RemindersActivity.selectedReminder.getDesc());

        NotesDatabaseHelper db = new NotesDatabaseHelper(EditReminderActivity.this);

        reminderSelectedYear = MainActivity.selectedYear;
        reminderSelectedMonth = MainActivity.selectedMonth;
        reminderSelectedDay = MainActivity.selectedDay;

        update_reminder_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                    String name = name_entry.getText().toString();
                    String description = description_entry.getText().toString();

                    LocalDate syn_date;
                    if(date_display.getText().equals(RemindersActivity.selectedReminder.getDate())) {
                        syn_date = LocalDate.parse(date_display.getText());
                    } else {
                        syn_date = LocalDate.of(reminderSelectedYear, reminderSelectedMonth, reminderSelectedDay);
                    }

                    RemindersActivity.selectedReminder.setName(name);
                    RemindersActivity.selectedReminder.setDate(syn_date);
                    RemindersActivity.selectedReminder.setDesc(description);
                    if(db.editReminder(RemindersActivity.selectedReminder)) {
                        toMainActivity(v);
                        Toast.makeText(EditReminderActivity.this, "Successfully updated reminder.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditReminderActivity.this, "Error updating reminder.", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e) {
                    Toast.makeText(EditReminderActivity.this, "Error updating reminder.", Toast.LENGTH_SHORT).show();
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
        System.out.println("open date dialog");
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
        Intent intent = new Intent(EditReminderActivity.this, RemindersActivity.class);
        startActivity(intent);
    }

    public void toMainActivity(View view) {
        Intent intent = new Intent(EditReminderActivity.this, MainActivity.class);
        startActivity(intent);
    }
}