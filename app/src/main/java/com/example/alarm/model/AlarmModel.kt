package com.example.alarm.model

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.text.format.DateUtils
import android.text.format.Time
import com.example.alarm.AlertReceiver
import com.example.alarm.AlarmContract
import com.example.alarm.view.ReminderActivity
import java.util.ArrayList

/**
 * Alarm Model for the Alarm activity
 *
 */
class AlarmModel : AlarmContract.Model {

    /**
     * Snooze the alarm
     *
     * @param alarmManager Alarm manager of phone
     * @param intent Intent of the activity
     * @param context Context of the activity
     */
    override fun snoozeAlarm(alarmManager: AlarmManager, intent: Intent, context: Context) {
        val pendingIntent = PendingIntent.getBroadcast(context, 2, intent, 0)

        val currentTimeMillis = System.currentTimeMillis();
        val nextUpdateTimeMillis = currentTimeMillis + 1 * DateUtils.MINUTE_IN_MILLIS
        val nextUpdateTime = Time()
        nextUpdateTime.set(nextUpdateTimeMillis)

        alarmManager.setExact(AlarmManager.RTC, nextUpdateTimeMillis, pendingIntent)
        ReminderActivity.instance.saveAlarm(nextUpdateTimeMillis)
    }

    /**
     * Cancel the alarm
     *
     * @param alarmManager Alarm manager of phone
     * @param intent Intent of the activity
     * @param context Context of the activity
     */
    override fun cancelAlarm(alarmManager: AlarmManager, intent: Intent, context: Context) {
        val pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0)
        alarmManager.cancel(pendingIntent)
        if (AlertReceiver.isMpInitialised() && AlertReceiver.mp.isPlaying)
            AlertReceiver.mp.stop()
    }

    /**
     * Load all the saved reminders
     *
     * @param sharedPreferences Shared preferences of the application
     * @return List of reminders
     */
    override fun loadAllReminders(sharedPreferences: SharedPreferences): ArrayList<Long> {
        val reminders = ArrayList<Long>(ReminderActivity.id.toInt() - 1)
        for (i in 1..ReminderActivity.id.toInt()) {
            val millis = sharedPreferences.getLong(i.toString(), 0)
            reminders.add(millis)
        }
        return reminders;
    }

}