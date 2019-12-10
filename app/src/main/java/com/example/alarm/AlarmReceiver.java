package com.example.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String getYourString = intent.getExtras().getString("extra");
        Integer getYourMusicChoice = intent.getExtras().getInt("music_choice");
        Intent serviceIntent = new Intent(context, RingtonePlayingService.class);
        serviceIntent.putExtra("extra", getYourString);
        serviceIntent.putExtra("music_choice", getYourMusicChoice);
        context.startService(serviceIntent);
    }
}