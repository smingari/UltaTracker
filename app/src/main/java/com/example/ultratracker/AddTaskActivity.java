package com.example.ultratracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalTime;

public class AddTaskActivity extends AppCompatActivity implements DateSelectorDialog.DateSelectorListener {
    Button cancel_button, create_task_button, edit_date_button;
    EditText name_entry, due_time_entry, description_entry, priority_entry;
    TextView date_display;

    private int taskSelectedYear;
    private int taskSelectedMonth;
    private int taskSelectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        cancel_button = findViewById(R.id.cancel_button);
        create_task_button = findViewById(R.id.create_task_button);
        edit_date_button = findViewById(R.id.edit_date);
        name_entry = findViewById(R.id.name_entry);
        date_display = findViewById(R.id.date_display);
        due_time_entry = findViewById(R.id.due_time_entry);
        description_entry = findViewById(R.id.description_entry);
        priority_entry = findViewById(R.id.priority_entry);

        TaskDatabaseHelper db = new TaskDatabaseHelper(AddTaskActivity.this);

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
                    String due_time = due_time_entry.getText().toString();
                    String description = description_entry.getText().toString();
                    String prior = priority_entry.getText().toString();
                    int priority = Integer.parseInt(prior);

                    LocalDate syn_date = LocalDate.of(taskSelectedYear,taskSelectedMonth,taskSelectedDay);
                    LocalTime syn_time = LocalTime.parse(due_time.length() < 5 ? "0" + due_time : due_time); //quick and dirty hack plz remove

                    Task task = new Task(name, syn_date, syn_date, syn_time, description, priority, false);
                    db.addOne(task);
                    toMainActivity(v);
                    Toast.makeText(AddTaskActivity.this, "Successfully made task.", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    Toast.makeText(AddTaskActivity.this, "Error creating task.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        edit_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    public void openDialog() {
        DateSelectorDialog exampleDialog = new DateSelectorDialog(taskSelectedYear, taskSelectedMonth, taskSelectedDay);
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyDate(int year, int month, int day) {
        taskSelectedYear = year;
        taskSelectedMonth = month;
        taskSelectedDay = day;
        date_display.setText(taskSelectedMonth + "/" + taskSelectedDay + "/" + taskSelectedYear);
    }

    public void createTask() {

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