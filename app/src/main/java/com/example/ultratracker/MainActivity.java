package com.example.ultratracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Boolean plannerMode;
    Boolean healthMode;
    Boolean exerciseMode;
    Button addPlanner;
    Button addHealth;
    Button addExercise;
    CalendarView cal;
    public static Meal newMeal;
    public static int selectedYear;
    public static int selectedMonth;
    public static int selectedDay;

    public static Task selectedTask;
    TableRow selectedRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addPlanner = findViewById(R.id.addPlanner);
        addHealth = findViewById(R.id.addHealth);
        addExercise = findViewById(R.id.addExercise);

        cal = (CalendarView) findViewById(R.id.calendarView4);

        plannerMode = true; // Main screen defaults to planner mode upon startup
        healthMode = false;
        exerciseMode = false;
        addPlanner.setBackgroundColor(getResources().getColor(R.color.teal_700));
        addHealth.setBackgroundColor(getResources().getColor(R.color.teal_200));
        addExercise.setBackgroundColor(getResources().getColor(R.color.teal_200));

        Calendar date = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String curDate = sdf.format(date.getTime());
        String[] cDate = curDate.split("-");
        selectedYear = Integer.parseInt(cDate[0]);
        selectedMonth = Integer.parseInt(cDate[1]);
        selectedDay = Integer.parseInt(cDate[2]);

        String sMonth;
        String sDay;
        if (selectedMonth < 10) {
            sMonth = "0" + selectedMonth;
        } else { sMonth = String.valueOf(selectedMonth); }
        if (selectedDay < 10) {
            sDay = "0" + selectedDay;
        } else { sDay = String.valueOf(selectedDay); }

        List<Food> foodList = new ArrayList<>();
        newMeal = new Meal("newMeal", 0, 0, 0, 0, 0, LocalDate.parse(selectedYear + "-" + sMonth + "-" + sDay), foodList);

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedYear = year;
                selectedMonth = month+1;
                selectedDay = dayOfMonth;
                if (plannerMode) {
                    toPDay(view);
                }
                else if (healthMode) {
                    toHDay(view);
                }
                else if (exerciseMode) {
                }
            }
        });

        addPlanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plannerMode = true; healthMode = false; exerciseMode = false;
                addPlanner.setBackgroundColor(getResources().getColor(R.color.teal_700));
                addHealth.setBackgroundColor(getResources().getColor(R.color.teal_200));
                addExercise.setBackgroundColor(getResources().getColor(R.color.teal_200));
                //Toast.makeText(MainActivity.this, "Select A Date", Toast.LENGTH_SHORT).show();
            }
        });
        addHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plannerMode = false; healthMode = true; exerciseMode = false;
                addPlanner.setBackgroundColor(getResources().getColor(R.color.teal_200));
                addHealth.setBackgroundColor(getResources().getColor(R.color.teal_700));
                addExercise.setBackgroundColor(getResources().getColor(R.color.teal_200));
                //Toast.makeText(MainActivity.this, "Select A Date", Toast.LENGTH_SHORT).show();
            }
        });
        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plannerMode = false; healthMode = false; exerciseMode = true;
                addPlanner.setBackgroundColor(getResources().getColor(R.color.teal_200));
                addHealth.setBackgroundColor(getResources().getColor(R.color.teal_200));
                addExercise.setBackgroundColor(getResources().getColor(R.color.teal_700));
                //Toast.makeText(MainActivity.this, "Select A Date", Toast.LENGTH_SHORT).show();
            }
        });

        init_recent_table();
    }

    // TODO: Will need to make this pull recent entries from all event types once other modes are implemented
    public void init_recent_table() {
        TableLayout taskTable = findViewById(R.id.recent_table);
        TaskDatabaseHelper taskDatabaseHelper = new TaskDatabaseHelper(this);

        // Format selected date for task query
        String sMonth;
        String sDay;
        if (selectedMonth < 10) {
            sMonth = "0" + selectedMonth;
        } else {
            sMonth = String.valueOf(selectedMonth);
        }
        if (selectedDay < 10) {
            sDay = "0" + selectedDay;
        } else {
            sDay = String.valueOf(selectedDay);
        }

        List<Task> taskList = taskDatabaseHelper.getAll();
        int dbSize = taskDatabaseHelper.getAll().size();

        // Set up table header
        TableRow taskTableHeader = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        taskTableHeader.setLayoutParams(lp);

        // First column header
        TextView tv0 = new TextView(this);
        tv0.setPaintFlags(tv0.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv0.setText(" Entry Type ");
        tv0.setGravity(Gravity.CENTER_HORIZONTAL);
        taskTableHeader.addView(tv0);

        // Second column header
        TextView tv1 = new TextView(this);
        tv1.setPaintFlags(tv1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv1.setText(" Name ");
        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
        taskTableHeader.addView(tv1);

        // Third column header
        TextView tv2 = new TextView(this);
        tv2.setPaintFlags(tv2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv2.setText(" Due Date ");
        tv2.setGravity(Gravity.CENTER_HORIZONTAL);
        taskTableHeader.addView(tv2);

        // Add header row to table
        taskTable.addView(taskTableHeader);

        // Add rows dynamically from database
        for (int i = 0; i < dbSize; i++) {
            TableRow row = new TableRow(this);
            row.setId(i);

            /*row.setOnClickListener(new View.OnClickListener() {
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
                }
            });*/

            TextView t0v = new TextView(this);
            t0v.setText("Task");
            t0v.setGravity(Gravity.CENTER_HORIZONTAL);
            row.addView(t0v);

            TextView t1v = new TextView(this);
            String taskName = taskList.get(i).getName();
            if (taskName.length() > 12) {
                taskName = (taskName.substring(0, Math.min(taskName.length(), 12))) + "..";
            }
            t1v.setText(taskName);
            t1v.setGravity(Gravity.CENTER_HORIZONTAL);
            row.addView(t1v);

            TextView t3v = new TextView(this);
            t3v.setText(taskList.get(i).getAssignedDate());
            t3v.setGravity(Gravity.CENTER_HORIZONTAL);
            row.addView(t3v);

            taskTable.addView(row);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void toPDay(View view) {
        Intent intent = new Intent(MainActivity.this, PDayActivity.class);
        startActivity(intent);
    }

    public void toHDay(View view) {
        Intent intent = new Intent(MainActivity.this, HDayActivity.class);
        startActivity(intent);
    }

    public void toTodo(View view) {
        Intent intent = new Intent(MainActivity.this, TodoActivity.class);
        startActivity(intent);
    }
}