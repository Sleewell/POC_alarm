package com.example.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationManagerCompat
import com.example.alarm.view.AlarmActivity

/**
 * Notification receiver
 *
 */
class GlobalReceiver : BroadcastReceiver() {

    /**
     * Stop the alarm when button 'stop" have been click on the notificatop,
     *
     * @param context Context of the application
     */
    private fun stopAlarm(context: Context?) {
        val notificationManager = NotificationManagerCompat.from(context!!)
        notificationManager.cancel(1)
        notificationManager.cancelAll()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null && intent.action != null) {
            when (intent.action) {
                "Stop" -> {
                    AlarmActivity.instance.cancelAlarm()
                    stopAlarm(context)
                }
                "Snooze" -> {
                    AlarmActivity.instance.cancelAlarm()
                    AlarmActivity.instance.snoozeAlarm()
                    stopAlarm(context)
                }
            }
        }
    }
}