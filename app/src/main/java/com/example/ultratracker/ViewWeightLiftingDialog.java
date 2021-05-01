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

public class ViewWeightLiftingDialog extends AppCompatDialogFragment {
    TextView sets_display, reps_display, weight_display;

    Weightlifting w;
    public ViewWeightLiftingDialog(Weightlifting ww) {
        this.w = ww;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.view_weightlifting_popup, null);

        sets_display = view.findViewById(R.id.ww_sets_display);
        reps_display = view.findViewById(R.id.ww_duration_display);
        weight_display = view.findViewById(R.id.ww_weight_display);

        builder.setView(view);
        builder.setTitle(w.getName());
        sets_display.setText(String.valueOf(w.getSets()));
        reps_display.setText(String.valueOf(w.getReps()));
        weight_display.setText(String.valueOf(w.getWeight()));
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });
        return builder.create();
    }
}
