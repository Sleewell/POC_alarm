package com.example.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.security.spec.PKCS8EncodedKeySpec;

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

            mediaPlayer = MediaPlayer.create(this, R.raw.mii_theme);
            mediaPlayer.start();

            isRunning = true;
            start_id = 0;

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
        super.onDestroy();;
        isRunning = false;
    }
}
