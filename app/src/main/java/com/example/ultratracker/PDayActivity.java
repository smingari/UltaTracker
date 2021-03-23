package com.example.ultratracker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;


public class PDayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_day);

        // Create the table of tasks programmatically
        init_table();

    }

    public void init_table() {
        TableLayout taskTable = findViewById(R.id.task_table);
        TaskDatabaseHelper taskDatabaseHelper = new TaskDatabaseHelper(this);
        List<Task> taskList = taskDatabaseHelper.getAll();
        int dbSize = taskDatabaseHelper.getAll().size();

        // Set up table header
        TableRow taskTableHeader = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        taskTableHeader.setLayoutParams(lp);

        // First column header
        TextView tv0 = new TextView(this);
        tv0.setText(" Task ");
        taskTableHeader.addView(tv0);

        // Second column header
        TextView tv1 = new TextView(this);
        tv1.setText(" Due Date ");
        taskTableHeader.addView(tv1);

        // Third column header
        TextView tv2 = new TextView(this);
        tv2.setText(" Priority Level ");
        taskTableHeader.addView(tv2);

        // Add header row to table
        taskTable.addView(taskTableHeader);

        // Add rows dynamically from database
        for (int i = 0; i < dbSize; i++) {
            TableRow row = new TableRow(this);

            TextView t1v = new TextView(this);
            t1v.setText(taskList.get(i).getName());
            t1v.setGravity(Gravity.CENTER);
            row.addView(t1v);

            TextView t2v = new TextView(this);
            t1v.setText(taskList.get(i).getDueDate());
            t1v.setGravity(Gravity.CENTER);
            row.addView(t2v);

            TextView t3v = new TextView(this);
            t3v.setText(taskList.get(i).getPriority());
            t3v.setGravity(Gravity.CENTER);
            row.addView(t3v);

            taskTable.addView(row);
        }

    }
}