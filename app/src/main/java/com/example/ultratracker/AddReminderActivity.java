package com.example.ultratracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;

public class AddReminderActivity extends AppCompatActivity implements DateSelectorDialog.DateSelectorListener {
    Button cancel_button, create_note_button, edit_date_button;
    EditText name_entry, description_entry;
    TextView date_display;

    private int noteSelectedYear;
    private int noteSelectedMonth;
    private int noteSelectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        cancel_button = findViewById(R.id.note_cancel_button);
        create_note_button = findViewById(R.id.create_note_button);
        edit_date_button = findViewById(R.id.edit_note_date);
        name_entry = findViewById(R.id.note_name_entry);
        date_display = findViewById(R.id.note_date_display);
        description_entry = findViewById(R.id.note_description_entry);

        //NotesDatabaseHelper db = new NotesDatabaseHelper(AddReminderActivity.this);

        noteSelectedYear = MainActivity.selectedYear;
        noteSelectedMonth = MainActivity.selectedMonth;
        noteSelectedDay = MainActivity.selectedDay;
        date_display.setText(noteSelectedMonth + "/" + noteSelectedDay + "/" + noteSelectedYear);

        create_note_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                    String name = name_entry.getText().toString();
                    if (name.equals("")) {
                        Toast.makeText(AddReminderActivity.this, "Please provide a name for the note.", Toast.LENGTH_SHORT).show();
                    } else {
                        String description = description_entry.getText().toString();

                        LocalDate syn_date = LocalDate.of(noteSelectedYear, noteSelectedMonth, noteSelectedDay);

                        Note note = new Note(name, syn_date, description);
                        //db.addNote(note);
                        toMainActivity(v);
                        Toast.makeText(AddReminderActivity.this, "Successfully made note.", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e) {
                    Toast.makeText(AddReminderActivity.this, "Error creating note.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        edit_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDateDialog();
            }
        });
    }

    public void openDateDialog() {
        DateSelectorDialog dateDialog = new DateSelectorDialog();
        dateDialog.show(getSupportFragmentManager(), "date dialog");
    }

    @Override
    public void applyDate(int year, int month, int day) {
        noteSelectedYear = year;
        noteSelectedMonth = month;
        noteSelectedDay = day;
        date_display.setText(noteSelectedMonth + "/" + noteSelectedDay + "/" + noteSelectedYear);
    }

    public void cancelPressed(View view) {
        Intent intent = new Intent(AddReminderActivity.this, NotesActivity.class);
        startActivity(intent);
    }

    public void toPDay(View view) {
        Intent intent = new Intent(AddReminderActivity.this, PDayActivity.class);
        startActivity(intent);
    }

    public void toMainActivity(View view) {
        Intent intent = new Intent(AddReminderActivity.this, MainActivity.class);
        startActivity(intent);
    }
}