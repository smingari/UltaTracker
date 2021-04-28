package com.example.ultratracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.example.ultratracker.TaskDatabaseHelper;

public class TodoActivity extends AppCompatActivity {
    TableRow selectedRow;
    Button btn_taskAdd, btn_taskDelete, btn_taskEdit, btn_taskReminder, btn_taskComplete, btn_view;
    TaskDatabaseHelper taskDatabaseHelper;
    TableLayout taskTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        btn_taskAdd = findViewById(R.id.todo_add_button);
        btn_taskDelete = findViewById(R.id.todo_delete_button);
        btn_taskEdit = findViewById(R.id.todo_edit_button);
        btn_taskReminder = findViewById(R.id.todo_reminder_button);
        btn_taskComplete = findViewById(R.id.todo_complete_button);
        btn_view = findViewById(R.id.todo_view_button);

        taskTable = findViewById(R.id.todo_table);

        btn_taskDelete.setVisibility(View.INVISIBLE);
        btn_taskEdit.setVisibility(View.INVISIBLE);
        btn_taskReminder.setVisibility(View.INVISIBLE);
        btn_taskComplete.setVisibility(View.INVISIBLE);
        btn_view.setVisibility(View.INVISIBLE);

        MainActivity.inTodo = true;

        taskDatabaseHelper = new TaskDatabaseHelper(this);
        init_task_table();
    }

    public void init_task_table() {

        List<Task> taskList = taskDatabaseHelper.getTodoList();
        int dbSize = taskDatabaseHelper.getTodoList().size();

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
            row.setBackgroundResource(R.drawable.list_selector_background);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedRow != null) {
                        selectedRow.setSelected(false);
                    }
                    selectedRow = row;
                    row.setSelected(true);
                    MainActivity.selectedTask = taskList.get(row.getId());
                    btn_taskDelete.setVisibility(View.VISIBLE);
                    btn_taskReminder.setVisibility(View.VISIBLE);
                    btn_taskComplete.setVisibility(View.VISIBLE);
                    btn_taskEdit.setVisibility(View.VISIBLE);
                    btn_view.setVisibility(View.VISIBLE);
                }
            });

            TextView t1v = new TextView(this);
            t1v.setGravity(Gravity.CENTER_HORIZONTAL);
            String taskName = taskList.get(i).getName();
            if (taskName.length() > 12) { taskName = (taskName.substring(0, Math.min(taskName.length(), 12))) + ".."; }
            t1v.setText(taskName);
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
        boolean success = taskDatabaseHelper.deleteTask(MainActivity.selectedTask);
        taskTable.removeView(selectedRow);
        selectedRow = null;
        MainActivity.selectedTask = null;
        btn_taskDelete.setVisibility(View.INVISIBLE);
        btn_taskReminder.setVisibility(View.INVISIBLE);
        btn_taskComplete.setVisibility(View.INVISIBLE);
        btn_taskEdit.setVisibility(View.INVISIBLE);
        btn_view.setVisibility(View.INVISIBLE);
    }

    public void markComplete(View view) {
        boolean success = taskDatabaseHelper.modifyComplete(MainActivity.selectedTask, true);
        if (success) {
            Toast.makeText(this,  "Successfully marked complete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,  "Error marking complete.", Toast.LENGTH_SHORT).show();
        }
        taskTable.removeView(selectedRow);
        selectedRow = null;
        MainActivity.selectedTask = null;
        btn_taskDelete.setVisibility(View.INVISIBLE);
        btn_taskReminder.setVisibility(View.INVISIBLE);
        btn_taskComplete.setVisibility(View.INVISIBLE);
        btn_taskEdit.setVisibility(View.INVISIBLE);
        btn_view.setVisibility(View.INVISIBLE);
    }

    public void toMainActivity(View view) {
        MainActivity.inTodo = false;
        Intent intent = new Intent(TodoActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void toAddTaskActivity(View view) {
        Intent intent = new Intent(TodoActivity.this, AddTaskActivity.class);
        startActivity(intent);
    }

    public void toEditTaskActivity(View view) {
        MainActivity.inTodo = false;
        Intent intent = new Intent(TodoActivity.this, EditTaskActivity.class);
        startActivity(intent);
    }

    public void openViewDialog(View view) {
        ViewTaskDialog viewTaskDialog = new ViewTaskDialog(MainActivity.selectedTask);
        viewTaskDialog.show(getSupportFragmentManager(), "view task dialog");
    }
}