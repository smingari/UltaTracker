package com.example.ultratracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.HashMap;

public class TrackWeightActivity extends AppCompatActivity {

    public TextView weight;
    public EditText newWeight;
    public Button homeButton, addButton;

    HashMap<LocalDate, Double> weights = new HashMap<LocalDate, Double>();

    private int taskSelectedYear;
    private int taskSelectedMonth;
    private int taskSelectedDay;
    private LocalDate syn_date;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_weight);

        weight = findViewById(R.id.textView3);
        newWeight = findViewById(R.id.add_weight);
        homeButton = findViewById(R.id.button2);
        addButton = findViewById(R.id.button);

        taskSelectedYear = MainActivity.selectedYear;
        taskSelectedMonth = MainActivity.selectedMonth;
        taskSelectedDay = MainActivity.selectedDay;
        syn_date = LocalDate.of(taskSelectedYear,taskSelectedMonth,taskSelectedDay);

    }

    public void setNewWeight(View view){
        try{
            double bodyweight = Math.round(Double.parseDouble(String.valueOf(newWeight.getText())) * 10)/10.0;
            weight.setText(new StringBuilder().append(String.valueOf(bodyweight)).append("lb").toString());
            Toast.makeText(this, "Successfully set new weight", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(this, "Failed to set new weight", Toast.LENGTH_SHORT).show();
        }
    }

    public void toMainActivity(View view){
        Intent intent = new Intent(TrackWeightActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
