package com.example.ultratracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.util.List;

import com.example.ultratracker.MainActivity;


public class PDayActivity extends AppCompatActivity {

    Button btn_taskAdd, btn_taskDelete, btn_taskEdit, btn_taskReminder, btn_taskComplete, btn_moveToTasks;

    TableRow selectedRow;
    TaskDatabaseHelper taskDatabaseHelper;
    Task selectedTask;
    Task selectedCTask;

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
        btn_moveToTasks = findViewById(R.id.move_to_tasks_button);

        btn_taskAdd.setVisibility(View.VISIBLE);
        btn_taskDelete.setVisibility(View.INVISIBLE);
        btn_taskEdit.setVisibility(View.INVISIBLE);
        btn_taskReminder.setVisibility(View.INVISIBLE);
        btn_taskComplete.setVisibility(View.INVISIBLE);
        btn_moveToTasks.setBackgroundColor(getResources().getColor(R.color.grey));
        btn_moveToTasks.setClickable(false);

        // Create the table of tasks programmatically
        taskDatabaseHelper = new TaskDatabaseHelper(this);
        init_task_table();
        init_completed_table();
    }

    public void init_task_table() {
        TableLayout taskTable = findViewById(R.id.task_table);

        // Format selected date for task query
        String sMonth;
        String sDay;
        if (MainActivity.selectedMonth < 10) {
            sMonth = "0" + MainActivity.selectedMonth;
        } else { sMonth = String.valueOf(MainActivity.selectedMonth); }
        if (MainActivity.selectedDay < 10) {
            sDay = "0" + MainActivity.selectedDay;
        } else { sDay = String.valueOf(MainActivity.selectedDay); }

        List<Task> taskList = taskDatabaseHelper.getByDate(MainActivity.selectedYear + "-" + sMonth + "-" + sDay);
        int dbSize = taskDatabaseHelper.getByDate(MainActivity.selectedYear + "-" + sMonth + "-" + sDay).size();

        // Set up table header
        TableRow taskTableHeader = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        taskTableHeader.setLayoutParams(lp);

        // First column header
        TextView tv0 = new TextView(this);
        tv0.setPaintFlags(tv0.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv0.setText(" Task ");
        tv0.setGravity(Gravity.CENTER_HORIZONTAL);
        taskTableHeader.addView(tv0);

        // Second column header
        TextView tv1 = new TextView(this);
        tv1.setPaintFlags(tv1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv1.setText(" Due Date ");
        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
        taskTableHeader.addView(tv1);

        // Third column header
        TextView tv2 = new TextView(this);
        tv2.setPaintFlags(tv2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv2.setText(" Priority Level ");
        tv2.setGravity(Gravity.CENTER_HORIZONTAL);
        taskTableHeader.addView(tv2);

        // Add header row to table
        taskTable.addView(taskTableHeader);

        // Add rows dynamically from database
        for (int i = 0; i < dbSize; i++) {
            TableRow row = new TableRow(this);
            row.setId(i);

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedRow == null) {
                        selectedRow = row;
                        taskTable.setBackgroundColor(getResources().getColor(R.color.white));
                        row.setBackgroundColor(getResources().getColor(R.color.teal_200));
                    } else {
                        selectedRow.setBackgroundColor(getResources().getColor(R.color.white));
                        row.setBackgroundColor(getResources().getColor(R.color.teal_200));
                        selectedRow = row;
                    }
                    selectedTask = taskList.get(row.getId());
                    btn_taskDelete.setVisibility(View.VISIBLE);
                    btn_taskReminder.setVisibility(View.VISIBLE);
                    btn_taskComplete.setVisibility(View.VISIBLE);
                    btn_taskEdit.setVisibility(View.VISIBLE);
                }
            });

            TextView t1v = new TextView(this);
            String taskName = taskList.get(i).getName();
            if (taskName.length() > 12) { taskName = (taskName.substring(0, Math.min(taskName.length(), 12))) + ".."; }
            t1v.setText(taskName);
            t1v.setGravity(Gravity.CENTER_HORIZONTAL);
            row.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText(taskList.get(i).getDueDate() + " @ " + taskList.get(i).getDueTime());
            t2v.setGravity(Gravity.CENTER_HORIZONTAL);
            row.addView(t2v);

            TextView t3v = new TextView(this);
            t3v.setText(Integer.toString(taskList.get(i).getPriority()));
            t3v.setGravity(Gravity.CENTER_HORIZONTAL);
            row.addView(t3v);

            taskTable.addView(row);
        }

    }

    public void init_completed_table() {
        TableLayout taskTable = findViewById(R.id.completed_table);

        // Format selected date for task query
        String sMonth;
        String sDay;
        if (MainActivity.selectedMonth < 10) {
            sMonth = "0" + MainActivity.selectedMonth;
        } else { sMonth = String.valueOf(MainActivity.selectedMonth); }
        if (MainActivity.selectedDay < 10) {
            sDay = "0" + MainActivity.selectedDay;
        } else { sDay = String.valueOf(MainActivity.selectedDay); }

        List<Task> taskList = taskDatabaseHelper.getByDateCompleted(MainActivity.selectedYear + "-" + sMonth + "-" + sDay);
        int dbSize = taskDatabaseHelper.getByDateCompleted(MainActivity.selectedYear + "-" + sMonth + "-" + sDay).size();

        // Set up table header
        TableRow taskTableHeader = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        taskTableHeader.setLayoutParams(lp);

        // First column header
        TextView tv0 = new TextView(this);
        tv0.setPaintFlags(tv0.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv0.setText(" Task ");
        tv0.setGravity(Gravity.CENTER_HORIZONTAL);
        taskTableHeader.addView(tv0);

        // Second column header
        TextView tv1 = new TextView(this);
        tv1.setPaintFlags(tv1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv1.setText(" Due Date ");
        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
        taskTableHeader.addView(tv1);

        // Third column header
        TextView tv2 = new TextView(this);
        tv2.setPaintFlags(tv2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv2.setText(" Priority Level ");
        tv2.setGravity(Gravity.CENTER_HORIZONTAL);
        taskTableHeader.addView(tv2);

        // Add header row to table
        taskTable.addView(taskTableHeader);

        // Add rows dynamically from database
        for (int i = 0; i < dbSize; i++) {
            TableRow row = new TableRow(this);
            row.setId(i);

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedRow == null) {
                        selectedRow = row;
                        taskTable.setBackgroundColor(getResources().getColor(R.color.white));
                        row.setBackgroundColor(getResources().getColor(R.color.teal_200));
                    } else {
                        selectedRow.setBackgroundColor(getResources().getColor(R.color.white));
                        row.setBackgroundColor(getResources().getColor(R.color.teal_200));
                        selectedRow = row;
                    }
                    selectedCTask = taskList.get(row.getId());
                    btn_moveToTasks.setBackgroundColor(getResources().getColor(R.color.teal_200));
                    btn_moveToTasks.setClickable(true);
                }
            });

            TextView t1v = new TextView(this);
            String taskName = taskList.get(i).getName();
            if (taskName.length() > 12) { taskName = (taskName.substring(0, Math.min(taskName.length(), 12))) + ".."; }
            t1v.setText(taskName);
            t1v.setGravity(Gravity.CENTER_HORIZONTAL);
            row.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText(taskList.get(i).getDueDate() + " @ " + taskList.get(i).getDueTime());
            t2v.setGravity(Gravity.CENTER_HORIZONTAL);
            row.addView(t2v);

            TextView t3v = new TextView(this);
            t3v.setText(Integer.toString(taskList.get(i).getPriority()));
            t3v.setGravity(Gravity.CENTER_HORIZONTAL);
            row.addView(t3v);

            taskTable.addView(row);
        }

    }

    public void deleteT(View view) {
        Toast.makeText(this,  selectedTask.getName(), Toast.LENGTH_SHORT).show();
        boolean success = taskDatabaseHelper.deleteTask(selectedTask);
        if (success) {
            Toast.makeText(this,  "Successfully deleted task.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,  "Error deleting task.", Toast.LENGTH_SHORT).show();
        }
        refresh(view);
    }

    public void refresh(View view) {
        Intent intent = new Intent(PDayActivity.this, PDayActivity.class);
        startActivity(intent);
    }

    public void markComplete(View view) {
        boolean success = taskDatabaseHelper.modifyComplete(selectedTask, true);
        if (success) {
            Toast.makeText(this,  "Successfully marked complete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,  "Error marking complete.", Toast.LENGTH_SHORT).show();
        }
        refresh(view);
    }

    public void toMainActivity(View view) {
        Intent intent = new Intent(PDayActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void toEditTaskActivity(View view) {
        Intent intent = new Intent(PDayActivity.this, EditTaskActivity.class);
        startActivity(intent);
    }

    public void toAddTaskActivity(View view) {
        Intent intent = new Intent(PDayActivity.this, AddTaskActivity.class);
        startActivity(intent);
    }
}