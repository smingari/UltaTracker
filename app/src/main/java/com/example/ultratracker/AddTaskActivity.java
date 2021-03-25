package com.example.ultratracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
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