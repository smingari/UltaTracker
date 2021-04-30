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


public class PDayActivity extends AppCompatActivity {

    Button btn_taskAdd, btn_taskDelete, btn_taskEdit, btn_taskReminder, btn_taskComplete, btn_moveToTasks, btn_view;

    TableLayout taskTable;
    TableLayout completedTable;
    TableRow selectedRow;
    TaskDatabaseHelper taskDatabaseHelper;

    boolean taskSelected;
    boolean completedTaskSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_day);

        setContentView(R.layout.activity_p_day);
        TextView date = (TextView)findViewById(R.id.current_date);
        date.setText(String.format("%d/%d/%d", MainActivity.selectedMonth, MainActivity.selectedDay, MainActivity.selectedYear));

        btn_taskAdd = findViewById(R.id.add_task_button);
        btn_taskDelete = findViewById(R.id.delete_button);
        btn_taskEdit = findViewById(R.id.edit_task_button);
        btn_taskReminder = findViewById(R.id.set_reminder_button);
        btn_taskComplete = findViewById(R.id.mark_complete_button);
        btn_moveToTasks = findViewById(R.id.move_to_tasks_button);
        btn_view = findViewById(R.id.pday_view_button);

        btn_taskAdd.setVisibility(View.VISIBLE);
        btn_taskDelete.setVisibility(View.INVISIBLE);
        btn_taskEdit.setVisibility(View.INVISIBLE);
        btn_taskReminder.setVisibility(View.INVISIBLE);
        btn_taskComplete.setVisibility(View.INVISIBLE);
        btn_moveToTasks.setVisibility(View.INVISIBLE);
        btn_view.setVisibility(View.INVISIBLE);

        // Create the table of tasks programmatically
        taskDatabaseHelper = new TaskDatabaseHelper(this);
        init_task_table();
        init_completed_table();
    }

    public void init_task_table() {
        taskTable = findViewById(R.id.task_table);

        // Format selected date for task query
        String sMonth;
        String sDay;
        if (MainActivity.selectedMonth < 10) {
            sMonth = "0" + MainActivity.selectedMonth;
        } else { sMonth = String.valueOf(MainActivity.selectedMonth); }
        if (MainActivity.selectedDay < 10) {
            sDay = "0" + MainActivity.selectedDay;
        } else { sDay = String.valueOf(MainActivity.selectedDay); }

        List<Task> taskList = taskDatabaseHelper.getByDateAndCompletion(MainActivity.selectedYear + "-" + sMonth + "-" + sDay, 0);
        int dbSize = taskList.size();

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
                    boolean isComplete;
                    if (selectedRow != null) {
                        selectedRow.setSelected(false);
                    }
                    selectedRow = row;
                    row.setSelected(true);
                    MainActivity.selectedTask = taskList.get(row.getId());
                    isComplete = MainActivity.selectedTask.isComplete();
                    taskSelected = !isComplete;
                    completedTaskSelected = isComplete;
                    showButtons();
                    if(isComplete) {
                        btn_moveToTasks.setVisibility(View.VISIBLE);
                    } else {
                        btn_moveToTasks.setVisibility(View.INVISIBLE);
                    }
                }
            });

            TextView t1v = new TextView(this);
            String taskName = taskList.get(i).getName();
            if (taskName.length() > 12) { taskName = (taskName.substring(0, Math.min(taskName.length(), 12))) + ".."; }
            t1v.setText(taskName);
            t1v.setGravity(Gravity.CENTER_HORIZONTAL);
            row.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText(String.format("%s @ %s", taskList.get(i).getDueDate(), taskList.get(i).getDueTime()));
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
        completedTable = findViewById(R.id.completed_table);

        // Format selected date for task query
        String sMonth;
        String sDay;
        if (MainActivity.selectedMonth < 10) {
            sMonth = "0" + MainActivity.selectedMonth;
        } else { sMonth = String.valueOf(MainActivity.selectedMonth); }
        if (MainActivity.selectedDay < 10) {
            sDay = "0" + MainActivity.selectedDay;
        } else { sDay = String.valueOf(MainActivity.selectedDay); }

        List<Task> taskList = taskDatabaseHelper.getByDateAndCompletion(MainActivity.selectedYear + "-" + sMonth + "-" + sDay, 1);
        int dbSize = taskList.size();

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
        completedTable.addView(taskTableHeader);

        // Add rows dynamically from database
        for (int i = 0; i < dbSize; i++) {
            TableRow row = new TableRow(this);
            row.setId(i);
            row.setBackgroundResource(R.drawable.list_selector_background);

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isComplete;
                    if (selectedRow != null) {
                        selectedRow.setSelected(false);
                    }
                    selectedRow = row;
                    row.setSelected(true);
                    MainActivity.selectedTask = taskList.get(row.getId());
                    isComplete = MainActivity.selectedTask.isComplete();
                    taskSelected = !isComplete;
                    completedTaskSelected = isComplete;
                    showButtons();
                    if(isComplete) {
                        btn_moveToTasks.setVisibility(View.VISIBLE);
                    } else {
                        btn_moveToTasks.setVisibility(View.INVISIBLE);
                    }
                }
            });

            TextView t1v = new TextView(this);
            String taskName = taskList.get(i).getName();
            if (taskName.length() > 12) { taskName = (taskName.substring(0, Math.min(taskName.length(), 12))) + ".."; }
            t1v.setText(taskName);
            t1v.setGravity(Gravity.CENTER_HORIZONTAL);
            row.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText(String.format("%s @ %s", taskList.get(i).getDueDate(), taskList.get(i).getDueTime()));
            t2v.setGravity(Gravity.CENTER_HORIZONTAL);
            row.addView(t2v);

            TextView t3v = new TextView(this);
            t3v.setText(Integer.toString(taskList.get(i).getPriority()));
            t3v.setGravity(Gravity.CENTER_HORIZONTAL);
            row.addView(t3v);

            completedTable.addView(row);
        }

    }

    public void deleteT(View view) {
        if (MainActivity.selectedTask != null) {
            boolean success = taskDatabaseHelper.deleteTask(MainActivity.selectedTask);
            taskTable.removeView(selectedRow);
        }
        /*if (success) {
            Toast.makeText(this,  "Successfully deleted task.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,  "Error deleting task.", Toast.LENGTH_SHORT).show();
        }*/
        if(taskSelected) {
            taskTable.removeView(selectedRow);
        }
        if(completedTaskSelected) {
            completedTable.removeView(selectedRow);
        }
        hideButtons();
    }

    public void markComplete(View view) {
        boolean success;
        if(taskSelected) {
            success = taskDatabaseHelper.modifyComplete(MainActivity.selectedTask, true);
            MainActivity.selectedTask.setComplete(true);
            taskTable.removeView(selectedRow);
            completedTable.addView(selectedRow);
            btn_moveToTasks.setVisibility(View.VISIBLE);
            completedTaskSelected = true;
            taskSelected = false;
        }
        /*if (success) {
            Toast.makeText(this,  "Successfully marked complete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,  "Error marking complete.", Toast.LENGTH_SHORT).show();
        }*/
    }

    public void markIncomplete(View view) {
        boolean success;
        if(completedTaskSelected) {
            success = taskDatabaseHelper.modifyComplete(MainActivity.selectedTask, false);
            MainActivity.selectedTask.setComplete(false);
            completedTable.removeView(selectedRow);
            taskTable.addView(selectedRow);
            btn_moveToTasks.setVisibility(View.INVISIBLE);
            taskSelected = true;
            completedTaskSelected = false;
        }
        /*if (success) {
            Toast.makeText(this,  "Successfully marked incomplete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,  "Error marking incomplete.", Toast.LENGTH_SHORT).show();
        }*/
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

    public void openViewDialog(View view) {
        ViewTaskDialog viewTaskDialog = new ViewTaskDialog(MainActivity.selectedTask);
        viewTaskDialog.show(getSupportFragmentManager(), "view task dialog");
    }

    public void hideButtons() {
        btn_taskDelete.setVisibility(View.INVISIBLE);
        btn_taskReminder.setVisibility(View.INVISIBLE);
        btn_taskComplete.setVisibility(View.INVISIBLE);
        btn_taskEdit.setVisibility(View.INVISIBLE);
        btn_view.setVisibility(View.INVISIBLE);
    }

    public void showButtons() {
        btn_taskDelete.setVisibility(View.VISIBLE);
        btn_taskReminder.setVisibility(View.VISIBLE);
        btn_taskComplete.setVisibility(View.VISIBLE);
        btn_taskEdit.setVisibility(View.VISIBLE);
        btn_view.setVisibility(View.VISIBLE);
    }

    public void createReminder(View view) {
        NotesDatabaseHelper db = new NotesDatabaseHelper(this);
        Task task = MainActivity.selectedTask;
        if(task != null) {
            Reminder rem = new Reminder(task.getName(), task.getDueDate(), task.getDueTime(), task.getDescription(), task.getKey());
            db.addReminder(rem);
        }
        Toast.makeText(this,  "Created Reminder", Toast.LENGTH_SHORT).show();
    }
}