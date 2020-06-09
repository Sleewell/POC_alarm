package com.example.prepreveil

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.format.DateUtils
import android.text.format.Time
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), OnTimeSetListener {

    companion object {
        lateinit var instance: MainActivity
            private set
        var id = "1"
    }

    private var mTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
        setContentView(R.layout.activity_main)
        mTextView = findViewById(R.id.textView)
        val buttonTimePicker = findViewById<Button>(R.id.button_timepicker)
        buttonTimePicker.setOnClickListener {
            val timePicker: DialogFragment = TimePickerFragment()
            timePicker.show(supportFragmentManager, "time picker")
        }
        val buttonCancelAlarm = findViewById<Button>(R.id.button_cancel)
        buttonCancelAlarm.setOnClickListener { cancelAlarm() }
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        if (view.isShown) {
            val c = Calendar.getInstance()
            c[Calendar.HOUR_OF_DAY] = hourOfDay
            c[Calendar.MINUTE] = minute
            c[Calendar.SECOND] = 0
            updateTimeText(c)
            startAlarm(c)
        }
    }

    private fun updateTimeText(c: Calendar) {
        var timeText: String? = "Alarm set for: "
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.time)
        mTextView!!.text = timeText
    }

    private fun saveAlarm(time: Long) {

        val sharedPref = this.getSharedPreferences("com.example.prepreveil", Context.MODE_PRIVATE)
        sharedPref.edit().putLong(id, time).apply()
        val newId = id.toInt() + 1
        id = newId.toString()

        for (i in 1..id.toInt()) {
            val millis = sharedPref.getLong(i.toString(), 0)
            // New date object from millis
            // New date object from millis
            val date = Date(millis)
// formattter
// formattter
            val formatter = SimpleDateFormat("HH:mm:ss.SSS")
            formatter.timeZone = TimeZone.getTimeZone("GMT+2")
// Pass date object
// Pass date object
            val formatted: String = formatter.format(date)
            Log.d("test", formatted)
        }
    }

    private fun startAlarm(c: Calendar) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlertReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0)
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1)
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.timeInMillis, pendingIntent)
        saveAlarm(c.timeInMillis)
    }

    fun snoozeAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlertReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 2, intent, 0)

        val currentTimeMillis = System.currentTimeMillis();
        val nextUpdateTimeMillis = currentTimeMillis + 1 * DateUtils.MINUTE_IN_MILLIS
        val nextUpdateTime = Time()
        nextUpdateTime.set(nextUpdateTimeMillis)

        alarmManager.setExact(AlarmManager.RTC, nextUpdateTimeMillis, pendingIntent)
        saveAlarm(nextUpdateTimeMillis)
    }

    fun cancelAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlertReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0)
        alarmManager.cancel(pendingIntent)
        mTextView!!.text = "Alarm canceled"
        if (AlertReceiver.isMpInitialised() && AlertReceiver.mp.isPlaying)
            AlertReceiver.mp.stop()
    }
}