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

public class ViewRideDialog extends AppCompatDialogFragment {
    TextView completed_time_display, duration_display, calories_display, distance_display, pace_display;

    Ride ride;
    public ViewRideDialog(Ride ride) {
        this.ride = ride;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.view_ride_popup, null);

        completed_time_display = view.findViewById(R.id.ride_completed_date_display);
        duration_display = view.findViewById(R.id.ride_duration_display);
        calories_display = view.findViewById(R.id.ride_calories_display);
        distance_display = view.findViewById(R.id.ride_distance_display);
        pace_display = view.findViewById(R.id.ride_pace_display);

        completed_time_display.setText(ride.getCompletedTime());
        duration_display.setText(String.valueOf(ride.getDuration()));
        calories_display.setText(String.valueOf(ride.getCaloriesBurned()));
        distance_display.setText(String.valueOf(ride.getDistance()));
        pace_display.setText(String.valueOf(ride.getPace()));

        builder.setView(view);
        builder.setTitle(ride.getExerciseType());
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });
        return builder.create();
    }
}
