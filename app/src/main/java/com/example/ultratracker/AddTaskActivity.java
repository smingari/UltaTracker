package com.example.ultratracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalTime;

public class AddTaskActivity extends AppCompatActivity {
    Button cancel_button, create_task_button;
    EditText name_entry, due_date_entry, due_time_entry, description_entry, priority_entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        cancel_button = findViewById(R.id.cancel_button);
        create_task_button = findViewById(R.id.create_task_button);
        name_entry = findViewById(R.id.name_entry);
        due_date_entry = findViewById(R.id.due_date_entry);
        due_time_entry = findViewById(R.id.due_time_entry);
        description_entry = findViewById(R.id.description_entry);
        priority_entry = findViewById(R.id.priority_entry);

        TaskDatabaseHelper db = new TaskDatabaseHelper(AddTaskActivity.this);

        create_task_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = name_entry.getText().toString();
                    String due_date = due_date_entry.getText().toString();
                    String due_time = due_time_entry.getText().toString();
                    String description = description_entry.getText().toString();
                    String prior = priority_entry.getText().toString();
                    int priority = Integer.parseInt(prior);

                    String[] date = due_date.split("/");
                    LocalDate syn_date = LocalDate.parse(date[2] + "-" + date[0] + "-" + date[1]);
                    LocalTime syn_time = LocalTime.parse(due_time);

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