package com.example.prepreveil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {

    public static MediaPlayer mp;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());

        Uri alarmUri = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getActualDefaultRingtoneUri(context,RingtoneManager.TYPE_NOTIFICATION);
            if (alarmUri == null)
                alarmUri = RingtoneManager.getActualDefaultRingtoneUri(context,RingtoneManager.TYPE_RINGTONE);
        }
        mp = MediaPlayer.create(context, alarmUri);
        mp.setLooping(true);
        mp.start();
    }
}