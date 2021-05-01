package com.example.ultratracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalTime;

public class EditNoteActivity extends AppCompatActivity implements DateSelectorDialog.DateSelectorListener {
    Button cancel_button, update_note_button, edit_date_button;
    EditText name_entry, description_entry;
    TextView date_display;

    private int noteSelectedYear;
    private int noteSelectedMonth;
    private int noteSelectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        cancel_button = findViewById(R.id.edit_note_cancel_button);
        update_note_button = findViewById(R.id.edit_note_button);
        edit_date_button = findViewById(R.id.edit_note_date2);
        name_entry = findViewById(R.id.edit_note_name_entry);
        date_display = findViewById(R.id.edit_note_date_display);
        description_entry = findViewById(R.id.edit_note_description_entry);

        name_entry.setText(NotesActivity.selectedNote.getName());
        date_display.setText(NotesActivity.selectedNote.getDate());
        description_entry.setText(NotesActivity.selectedNote.getDesc());

        NotesDatabaseHelper db = new NotesDatabaseHelper(EditNoteActivity.this);

        noteSelectedYear = MainActivity.selectedYear;
        noteSelectedMonth = MainActivity.selectedMonth;
        noteSelectedDay = MainActivity.selectedDay;

        update_note_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                    String name = name_entry.getText().toString();
                    String description = description_entry.getText().toString();

                    LocalDate syn_date;
                    if(date_display.getText().equals(NotesActivity.selectedNote.getDate())) {
                        syn_date = LocalDate.parse(date_display.getText());
                    } else {
                        syn_date = LocalDate.of(noteSelectedYear, noteSelectedMonth, noteSelectedDay);
                    }

                    NotesActivity.selectedNote.setName(name);
                    NotesActivity.selectedNote.setDate(syn_date);
                    NotesActivity.selectedNote.setDesc(description);
                    if(db.editNote(NotesActivity.selectedNote)) {
                        cancelPressed(v);
                        Toast.makeText(EditNoteActivity.this, "Successfully updated note.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditNoteActivity.this, "Error updating note.", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e) {
                    Toast.makeText(EditNoteActivity.this, "Error updating note.", Toast.LENGTH_SHORT).show();
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
        System.out.println("open date dialog");
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
        Intent intent = new Intent(EditNoteActivity.this, NotesActivity.class);
        startActivity(intent);
    }

    public void toMainActivity(View view) {
        Intent intent = new Intent(EditNoteActivity.this, MainActivity.class);
        startActivity(intent);
    }
}