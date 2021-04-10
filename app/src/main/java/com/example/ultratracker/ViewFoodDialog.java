package com.example.ultratracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ViewFoodDialog extends AppCompatDialogFragment {
    TextView cals_display, protein_display, carbs_display, fat_display, fiber_display;

    Food food;
    public ViewFoodDialog(Food food) {
        this.food = food;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.view_food_popup, null);

        cals_display = view.findViewById(R.id.food_cals_display);
        protein_display = view.findViewById(R.id.food_protein_display);
        carbs_display = view.findViewById(R.id.food_carbs_display);
        fat_display = view.findViewById(R.id.food_fat_display);
        fiber_display = view.findViewById(R.id.food_fiber_display);

        cals_display.setText(String.valueOf(food.getCals()));
        protein_display.setText(String.valueOf(food.getProtein()));
        carbs_display.setText(String.valueOf(food.getCarbs()));
        fat_display.setText(String.valueOf(food.getFat()));
        fiber_display.setText(String.valueOf(food.getFiber()));

        builder.setView(view);
        builder.setTitle(food.getName());
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });
        return builder.create();
    }
}
