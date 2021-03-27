package com.example.ultratracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ViewTaskDialog extends AppCompatDialogFragment {
    TextView date_display, due_time_display, description_display, priority_display;

    Task task;
    public ViewTaskDialog(Task task) {
        this.task = task;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.view_task_popup, null);

        date_display = view.findViewById(R.id.date_display);
        due_time_display = view.findViewById(R.id.due_time_display);
        description_display = view.findViewById(R.id.description_display);
        priority_display = view.findViewById(R.id.priority_display);

        date_display.setText(task.getDueDate());
        due_time_display.setText(task.getDueTime());
        description_display.setText(task.getDescription());
        priority_display.setText(String.valueOf(task.getPriority()));

        builder.setView(view);
        builder.setTitle(task.getName());
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });
        return builder.create();
    }
}
