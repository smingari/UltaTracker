package com.example.ultratracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class TimeSelectorDialog extends AppCompatDialogFragment {

    private TimeSelectorListener listener;
    private int hour;
    private int min;
    TimePicker time_picker;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.time_selector_popup, null);

        time_picker = view.findViewById(R.id.time_picker);

        builder.setView(view);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hour = time_picker.getHour();
                } else {
                    hour = time_picker.getCurrentHour();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    min = time_picker.getMinute();
                } else {
                    min = time_picker.getCurrentMinute();
                }
                listener.applyTime(hour, min);
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (TimeSelectorListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement TimeSelectorListener");
        }
    }
    public interface TimeSelectorListener {
        void applyTime(int hour, int min);
    }
}
