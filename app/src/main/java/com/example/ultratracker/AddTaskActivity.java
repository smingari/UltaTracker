package com.example.ultratracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity implements DateSelectorDialog.DateSelectorListener, TimeSelectorDialog.TimeSelectorListener {
    Button cancel_button, create_task_button, edit_date_button, edit_time_button;
    EditText name_entry, description_entry;
    TextView date_display, due_time_display;
    Spinner priority_entry;

    private int priority;
    private int taskSelectedYear;
    private int taskSelectedMonth;
    private int taskSelectedDay;
    private int dueHour;
    private int dueMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        cancel_button = findViewById(R.id.cancel_button);
        create_task_button = findViewById(R.id.create_task_button);
        edit_date_button = findViewById(R.id.edit_date);
        edit_time_button = findViewById(R.id.edit_time);
        name_entry = findViewById(R.id.name_entry);
        date_display = findViewById(R.id.date_display);
        due_time_display = findViewById(R.id.due_time_display);
        description_entry = findViewById(R.id.description_entry);
        priority_entry = findViewById(R.id.priority_entry);

        TaskDatabaseHelper db = new TaskDatabaseHelper(AddTaskActivity.this);

        applyTime(12,0);
        taskSelectedYear = MainActivity.selectedYear;
        taskSelectedMonth = MainActivity.selectedMonth;
        taskSelectedDay = MainActivity.selectedDay;
        date_display.setText(taskSelectedMonth + "/" + taskSelectedDay + "/" + taskSelectedYear);

        create_task_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                    String name = name_entry.getText().toString();
                    if (name.equals("")) {
                        Toast.makeText(AddTaskActivity.this, "Please provide a name for the task.", Toast.LENGTH_SHORT).show();
                    } else {
                        String description = description_entry.getText().toString();

                        LocalDate syn_date = LocalDate.of(taskSelectedYear, taskSelectedMonth, taskSelectedDay);
                        LocalTime syn_time = LocalTime.of(dueHour, dueMinute);

                        Task task = new Task(name, syn_date, syn_date, syn_time, description, priority, false);
                        db.addOne(task);
                        toPDay(v);
                        Toast.makeText(AddTaskActivity.this, "Successfully made task.", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e) {
                    Toast.makeText(AddTaskActivity.this, "Error creating task.", Toast.LENGTH_SHORT).show();
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

        final List<Integer> priorities = new ArrayList<Integer>();

        priorities.add(1);
        priorities.add(2);
        priorities.add(3);
        priorities.add(4);
        priorities.add(5);

        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, priorities);
        priority_entry.setAdapter(dataAdapter);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        priority_entry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                priority = priorities.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
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
        due_time_display.setText(String.format("%d:%02d %s", displayHour, minute, AMorPM));
    }

    public void cancelPressed(View view) {
        if (MainActivity.inTodo) {
            Intent intent = new Intent(AddTaskActivity.this, TodoActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(AddTaskActivity.this, PDayActivity.class);
            startActivity(intent);
        }
    }

    public void toPDay(View view) {
        Intent intent = new Intent(AddTaskActivity.this, PDayActivity.class);
        startActivity(intent);
    }

    public void toMainActivity(View view) {
        Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
        startActivity(intent);
    }
}