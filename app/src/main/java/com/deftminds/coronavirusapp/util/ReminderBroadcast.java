package com.deftminds.coronavirusapp.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.deftminds.coronavirusapp.R;
import com.deftminds.coronavirusapp.activity.MainActivity;
import com.deftminds.coronavirusapp.fragment.TrainingFragment;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context,"YogaReminder")
                        .setSmallIcon(R.mipmap.ic_yoga) //set icon for notification
                        .setContentTitle("Hey!it's Workout time") //set title of notification
                        .setContentText("Yoga help you to stay fit & healthy.")//this is notification message
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT) //set priority of notification
                        .setAutoCancel(true);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //notification message will get at NotificationView
        notificationIntent.putExtra("message", "This is a notification message");

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        // Add as notification
        NotificationManagerCompat notificationManagerCompat = (NotificationManagerCompat.from(context));
        notificationManagerCompat.notify(200, builder.build());
    }
}
