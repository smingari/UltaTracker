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

import java.util.List;

public class NotesActivity extends AppCompatActivity {
    TableRow selectedRow;
    public static Note selectedNote;
    Button btn_add, btn_delete, btn_edit, btn_view;
    NotesDatabaseHelper db;
    TableLayout notesTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        btn_add = findViewById(R.id.notes_add_button);
        btn_delete = findViewById(R.id.notes_delete_button);
        btn_edit = findViewById(R.id.notes_edit_button);
        btn_view = findViewById(R.id.notes_view_button);

        notesTable = findViewById(R.id.notes_table);

        btn_delete.setVisibility(View.INVISIBLE);
        btn_edit.setVisibility(View.INVISIBLE);
        btn_view.setVisibility(View.INVISIBLE);

        db = new NotesDatabaseHelper(this);
        init_notes_table();
    }

    public void init_notes_table() {
        List<Note> notesList = db.getAllNotes();
        int dbSize = notesList.size();

        // Set up table header
        TableRow notesTableHeader = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        notesTableHeader.setLayoutParams(lp);

        // First column header
        TextView tv0 = new TextView(this);
        tv0.setPaintFlags(tv0.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv0.setText(" Name ");
        tv0.setGravity(Gravity.CENTER_HORIZONTAL);
        notesTableHeader.addView(tv0);

        // Second column header
        TextView tv1 = new TextView(this);
        tv1.setPaintFlags(tv1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv1.setText(" Date ");
        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
        notesTableHeader.addView(tv1);

        // Third column header
        TextView tv2 = new TextView(this);
        tv2.setPaintFlags(tv2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv2.setText(" Description ");
        tv2.setGravity(Gravity.CENTER_HORIZONTAL);
        notesTableHeader.addView(tv2);

        // Add header row to table
        notesTable.addView(notesTableHeader);

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
                    selectedNote = notesList.get(row.getId());
                    btn_delete.setVisibility(View.VISIBLE);
                    btn_edit.setVisibility(View.VISIBLE);
                    btn_view.setVisibility(View.VISIBLE);
                }
            });

            TextView t1v = new TextView(this);
            t1v.setGravity(Gravity.CENTER_HORIZONTAL);
            String noteName = notesList.get(i).getName();
            if (noteName.length() > 12) { noteName = (noteName.substring(0, Math.min(noteName.length(), 12))) + ".."; }
            t1v.setText(noteName);
            row.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText(notesList.get(i).getDate());
            t2v.setGravity(Gravity.CENTER_HORIZONTAL);
            row.addView(t2v);

            TextView t3v = new TextView(this);
            t3v.setText(notesList.get(i).getDesc());
            t3v.setGravity(Gravity.CENTER_HORIZONTAL);
            row.addView(t3v);

            notesTable.addView(row);
        }
    }

    public void deleteNote(View view) {
        boolean success = db.deleteNote(selectedNote);
        notesTable.removeView(selectedRow);
        selectedRow = null;
        selectedNote = null;
        btn_delete.setVisibility(View.INVISIBLE);
        btn_edit.setVisibility(View.INVISIBLE);
        btn_view.setVisibility(View.INVISIBLE);
    }

    public void toMainActivity(View view) {
        Intent intent = new Intent(NotesActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void toAddNoteActivity(View view) {
        Intent intent = new Intent(NotesActivity.this, AddNoteActivity.class);
        startActivity(intent);
    }

    public void toEditNoteActivity(View view) {
        Intent intent = new Intent(NotesActivity.this, EditNoteActivity.class);
        startActivity(intent);
    }

    public void openViewDialog(View view) {
        ViewNoteDialog viewNoteDialog = new ViewNoteDialog(selectedNote);
        viewNoteDialog.show(getSupportFragmentManager(), "view note dialog");
    }
}