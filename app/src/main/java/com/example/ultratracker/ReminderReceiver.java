package com.example.ultratracker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        /*
        int key = intent.getIntExtra("key", -1);
        Reminder rem = new Reminder(name, null, null, description, key);
        NotesDatabaseHelper db = new NotesDatabaseHelper(context);
        db.deleteReminder(rem);
         */

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, MainActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingI = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Reminder Notification", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Reminder");
            if (nm != null) {
                nm.createNotificationChannel(channel);
            }
        }
        NotificationCompat.Builder b = new NotificationCompat.Builder(context, "default");
        b.setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                //.setTicker("UltaTracker")
                .setContentTitle(name)
                .setContentText(description)
                //.setContentInfo("INFO")
                .setContentIntent(pendingI);

        if (nm != null) {
            nm.notify(1, b.build());
        }
    }
}
