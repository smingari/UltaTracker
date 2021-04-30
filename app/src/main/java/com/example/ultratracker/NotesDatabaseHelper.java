package com.example.ultratracker;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Calendar;
import java.util.List;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.widget.Toast;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

//import com.example.ultratracker.MainActivity;

public class NotesDatabaseHelper extends SQLiteOpenHelper {

    public static final String NOTES_TABLE = "NOTES_TABLE";
    public static final String COLUMN_KEY = "KEYID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_DATE = "ASSIGNEDDATE";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String COLUMN_ID = "ID";

    public static final String REMINDER_TABLE = "REMINDER_TABLE";
    public static final String REMINDER_COLUMN_KEY = "KEYID";
    public static final String REMINDER_COLUMN_NAME = "NAME";
    public static final String REMINDER_COLUMN_DATE = "ASSIGNEDDATE";
    public static final String REMINDER_COLUMN_TIME = "ASSIGNEDTIME";
    public static final String REMINDER_COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String REMINDER_COLUMN_ID = "ID";

    private Context context;

    public NotesDatabaseHelper(@Nullable Context context) {
        super(context, "notes.db", null, 1);
        this.context = context;
    }

    // Called first time a database is accessed
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + NOTES_TABLE + " (" + COLUMN_ID + " INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_KEY + " INT, " + COLUMN_NAME + " TEXT, " + COLUMN_DATE + " TEXT, " + COLUMN_DESCRIPTION + " TEXT) ";

        String createReminderTableStatement = "CREATE TABLE IF NOT EXISTS " + REMINDER_TABLE + " (" + REMINDER_COLUMN_ID + " INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                REMINDER_COLUMN_KEY + " INT, " + REMINDER_COLUMN_NAME + " TEXT, " + REMINDER_COLUMN_DATE + " TEXT, " + REMINDER_COLUMN_TIME + " TEXT, " + REMINDER_COLUMN_DESCRIPTION + " TEXT) ";

        db.execSQL(createTableStatement);
        db.execSQL(createReminderTableStatement);
    }

    // this is called if the database version number changes. Prevents db from breaking
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addNote(Note note) {

        String queryString = "SELECT * FROM " + NOTES_TABLE + " WHERE " + COLUMN_KEY + " = " + note.getKey();

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_KEY, note.getKey());
        cv.put(COLUMN_NAME, note.getName());
        cv.put(COLUMN_DATE, note.getDate());
        cv.put(COLUMN_DESCRIPTION, note.getDesc());

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return false;
        }
        cursor.close();
        long insert = db.insert(NOTES_TABLE, null, cv);
        return insert != -1;
    }


    public boolean deleteNote(Note note) {
        // get data from the database
        String queryString = "DELETE FROM " + NOTES_TABLE + " WHERE " + COLUMN_KEY + " = " + note.getKey();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public List<Note> getAllNotes() {
        List<Note> returnList = new ArrayList<>();

        // get data from the database
        String queryString = "SELECT * FROM " + NOTES_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        // move to the first result. If it is true then there is at least 1 value
        if(cursor.moveToFirst()) {
            // loop through cursor and create new food objects and put in return list
            do{
                int key = cursor.getInt(1);
                String name = cursor.getString(2);
                String date = cursor.getString(3);
                String description = cursor.getString(4);

                Note newNote = new Note(name, date, description, key);

                returnList.add(newNote);
            } while (cursor.moveToNext());
        }

        //returnList = sortNotes(returnList);
        Collections.sort(returnList, Note.reminderComparator);
        cursor.close();
        db.close();
        return returnList;
    }

    public boolean editNote(Note note) {
        // get data from the database
        String queryString = "SELECT * FROM " + NOTES_TABLE + " WHERE " + COLUMN_KEY + " = " + note.getKey();
        SQLiteDatabase db = this.getWritableDatabase();

        // Find user
        String name;
        String date;
        String description;
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            name = note.getName();
            date = note.getDate();
            description = note.getDesc();

            ContentValues cv = new ContentValues();
            cv.put(COLUMN_KEY, note.getKey());
            cv.put(COLUMN_NAME, name);
            cv.put(COLUMN_DATE, date);
            cv.put(COLUMN_DESCRIPTION, description);

            String[] whereArgs = {String.valueOf(note.getKey())};
            int success = db.update(NOTES_TABLE, cv, "keyid=?", whereArgs);
            if (success > 0) {
                db.close();
                cursor.close();
                return true;
            }
        }
        db.close();
        cursor.close();
        return false;
    }

    public boolean addReminder(Reminder rem) {

        String queryString = "SELECT * FROM " + REMINDER_TABLE + " WHERE " + REMINDER_COLUMN_KEY + " = " + rem.getKey();

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(REMINDER_COLUMN_KEY, rem.getKey());
        cv.put(REMINDER_COLUMN_NAME, rem.getName());
        cv.put(REMINDER_COLUMN_DATE, rem.getDate());
        cv.put(REMINDER_COLUMN_TIME, rem.getTime());
        cv.put(REMINDER_COLUMN_DESCRIPTION, rem.getDesc());
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return false;
        }
        cursor.close();
        long insert = db.insert(REMINDER_TABLE, null, cv);
        if(insert != -1) {
            //Schedule new notification
            PackageManager pm = context.getPackageManager();
            ComponentName receiver = new ComponentName(context, DeviceBootReceiver.class);
            Intent alarmIntent = new Intent(context, ReminderReceiver.class);
            PendingIntent pendingIntent;
            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            alarmIntent.putExtra("name", rem.getName());
            alarmIntent.putExtra("description", rem.getDesc());
            alarmIntent.putExtra("key", rem.getKey());
            pendingIntent = PendingIntent.getBroadcast(context, rem.getKey(), alarmIntent, 0);

            LocalDate ld = LocalDate.parse(rem.getDate());
            LocalTime lt = LocalTime.parse(rem.getTime());

            //region Enable Daily Notifications
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(ld.getYear(), ld.getMonthValue()-1, ld.getDayOfMonth(), lt.getHour(), lt.getMinute(), 0);

            if (manager != null) {
                manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }
            //To enable Boot Receiver class
            pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
            return true;
        } else return false;
    }


    public boolean deleteReminder(Reminder rem) {
        // get data from the database
        String queryString = "DELETE FROM " + REMINDER_TABLE + " WHERE " + REMINDER_COLUMN_KEY + " = " + rem.getKey();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        Intent alarmIntent = new Intent(context, ReminderReceiver.class);
        PendingIntent pendingIntent;
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        pendingIntent = PendingIntent.getBroadcast(context, rem.getKey(), alarmIntent, 0);

        if (PendingIntent.getBroadcast(context, rem.getKey(), alarmIntent, PendingIntent.FLAG_NO_CREATE) != null && manager != null) {
            manager.cancel(pendingIntent);
        }
        if(cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public List<Reminder> getAllReminders() {
        List<Reminder> returnList = new ArrayList<>();

        // get data from the database
        String queryString = "SELECT * FROM " + REMINDER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        // move to the first result. If it is true then there is at least 1 value
        if(cursor.moveToFirst()) {
            // loop through cursor and create new food objects and put in return list
            do{
                int key = cursor.getInt(1);
                String name = cursor.getString(2);
                String date = cursor.getString(3);
                String time = cursor.getString(4);
                String description = cursor.getString(5);

                Reminder newRem = new Reminder(name, date, time, description, key);

                returnList.add(newRem);
            } while (cursor.moveToNext());
        }

        //returnList = sortReminders(returnList);
        Collections.sort(returnList, Reminder.reminderComparator);
        cursor.close();
        db.close();
        return returnList;
    }

    public List<Reminder> getAllRemindersByDate(String currentDate) {
        List<Reminder> returnList = new ArrayList<>();

        // get data from the database
        String queryString = "SELECT * FROM " + REMINDER_TABLE + " WHERE " + REMINDER_COLUMN_DATE + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, new String[] {currentDate});

        // move to the first result. If it is true then there is at least 1 value
        if (cursor.moveToFirst()) {
            do {
                int key = cursor.getInt(1);
                String name = cursor.getString(2);
                String date = cursor.getString(3);
                String time = cursor.getString(4);
                String description = cursor.getString(5);

                Reminder newRem = new Reminder(name, date, time, description, key);

                returnList.add(newRem);
            } while(cursor.moveToNext());
        }

        //returnList = sortReminders(returnList);
        Collections.sort(returnList, Reminder.reminderComparator);
        cursor.close();
        db.close();
        return returnList;
    }

    public boolean editReminder(Reminder rem) {
        // get data from the database
        String queryString = "SELECT * FROM " + REMINDER_TABLE + " WHERE " + REMINDER_COLUMN_KEY + " = " + rem.getKey();
        SQLiteDatabase db = this.getWritableDatabase();

        // Find user
        String name;
        String date;
        String time;
        String description;
        Cursor cursor = db.rawQuery(queryString, null);
        int success = 0;
        if (cursor.moveToFirst()) {
            name = rem.getName();
            date = rem.getDate();
            time = rem.getTime();
            description = rem.getDesc();

            ContentValues cv = new ContentValues();
            cv.put(REMINDER_COLUMN_KEY, rem.getKey());
            cv.put(REMINDER_COLUMN_NAME, name);
            cv.put(REMINDER_COLUMN_DATE, date);
            cv.put(REMINDER_COLUMN_TIME, time);
            cv.put(REMINDER_COLUMN_DESCRIPTION, description);

            String[] whereArgs = {String.valueOf(rem.getKey())};
            success = db.update(REMINDER_TABLE, cv, "keyid=?", whereArgs);
        }
        db.close();
        cursor.close();
        if (success > 0) {
            Intent alarmIntent = new Intent(context, ReminderReceiver.class);
            PendingIntent pendingIntent;
            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            alarmIntent.putExtra("name", rem.getName());
            alarmIntent.putExtra("description", rem.getDesc());
            alarmIntent.putExtra("key", rem.getKey());
            pendingIntent = PendingIntent.getBroadcast(context, rem.getKey(), alarmIntent, FLAG_UPDATE_CURRENT);

            LocalDate ld = LocalDate.parse(rem.getDate());
            LocalTime lt = LocalTime.parse(rem.getTime());

            //region Enable Daily Notifications
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(ld.getYear(), ld.getMonthValue()-1, ld.getDayOfMonth(), lt.getHour(), lt.getMinute(), 0);

            if (manager != null) {
                manager.cancel(pendingIntent);
                manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }
            return true;
        } else return false;
    }

//    public List<Note> sortNotes (List<Note> noteList) {
//        HashMap<Integer, List<Note>> hashMap = new HashMap<Integer, List<Note>>(500000);
//        List<Note> returnList = new ArrayList<>();
//
//        for (Note note: noteList) {
//            if (!hashMap.containsKey(note.getKey())) {
//                List<Note> list = new ArrayList<>();
//                list.add(note);
//                hashMap.put(note.getKey(), list);
//                // taskKeys.add(task.getKey());
//            } else {
//                hashMap.get(note.getKey()).add(note);
//            }
//        }
//
//        for (int i = 0; i < 500000; i++) {
//            if (hashMap.get(i) != null) {
//                List<Note> notes = hashMap.get(i);
//                for (int j = 0; j < notes.size(); j++) {
//                    returnList.add(notes.get(j));
//                }
//            }
//        }
//        return returnList;
//    }
//
//    public List<Reminder> sortReminders (List<Reminder> remList) {
//        HashMap<Integer, List<Reminder>> hashMap = new HashMap<Integer, List<Reminder>>(500000);
//        List<Reminder> returnList = new ArrayList<>();
//
//        for (Reminder rem: remList) {
//            if (!hashMap.containsKey(rem.getKey())) {
//                List<Reminder> list = new ArrayList<>();
//                list.add(rem);
//                hashMap.put(rem.getKey(), list);
//                // taskKeys.add(task.getKey());
//            } else {
//                hashMap.get(rem.getKey()).add(rem);
//            }
//        }
//
//        for (int i = 0; i < 500000; i++) {
//            if (hashMap.get(i) != null) {
//                List<Reminder> rems = hashMap.get(i);
//                for (int j = 0; j < rems.size(); j++) {
//                    returnList.add(rems.get(j));
//                }
//            }
//        }
//        return returnList;
//    }
}