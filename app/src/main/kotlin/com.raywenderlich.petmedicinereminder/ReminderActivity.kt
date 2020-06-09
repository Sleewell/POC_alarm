package com.raywenderlich.petmedicinereminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.support.design.widget.AppBarLayout
import android.util.Log
import kotlinx.android.synthetic.main.fragment_reminder_dialog.*
import java.text.DateFormat


class ReminderActivity : AppCompatActivity(), OnTimeSetListener {

    companion object {
        lateinit var instance: ReminderActivity
            private set
        var id = "1"
        lateinit var c : Calendar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_reminder_dialog)

        this.supportActionBar?.hide();

        instance = this

        val buttonTimePicker = findViewById<Button>(R.id.buttonTime)
        buttonTimePicker.setOnClickListener {
            val timePicker: DialogFragment = TimePickerFragment()
            timePicker.show(supportFragmentManager, "time picker")
        }

        val buttonSaveReminder = findViewById<FloatingActionButton>(R.id.fabSaveReminder)
        buttonSaveReminder.setOnClickListener {
            startAlarm(c)
            changeView()
        }

        val closeButton = findViewById<Toolbar>(R.id.toolbarReminder)
        closeButton.setNavigationOnClickListener {
            changeView()
        }
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {

        if (view.isShown) {
            c = Calendar.getInstance()
            c[Calendar.HOUR_OF_DAY] = hourOfDay
            c[Calendar.MINUTE] = minute
            c[Calendar.SECOND] = 0

            val date = Date(c.time.toString())
            val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
            formatter.timeZone = TimeZone.getTimeZone("GMT+2")
            val formatted: String = formatter.format(date)

            buttonTime!!.text = formatted
        }
    }

    fun changeView() {
        val intent = Intent(this@ReminderActivity, MainActivity::class.java)
        startActivity(intent)
    }

    fun saveAlarm(time: Long) {

        val sharedPref = this.getSharedPreferences("com.raywenderlich.petmedicinereminder", Context.MODE_PRIVATE)
        sharedPref.edit().putLong(id, time).apply()
        val newId = id.toInt() + 1
        id = newId.toString()

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

}