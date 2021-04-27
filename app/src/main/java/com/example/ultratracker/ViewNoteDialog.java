package com.example.ultratracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ViewNoteDialog extends AppCompatDialogFragment {
    TextView date_display, description_display;

    Note note;
    public ViewNoteDialog(Note note) {
        this.note = note;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.view_note_popup, null);

        date_display = view.findViewById(R.id.view_date_display);
        description_display = view.findViewById(R.id.view_description_display);

        date_display.setText(note.getDate());
        description_display.setText(note.getDesc());

        builder.setView(view);
        builder.setTitle(note.getName());
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });
        return builder.create();
    }
}
