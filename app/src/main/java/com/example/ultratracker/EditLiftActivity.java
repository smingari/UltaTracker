package com.example.ultratracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class EditLiftActivity extends AppCompatActivity {
    Button cancel_button, edit_lift_button;

    EditText liftname_entry, sets_entry, reps_entry, weight_entry;

    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lift);

        ExerciseDatabaseHelper db = new ExerciseDatabaseHelper(EditLiftActivity.this);

        liftname_entry = findViewById(R.id.liftname_entry);
        sets_entry = findViewById(R.id.sets_entry);
        reps_entry = findViewById(R.id.reps_entry);
        weight_entry = findViewById(R.id.weight_entry);

        cancel_button = findViewById(R.id.cancel_button);
        edit_lift_button = findViewById(R.id.edit_edit_lift_button);

        liftname_entry.setText(AddWeightliftingActivity.selectedWl.getName());
        sets_entry.setText(String.valueOf(AddWeightliftingActivity.selectedWl.getSets()));
        reps_entry.setText(String.valueOf(AddWeightliftingActivity.selectedWl.getReps()));
        weight_entry.setText(String.valueOf(AddWeightliftingActivity.selectedWl.getWeight()));

        edit_lift_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try{
                    String liftName = liftname_entry.getText().toString();

                    int sets = Integer.parseInt(sets_entry.getText().toString());
                    int reps = Integer.parseInt(reps_entry.getText().toString());
                    int weight = Integer.parseInt(weight_entry.getText().toString());

                    AddWeightliftingActivity.selectedWl.setName(liftName);
                    AddWeightliftingActivity.selectedWl.setSets(sets);
                    AddWeightliftingActivity.selectedWl.setReps(reps);
                    AddWeightliftingActivity.selectedWl.setWeight(weight);

                    if(db.editLift(AddWeightliftingActivity.selectedWl)){
                        toAddWeightliftingActivity(v);
                        Toast.makeText(EditLiftActivity.this, "Successfully edited lift.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(EditLiftActivity.this, "Error editing lift.", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(EditLiftActivity.this, "Error editing lift.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void toAddWeightliftingActivity(View view){
        Intent intent = new Intent(EditLiftActivity.this, AddWeightliftingActivity.class);
        startActivity(intent);
    }
}
