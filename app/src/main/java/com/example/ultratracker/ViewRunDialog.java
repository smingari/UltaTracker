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

public class ViewRunDialog extends AppCompatDialogFragment {
    TextView completed_time_display, duration_display, calories_display, distance_display, pace_display;

    Run run;
    public ViewRunDialog(Run run) {
        this.run = run;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.view_run_popup, null);

        completed_time_display = view.findViewById(R.id.run_completed_date_display);
        duration_display = view.findViewById(R.id.run_duration_display);
        calories_display = view.findViewById(R.id.run_calories_display);
        distance_display = view.findViewById(R.id.run_distance_display);
        pace_display = view.findViewById(R.id.run_pace_display);

        completed_time_display.setText(run.getCompletedTime());
        duration_display.setText(String.valueOf(run.getDuration()));
        calories_display.setText(String.valueOf(run.getCaloriesBurned()));
        distance_display.setText(String.valueOf(run.getDistance()));
        pace_display.setText(String.valueOf(run.getPace()));

        builder.setView(view);
        builder.setTitle(run.getExerciseType());
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });
        return builder.create();
    }
}
