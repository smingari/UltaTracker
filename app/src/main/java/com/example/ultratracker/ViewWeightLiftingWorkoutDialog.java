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

public class ViewWeightLiftingWorkoutDialog extends AppCompatDialogFragment {
    TextView completed_time_display, duration_display, calories_display, distance_display, pace_display;

    WeightliftingWorkout ww;
    public ViewWeightLiftingWorkoutDialog(WeightliftingWorkout ww) {
        this.ww = ww;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.view_weightlifting_workout_popup, null);

        completed_time_display = view.findViewById(R.id.ww_completed_date_display);
        duration_display = view.findViewById(R.id.ww_duration_display);
        calories_display = view.findViewById(R.id.ww_calories_display);

        builder.setView(view);
        builder.setTitle(ww.getExerciseType());
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });
        return builder.create();
    }
}
