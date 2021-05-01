package com.example.ultratracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddWeightliftingActivity extends AppCompatActivity {
    Button addButton, editButton, deleteButton, viewButton, addToButton, createButton;
    TextView woName;
    EditText woEntry;

    TableLayout woTable;
    TableLayout liftTable;
    TableRow selectedRow;
    public static Weightlifting selectedWl;
    List<Weightlifting> woList;
    ExerciseDatabaseHelper db;

    String curDate;
    Workout originalWo;

    boolean mealSelected;
    boolean bankSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_weightlifting);

        // Format selected date
        String sMonth;
        String sDay;
        if (MainActivity.selectedMonth < 10) {
            sMonth = "0" + MainActivity.selectedMonth;
        } else { sMonth = String.valueOf(MainActivity.selectedMonth); }
        if (MainActivity.selectedDay < 10) {
            sDay = "0" + MainActivity.selectedDay;
        } else { sDay = String.valueOf(MainActivity.selectedDay); }
        curDate = MainActivity.selectedYear + "-" + sMonth + "-" + sDay;

        addButton = findViewById(R.id.add_lift_button);
        editButton = findViewById(R.id.edit_lift_button);
        deleteButton = findViewById(R.id.delete_lift_button);
        viewButton = findViewById(R.id.add_wl_view_button);
        addToButton = findViewById(R.id.add_to_wl_button);
        createButton = findViewById(R.id.create_wl_button);
        woName = findViewById(R.id.wl_name_text);
        woEntry = findViewById(R.id.wl_name_entry);

        hideButtons();
        addToButton.setVisibility(View.INVISIBLE);
        editButton.setVisibility(View.INVISIBLE);
        if (EDayActivity.inEdit) { originalWo = MainActivity.newWo; }
        displayCreate();

        woList = new ArrayList<>();

        db = new ExerciseDatabaseHelper(this);

        init_wo_table();
        init_lift_table();
    }

    public void init_wo_table() {
        woTable = findViewById(R.id.wl_table);

        Workout wo = MainActivity.newWo;
        int dbSize;
        if (wo != null && wo.getLiftList().size() != 0) {
            dbSize = wo.getLiftList().size();
        } else { dbSize = 0; }
        //Toast.makeText(AddMealActivity.this, String.valueOf(dbSize), Toast.LENGTH_SHORT).show();

        // Set up table header
        TableRow createTableHeader = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        createTableHeader.setLayoutParams(lp);

        // First column header
        TextView tv0 = new TextView(this);
        tv0.setPaintFlags(tv0.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv0.setText(" Name ");
        tv0.setGravity(Gravity.CENTER_HORIZONTAL);
        createTableHeader.addView(tv0);

        // Second column header
        TextView tv1 = new TextView(this);
        tv1.setPaintFlags(tv1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv1.setText(" Sets ");
        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
        createTableHeader.addView(tv1);

        // Third column header
        TextView tv2 = new TextView(this);
        tv2.setPaintFlags(tv2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv2.setText(" Reps ");
        tv2.setGravity(Gravity.CENTER_HORIZONTAL);
        createTableHeader.addView(tv2);

        // Fourth column header
        TextView tv3 = new TextView(this);
        tv3.setPaintFlags(tv3.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv3.setText(" Weight ");
        tv3.setGravity(Gravity.CENTER_HORIZONTAL);
        createTableHeader.addView(tv3);

        // Add header row to table
        woTable.addView(createTableHeader);

        // Add rows dynamically from database
        if (dbSize != 0) {
            for (int i = 0; i < dbSize; i++) {
                TableRow row = new TableRow(this);
                row.setId(wo.getLiftList().get(i).getKey());
                row.setBackgroundResource(R.drawable.list_selector_background);
                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (selectedRow != null) {
                            selectedRow.setSelected(false);
                        }
                        selectedRow = row;
                        row.setSelected(true);
                        bankSelected = false;
                        mealSelected = true;
                        showButtons();
                        addToButton.setVisibility(View.INVISIBLE);
                        editButton.setVisibility(View.INVISIBLE);
                        //selectedWl = wo.getLiftList().get(row.getId());
                        for(Weightlifting wl : wo.getLiftList()) {
                            if(wl.getKey() == row.getId()) selectedWl = wl;
                        }
                    }
                });

                TextView t1v = new TextView(this);
                String liftName = wo.getLiftList().get(i).getName();
                if (liftName.length() > 12) { liftName = (liftName.substring(0, Math.min(liftName.length(), 12))) + ".."; }
                t1v.setText(liftName);
                t1v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t1v);

                TextView t2v = new TextView(this);
                t2v.setText(String.valueOf(wo.getLiftList().get(i).getSets()));
                t2v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t2v);

                TextView t3v = new TextView(this);
                t3v.setText(String.valueOf(wo.getLiftList().get(i).getReps()));
                t3v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t3v);

                TextView t4v = new TextView(this);
                t4v.setText(String.valueOf(wo.getLiftList().get(i).getWeight()));
                t4v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t4v);
                woTable.addView(row);
                //Toast.makeText(AddMealActivity.this, "Initialized create table", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void init_lift_table() {
        liftTable = findViewById(R.id.lift_table);

        List<Weightlifting> lifts = db.getLiftBank();
        int dbSize;
        if (lifts != null && lifts.size() != 0) { dbSize = lifts.size(); }
        else { dbSize = 0; }
        //Toast.makeText(AddMealActivity.this, String.valueOf(dbSize), Toast.LENGTH_SHORT).show();

        // Set up table header
        TableRow createTableHeader = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        createTableHeader.setLayoutParams(lp);

        // First column header
        TextView tv0 = new TextView(this);
        tv0.setPaintFlags(tv0.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv0.setText(" Name ");
        tv0.setGravity(Gravity.CENTER_HORIZONTAL);
        createTableHeader.addView(tv0);

        // Second column header
        TextView tv1 = new TextView(this);
        tv1.setPaintFlags(tv1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv1.setText(" Sets ");
        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
        createTableHeader.addView(tv1);

        // Third column header
        TextView tv2 = new TextView(this);
        tv2.setPaintFlags(tv2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv2.setText(" Reps ");
        tv2.setGravity(Gravity.CENTER_HORIZONTAL);
        createTableHeader.addView(tv2);

        // Fourth column header
        TextView tv3 = new TextView(this);
        tv3.setPaintFlags(tv3.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv3.setText(" Weight ");
        tv3.setGravity(Gravity.CENTER_HORIZONTAL);
        createTableHeader.addView(tv3);

        // Add header row to table
        liftTable.addView(createTableHeader);

        // Add rows dynamically from database
        if (dbSize != 0) {
            for (int i = 0; i < dbSize; i++) {
                TableRow row = new TableRow(this);
                row.setId(lifts.get(i).getKey());
                row.setBackgroundResource(R.drawable.list_selector_background);
                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (selectedRow != null) {
                            selectedRow.setSelected(false);
                        }
                        selectedRow = row;
                        row.setSelected(true);
                        showButtons();
                        bankSelected = true;
                        mealSelected = false;
                        addToButton.setVisibility(View.VISIBLE);
                        editButton.setVisibility(View.VISIBLE);
                        for(Weightlifting wl : lifts) {
                            if(wl.getKey() == row.getId()) selectedWl = wl;
                        }
                    }
                });

                TextView t1v = new TextView(this);
                String liftName = lifts.get(i).getName();
                if (liftName.length() > 12) { liftName = (liftName.substring(0, Math.min(liftName.length(), 12))) + ".."; }
                t1v.setText(liftName);
                t1v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t1v);

                TextView t2v = new TextView(this);
                t2v.setText(String.valueOf(lifts.get(i).getSets()));
                t2v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t2v);

                TextView t3v = new TextView(this);
                t3v.setText(String.valueOf(lifts.get(i).getReps()));
                t3v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t3v);

                TextView t4v = new TextView(this);
                t4v.setText(String.valueOf(lifts.get(i).getWeight()));
                t4v.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(t4v);
                liftTable.addView(row);
                //Toast.makeText(AddMealActivity.this, "Initialized create table", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addToWorkout(View view) {
        if (bankSelected) {
            TableRow newRow = new TableRow(this);
            newRow.setId(selectedWl.getKey());
            MainActivity.newWo.getLiftList().add(selectedWl);
            newRow.setBackgroundResource(R.drawable.list_selector_background);
            newRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedRow != null) {
                        selectedRow.setSelected(false);
                    }
                    selectedRow = newRow;
                    newRow.setSelected(true);
                    bankSelected = false;
                    mealSelected = true;
                    showButtons();
                    addToButton.setVisibility(View.INVISIBLE);
                    editButton.setVisibility(View.INVISIBLE);
                    for(Weightlifting wl : MainActivity.newWo.getLiftList()) {
                        if(wl.getKey() == newRow.getId()) selectedWl = wl;
                    }
                }
            });

            TextView t1v = new TextView(this);
            String liftName = selectedWl.getName();
            if (liftName.length() > 12) { liftName = (liftName.substring(0, Math.min(liftName.length(), 12))) + ".."; }
            t1v.setText(liftName);
            t1v.setGravity(Gravity.CENTER_HORIZONTAL);
            newRow.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText(String.valueOf(selectedWl.getSets()));
            t2v.setGravity(Gravity.CENTER_HORIZONTAL);
            newRow.addView(t2v);

            TextView t3v = new TextView(this);
            t3v.setText(String.valueOf(selectedWl.getReps()));
            t3v.setGravity(Gravity.CENTER_HORIZONTAL);
            newRow.addView(t3v);

            TextView t4v = new TextView(this);
            t4v.setText(String.valueOf(selectedWl.getWeight()));
            t4v.setGravity(Gravity.CENTER_HORIZONTAL);
            newRow.addView(t4v);
            woTable.addView(newRow);

            selectedRow.setSelected(false);
            selectedRow = null;
            selectedWl = null;
            addToButton.setVisibility(View.INVISIBLE);
            editButton.setVisibility(View.INVISIBLE);
            displayCreate();
        }
    }

    public void deleteWl(View view) {
        if (selectedWl != null) {
            woTable.removeView(selectedRow);
            MainActivity.newWo.getLiftList().remove(selectedWl);
            selectedRow = null;
            selectedWl = null;
            hideButtons();
            displayCreate();
        }
    }

    public void createNewWorkout(View view) {
        // Check if in edit mode
        if (EDayActivity.inEdit) { db.removeWorkout(originalWo); }

        String newWoName = woEntry.getText().toString();
        //boolean isNew = db.checkByNameRepsWeight(newMealName, );
        if (newWoName.matches("")) { // Check if meal name was entered
            Toast.makeText(this, "You did not name your meal.", Toast.LENGTH_SHORT).show();
        } else {
            // Add food items to meal database
            for (Weightlifting wl : MainActivity.newWo.getLiftList()) {
                Weightlifting newWl = new Weightlifting(wl.getName(), wl.getSets(), wl.getReps(), wl.getWeight(), newWoName, curDate, wl.getKey(), MainActivity.newWo.getKey());
                db.addWeightlifting(newWl, MainActivity.newWo.getKey());
            }
            Intent intent = new Intent(AddWeightliftingActivity.this, EDayActivity.class);
            startActivity(intent);
        }
    }

    public void toEDayActivity(View view) {
        Intent intent = new Intent(AddWeightliftingActivity.this, EDayActivity.class);
        startActivity(intent);
    }

    public void toAddLiftActivity(View view) {
        Intent intent = new Intent(AddWeightliftingActivity.this, AddLiftActivity.class);
        startActivity(intent);
    }

    public void toEditLiftActivity(View view){
        Intent intent = new Intent(AddWeightliftingActivity.this, EditLiftActivity.class);
        startActivity(intent);
    }

    public void openViewDialog(View view) {
        ViewWeightLiftingDialog viewWeightliftingDialog = new ViewWeightLiftingDialog(selectedWl);
        viewWeightliftingDialog.show(getSupportFragmentManager(), "view weightlifting dialog");
    }

    public void displayCreate() {
        // Check if in edit mode
        if (EDayActivity.inEdit) {
            createButton.setText("Update");
            woEntry.setText(MainActivity.newWo.getName());
        }

        if (MainActivity.newWo.getLiftList().isEmpty()) {
            createButton.setVisibility(View.INVISIBLE);
            woName.setVisibility(View.INVISIBLE);
            woEntry.setVisibility(View.INVISIBLE);
        } else {
            createButton.setVisibility(View.VISIBLE);
            woName.setVisibility(View.VISIBLE);
            woEntry.setVisibility(View.VISIBLE);
        }
    }

    public void hideButtons() {
        viewButton.setVisibility(View.INVISIBLE);
        deleteButton.setVisibility(View.INVISIBLE);
    }

    public void showButtons() {
        viewButton.setVisibility(View.VISIBLE);
        deleteButton.setVisibility(View.VISIBLE);
    }
}