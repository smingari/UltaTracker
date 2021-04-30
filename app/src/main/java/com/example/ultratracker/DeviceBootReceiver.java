package com.example.ultratracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class DeviceBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), "android.intent.action.BOOT_COMPLETED")) {
            // on device boot complete, reset the alarm
            Intent alarmIntent;
            PendingIntent pendingIntent;
            AlarmManager manager;
            NotesDatabaseHelper db = new NotesDatabaseHelper(context);
            List<Reminder> remList = db.getAllReminders();
            for(Reminder rem : remList) {
                LocalDate ld = LocalDate.parse(rem.getDate());
                LocalTime lt = LocalTime.parse(rem.getTime());

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(ld.getYear(), ld.getMonthValue() - 1, ld.getDayOfMonth(), lt.getHour(), lt.getMinute(), 0);

                if(calendar.getTimeInMillis() >= System.currentTimeMillis()) {
                    alarmIntent = new Intent(context, ReminderReceiver.class);
                    alarmIntent.putExtra("name", rem.getName());
                    alarmIntent.putExtra("description", rem.getDesc());
                    alarmIntent.putExtra("key", rem.getKey());
                    pendingIntent = PendingIntent.getBroadcast(context, rem.getKey(), alarmIntent, 0);
                    manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    if (manager != null) {
                        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        }
                    }
                }
            }
        }
    }
}
