package com.example.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Random;

public class RingtonePlayingService extends Service {

    MediaPlayer mediaPlayer;
    boolean isRunning;
    int start_id;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String state = intent.getExtras().getString("extra");
        Integer musicChoice = intent.getExtras().getInt("music_choice");

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent intentMainActivity = new Intent(this.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntentMainActivity = PendingIntent.getActivity(this, 0, intentMainActivity, 0);
        Notification notificationPopUp = new Notification.Builder(this)
                .setContentTitle("An alarm is going off")
                .setContentText("Click me")
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntentMainActivity)
                .setAutoCancel(true)
                .build();

        assert state != null;
        switch (state) {
            case "alarm on":
                start_id = 1;
                break;
            default:
                start_id = 0;
                break;
        }

        if (!this.isRunning && start_id == 1) {

            isRunning = true;
            start_id = 0;

            assert musicChoice != null;
            if (musicChoice == 0) {

                int min = 1;
                int max = 2;

                Random random = new Random();
                int musicNumber = random.nextInt(max + min);

                if (musicNumber == 1) {

                    mediaPlayer = MediaPlayer.create(this, R.raw.bad_guy);
                    mediaPlayer.start();

                } else {

                    mediaPlayer = MediaPlayer.create(this, R.raw.mii_theme);
                    mediaPlayer.start();

                }

            } else if (musicChoice == 1) {

                mediaPlayer = MediaPlayer.create(this, R.raw.bad_guy);
                mediaPlayer.start();

            } else {

                mediaPlayer = MediaPlayer.create(this, R.raw.mii_theme);
                mediaPlayer.start();

            }

        } else if (!this.isRunning && start_id == 0) {

            isRunning = false;
            start_id = 0;

        } else if (this.isRunning && start_id == 1) {

            isRunning = true;
            start_id = 1;

        } else {

            mediaPlayer.stop();
            mediaPlayer.reset();

            isRunning = false;
            start_id = 1;

        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }
}
