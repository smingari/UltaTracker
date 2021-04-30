package com.example.ultratracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

public class RemindersActivity extends AppCompatActivity {

    public static Reminder selectedReminder;
    TableRow selectedRow;
    Button btn_add, btn_delete, btn_edit, btn_view;
    NotesDatabaseHelper db;
    TableLayout remindersTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        btn_add = findViewById(R.id.reminders_add_button);
        btn_delete = findViewById(R.id.reminders_delete_button);
        btn_edit = findViewById(R.id.reminders_edit_button);
        btn_view = findViewById(R.id.reminders_view_button);

        remindersTable = findViewById(R.id.reminders_table);

        btn_delete.setVisibility(View.INVISIBLE);
        btn_edit.setVisibility(View.INVISIBLE);
        btn_view.setVisibility(View.INVISIBLE);

        db = new NotesDatabaseHelper(this);
        init_reminders_table();
    }

    public void init_reminders_table() {
        List<Reminder> remindersList = db.getAllReminders();
        int dbSize = remindersList.size();

        // Set up table header
        TableRow remindersTableHeader = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        remindersTableHeader.setLayoutParams(lp);

        // First column header
        TextView tv0 = new TextView(this);
        tv0.setPaintFlags(tv0.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv0.setText(" Name ");
        tv0.setGravity(Gravity.CENTER_HORIZONTAL);
        remindersTableHeader.addView(tv0);

        // Second column header
        TextView tv1 = new TextView(this);
        tv1.setPaintFlags(tv1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv1.setText(" Date ");
        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
        remindersTableHeader.addView(tv1);

        // Third column header
        TextView tv2 = new TextView(this);
        tv2.setPaintFlags(tv2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv2.setText(" Description ");
        tv2.setGravity(Gravity.CENTER_HORIZONTAL);
        remindersTableHeader.addView(tv2);

        // Add header row to table
        remindersTable.addView(remindersTableHeader);

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
                    selectedReminder = remindersList.get(row.getId());
                    btn_delete.setVisibility(View.VISIBLE);
                    btn_edit.setVisibility(View.VISIBLE);
                    btn_view.setVisibility(View.VISIBLE);
                }
            });

            TextView t1v = new TextView(this);
            t1v.setGravity(Gravity.CENTER_HORIZONTAL);
            String remName = remindersList.get(i).getName();
            if (remName.length() > 12) { remName = (remName.substring(0, Math.min(remName.length(), 12))) + ".."; }
            t1v.setText(remName);
            row.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText(String.format("%s @ %s", remindersList.get(i).getDate(), remindersList.get(i).getTime()));
            t2v.setGravity(Gravity.CENTER_HORIZONTAL);
            row.addView(t2v);

            TextView t3v = new TextView(this);
            t3v.setText(remindersList.get(i).getDesc());
            t3v.setGravity(Gravity.CENTER_HORIZONTAL);
            row.addView(t3v);

            remindersTable.addView(row);
        }
    }

    public void deleteNote(View view) {
        boolean success = db.deleteReminder(selectedReminder);
        remindersTable.removeView(selectedRow);
        selectedRow = null;
        selectedReminder = null;
        btn_delete.setVisibility(View.INVISIBLE);
        btn_edit.setVisibility(View.INVISIBLE);
        btn_view.setVisibility(View.INVISIBLE);
    }

    public void toMainActivity(View view) {
        Intent intent = new Intent(RemindersActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void toAddReminderActivity(View view) {
        Intent intent = new Intent(RemindersActivity.this, AddReminderActivity.class);
        startActivity(intent);
    }

    public void toEditReminderActivity(View view) {
        Intent intent = new Intent(RemindersActivity.this, EditReminderActivity.class);
        startActivity(intent);
    }

    public void openViewDialog(View view) {
        ViewReminderDialog viewReminderDialog = new ViewReminderDialog(selectedReminder);
        viewReminderDialog.show(getSupportFragmentManager(), "view reminder dialog");
    }
}