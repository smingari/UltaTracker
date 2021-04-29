package com.example.ultratracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    public static Workout newWo;
    public static int selectedYear;
    public static int selectedMonth;
    public static int selectedDay;
    String currentDate;

    public static Task selectedTask;
    TableRow selectedRow;

    public static boolean inTodo;

    //1. Notification Channel
    //2. Notification Builder
    //3. Notification Manager

    private static final String CHANNEL_ID = "ultatracker";
    private static final String CHANNEL_NAME = "UltaTracker";
    private static final String CHANNEL_DESC = "UltaTracker Notifications";

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

        inTodo = false;

        String sMonth;
        String sDay;
        if (selectedMonth < 10) {
            sMonth = "0" + selectedMonth;
        } else { sMonth = String.valueOf(selectedMonth); }
        if (selectedDay < 10) {
            sDay = "0" + selectedDay;
        } else { sDay = String.valueOf(selectedDay); }
        currentDate = selectedYear + "-" + sMonth + "-" + sDay;

        // Dark Mode Settings
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if(sp.getBoolean("DarkMode", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        List<Food> foodList = new ArrayList<>();
        newMeal = new Meal("newMeal", 0, 0, 0, 0, 0, LocalDate.parse(selectedYear + "-" + sMonth + "-" + sDay), foodList);

        List<Weightlifting> liftList = new ArrayList<>();
        newWo = new Workout("newWo", liftList, LocalDate.parse(selectedYear + "-" + sMonth + "-" + sDay));

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
                    toEDay(view);
                }
            }
        });

        addPlanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize planner entries in recent table
                if (!plannerMode) {
                    plannerMode = true; healthMode = false; exerciseMode = false;
                    init_recent_table();
                }

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
                // Initialize health entries in recent table
                if (!healthMode) {
                    plannerMode = false; healthMode = true; exerciseMode = false;
                    init_recent_table();
                }

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
                // Initialize exercise entries in recent table
                if (!exerciseMode) {
                    plannerMode = false; healthMode = false; exerciseMode = true;
                    init_recent_table();
                }

                plannerMode = false; healthMode = false; exerciseMode = true;
                addPlanner.setBackgroundColor(getResources().getColor(R.color.teal_200));
                addHealth.setBackgroundColor(getResources().getColor(R.color.teal_200));
                addExercise.setBackgroundColor(getResources().getColor(R.color.teal_700));
                //Toast.makeText(MainActivity.this, "Select A Date", Toast.LENGTH_SHORT).show();
            }
        });

        init_recent_table();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        displayNotification();
    }

    // TODO: Will need to make this pull recent entries from all event types once other modes are implemented
    public void init_recent_table() {
        TableLayout taskTable = findViewById(R.id.recent_table);
        TaskDatabaseHelper taskDatabaseHelper = new TaskDatabaseHelper(this);

        // Clear all rows in table
        taskTable.removeAllViews();

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

        int dbSize = 0;

        if (plannerMode) {
            List<Task> taskList = taskDatabaseHelper.getByDateAndCompletion(selectedYear + "-" + sMonth + "-" + sDay, 0);
            dbSize = taskList.size();

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
        } else if (healthMode) {
            MealDatabaseHelper mdb = new MealDatabaseHelper(this);
            List<Meal> foodList = mdb.getMealsByDate(selectedYear + "-" + sMonth + "-" + sDay);
            if (foodList != null) { dbSize = foodList.size(); }
            else { dbSize = 0; }

            // Set up table header
            TableRow mealTableHeader = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            mealTableHeader.setLayoutParams(lp);

            // First column header
            TextView tv0 = new TextView(this);
            tv0.setPaintFlags(tv0.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            tv0.setText(" Meal ");
            tv0.setGravity(Gravity.CENTER_HORIZONTAL);
            mealTableHeader.addView(tv0);

            // Second column header
            TextView tv1 = new TextView(this);
            tv1.setPaintFlags(tv1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            tv1.setText(" Date ");
            tv1.setGravity(Gravity.CENTER_HORIZONTAL);
            mealTableHeader.addView(tv1);

            // Third column header
            TextView tv2 = new TextView(this);
            tv2.setPaintFlags(tv2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            tv2.setText(" Calories ");
            tv2.setGravity(Gravity.CENTER_HORIZONTAL);
            mealTableHeader.addView(tv2);

            // Add header row to table
            taskTable.addView(mealTableHeader);

            // Add rows dynamically from database
            if (dbSize != 0) {
                for (int i = 0; i < dbSize; i++) {
                    TableRow row = new TableRow(this);
                    row.setId(i);

                    TextView t1v = new TextView(this);
                    String mealName = foodList.get(i).getName();
                    if (mealName.length() > 12) {
                        mealName = (mealName.substring(0, Math.min(mealName.length(), 12))) + "..";
                    }
                    t1v.setText(mealName);
                    t1v.setGravity(Gravity.CENTER_HORIZONTAL);
                    row.addView(t1v);

                    TextView t2v = new TextView(this);
                    t2v.setText(String.valueOf(foodList.get(i).getDate()));
                    t2v.setGravity(Gravity.CENTER_HORIZONTAL);
                    row.addView(t2v);

                    TextView t3v = new TextView(this);
                    t3v.setText(String.valueOf(foodList.get(i).getCals()));
                    t3v.setGravity(Gravity.CENTER_HORIZONTAL);
                    row.addView(t3v);
                    taskTable.addView(row);
                }
            }
        } else if (exerciseMode) {
            ExerciseDatabaseHelper e_db = new ExerciseDatabaseHelper(this);
            List<Exercise> exerciseList = e_db.getExercisesByDate(MainActivity.selectedYear + "-" + sMonth + "-" + sDay);
            if (exerciseList != null) { dbSize = exerciseList.size(); }
            else { dbSize = 0; }

            // Set up table header
            TableRow exerciseTableHeader = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            exerciseTableHeader.setLayoutParams(lp);

            // First column header
            TextView tv0 = new TextView(this);
            tv0.setPaintFlags(tv0.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            tv0.setText(" Exercise ");
            tv0.setGravity(Gravity.CENTER_HORIZONTAL);
            exerciseTableHeader.addView(tv0);

            // Second column header
            TextView tv1 = new TextView(this);
            tv1.setPaintFlags(tv1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            tv1.setText(" Date ");
            tv1.setGravity(Gravity.CENTER_HORIZONTAL);
            exerciseTableHeader.addView(tv1);

            // Third column header
            TextView tv2 = new TextView(this);
            tv2.setPaintFlags(tv2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            tv2.setText(" Calories Burned ");
            tv2.setGravity(Gravity.CENTER_HORIZONTAL);
            exerciseTableHeader.addView(tv2);

            // Add header row to table
            taskTable.addView(exerciseTableHeader);

            // Add rows dynamically from database
            if (dbSize != 0) {
                for (int i = 0; i < dbSize; i++) {
                    TableRow row = new TableRow(this);
                    row.setId(i);

                    TextView t1v = new TextView(this);
                    String exerciseType = exerciseList.get(i).getExerciseType();
                    if (exerciseType.length() > 12) {
                        exerciseType = (exerciseType.substring(0, Math.min(exerciseType.length(), 12))) + "..";
                    }
                    t1v.setText(exerciseType);
                    t1v.setGravity(Gravity.CENTER_HORIZONTAL);
                    row.addView(t1v);

                    TextView t2v = new TextView(this);
                    t2v.setText(exerciseList.get(i).getCompletedDate());
                    t2v.setGravity(Gravity.CENTER_HORIZONTAL);
                    row.addView(t2v);

                    TextView t3v = new TextView(this);
                    t3v.setText(String.valueOf(exerciseList.get(i).getCaloriesBurned()));
                    t3v.setGravity(Gravity.CENTER_HORIZONTAL);
                    row.addView(t3v);
                    taskTable.addView(row);
                }
            }
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
            //Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
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

    public void toEDay(View view) {
        Intent intent = new Intent(MainActivity.this, EDayActivity.class);
        startActivity(intent);
    }

    public void toTodo(View view) {
        Intent intent = new Intent(MainActivity.this, TodoActivity.class);
        startActivity(intent);
    }

    public void toNotes(View view) {
        Intent intent = new Intent(MainActivity.this, NotesActivity.class);
        startActivity(intent);
    }

    public void toReminders(View view) {
        Intent intent = new Intent(MainActivity.this, RemindersActivity.class);
        startActivity(intent);
    }

    private void displayNotification() {
        NotesDatabaseHelper myDB = new NotesDatabaseHelper(this);

        // NEED HELP WITH THIS METHOD
        List<Reminder> remindersList = myDB.getAllRemindersByDate(currentDate);
        int notificationID = 0;

        NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from(this);
        int dbSize = remindersList.size();
        for (int i = 0; i < dbSize; i++) {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_android_black_24dp)
                    .setContentTitle(remindersList.get(i).getName())
                    .setContentText(remindersList.get(i).getDesc())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);


            mNotificationMgr.notify(++notificationID, mBuilder.build());
        }

        /*NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentTitle("Hurray! It is working.")
                .setContentText("Your first notification..")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from(this);
        mNotificationMgr.notify(1, mBuilder.build());*/
    }

}