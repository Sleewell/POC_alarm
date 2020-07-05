package com.example.alarm.model

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.example.alarm.ReminderContract
import com.example.alarm.view.ReminderActivity
import java.text.SimpleDateFormat
import java.util.*

class ReminderModel : ReminderContract.Model {

    companion object {
        lateinit var c : Calendar
    }

    override fun startAlarm(alarmManager: AlarmManager, intent: Intent, context: Context, sharedPreferences: SharedPreferences) {
        val pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0)
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1)
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.timeInMillis, pendingIntent)
        saveAlarm(c.timeInMillis, sharedPreferences)
    }

    override fun saveAlarm(time: Long, sharedPreferences: SharedPreferences) {
        sharedPreferences.edit().putLong(ReminderActivity.id, c.timeInMillis).apply()
        val newId = ReminderActivity.id.toInt() + 1
        ReminderActivity.id = newId.toString()
    }

    override fun getTime(hourOfDay: Int, minute: Int) : String {
        c = Calendar.getInstance()
        c[Calendar.HOUR_OF_DAY] = hourOfDay
        c[Calendar.MINUTE] = minute
        c[Calendar.SECOND] = 0

        val date = Date(c.time.toString())
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("GMT+2")
        val formatted: String = formatter.format(date)

        return formatted
    }
}