package com.example.prepreveil

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat

class GlobalReceiver : BroadcastReceiver() {

    private fun stopAlarm(context: Context?) {
        val notificationManager = NotificationManagerCompat.from(context!!)
        notificationManager.cancel(1)
        notificationManager.cancelAll()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null && intent.action != null) {
            when (intent.action) {
                "Stop" -> {
                    MainActivity.instance.cancelAlarm()
                    stopAlarm(context)
                }
                "Snooze" -> {
                    MainActivity.instance.cancelAlarm()
                    MainActivity.instance.snoozeAlarm()
                    stopAlarm(context)
                }
            }
        }
    }
}