package com.example.ultratracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import com.example.ultratracker.MainActivity;


public class PDayActivity extends AppCompatActivity {

    Button btn_taskAdd, btn_taskDelete, btn_taskEdit, btn_taskReminder, btn_taskComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_day);

        setContentView(R.layout.activity_p_day);
        TextView date = (TextView)findViewById(R.id.current_date);
        date.setText(MainActivity.selectedMonth + "/" + MainActivity.selectedDay + "/" + MainActivity.selectedYear);

        btn_taskAdd = findViewById(R.id.add_task_button);
        btn_taskDelete = findViewById(R.id.delete_button);
        btn_taskEdit = findViewById(R.id.edit_task_button);
        btn_taskReminder = findViewById(R.id.set_reminder_button);
        btn_taskComplete = findViewById(R.id.mark_complete_button);

        btn_taskAdd.setVisibility(View.VISIBLE);
        btn_taskDelete.setVisibility(View.VISIBLE);
        btn_taskEdit.setVisibility(View.INVISIBLE);
        btn_taskReminder.setVisibility(View.INVISIBLE);
        btn_taskComplete.setVisibility(View.INVISIBLE);

        // Create the table of tasks programmatically
        init_task_table();
        //init_completed_table();
    }

    public void init_task_table() {
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
        tv0.setGravity(Gravity.CENTER_HORIZONTAL);
        taskTableHeader.addView(tv0);

        // Second column header
        TextView tv1 = new TextView(this);
        tv1.setText(" Due Date ");
        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
        taskTableHeader.addView(tv1);

        // Third column header
        TextView tv2 = new TextView(this);
        tv2.setText(" Priority Level ");
        tv2.setGravity(Gravity.CENTER_HORIZONTAL);
        taskTableHeader.addView(tv2);

        // Add header row to table
        taskTable.addView(taskTableHeader);

        // Add rows dynamically from database
        for (int i = 0; i < dbSize; i++) {
            TableRow row = new TableRow(this);

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    taskTable.setBackgroundColor(getResources().getColor(R.color.white));
                    row.setBackgroundColor(getResources().getColor(R.color.teal_200));
                    btn_taskReminder.setVisibility(View.VISIBLE);
                    btn_taskComplete.setVisibility(View.VISIBLE);
                    btn_taskEdit.setVisibility(View.VISIBLE);
                }
            });

            TextView t1v = new TextView(this);
            t1v.setText(taskList.get(i).getName());
            row.addView(t1v);

            TextView t2v = new TextView(this);
            t1v.setText(taskList.get(i).getDueDate());
            row.addView(t2v);

            //TextView t3v = new TextView(this);
            //t3v.setText(taskList.get(i).getPriority());
            //row.addView(t3v);

            taskTable.addView(row);
        }

    }

    /*public void init_completed_table() {
        TableLayout taskTable = findViewById(R.id.completed_table);
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
        tv0.setGravity(Gravity.CENTER_HORIZONTAL);
        taskTableHeader.addView(tv0);

        // Second column header
        TextView tv1 = new TextView(this);
        tv1.setText(" Due Date ");
        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
        taskTableHeader.addView(tv1);

        // Third column header
        TextView tv2 = new TextView(this);
        tv2.setText(" Priority Level ");
        tv2.setGravity(Gravity.CENTER_HORIZONTAL);
        taskTableHeader.addView(tv2);

        // Add header row to table
        taskTable.addView(taskTableHeader);

        // Add rows dynamically from database
        for (int i = 0; i < dbSize; i++) {
            TableRow row = new TableRow(this);

            TextView t1v = new TextView(this);
            t1v.setText(taskList.get(i).getName());
            row.addView(t1v);

            TextView t2v = new TextView(this);
            t1v.setText(taskList.get(i).getDueDate());
            row.addView(t2v);

            TextView t3v = new TextView(this);
            t3v.setText(taskList.get(i).getPriority());
            row.addView(t3v);

            taskTable.addView(row);
        }

    }*/

    public void toMainActivity(View view) {
        Intent intent = new Intent(PDayActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void toAddTaskActivity(View view) {
        Intent intent = new Intent(PDayActivity.this, AddTaskActivity.class);
        startActivity(intent);
    }
}