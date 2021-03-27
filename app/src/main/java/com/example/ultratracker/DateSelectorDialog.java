package com.example.ultratracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DateSelectorDialog extends AppCompatDialogFragment {
    private DateSelectorListener listener;
    private int year;
    private int month;
    private int day;
    DatePicker date_picker;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.date_selector_popup, null);

        date_picker = view.findViewById(R.id.date_picker);

        builder.setView(view);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                year = date_picker.getYear();
                month = date_picker.getMonth() + 1;
                day = date_picker.getDayOfMonth();
                listener.applyDate(year, month, day);
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DateSelectorListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement DateSelectorListener");
        }
    }
    public interface DateSelectorListener {
        void applyDate(int year, int month, int day);
    }
}
