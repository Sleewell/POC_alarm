package com.raywenderlich.petmedicinereminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.format.DateUtils
import android.text.format.Time
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import kotlinx.android.synthetic.main.content_main.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var instance: MainActivity
            private set
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        instance = this

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fabCreateReminder: FloatingActionButton = findViewById(R.id.fabCreateReminder)
        fabCreateReminder.setOnClickListener {
            val intent = Intent(this@MainActivity, ReminderActivity::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recyclerViewReminders)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        displayReminders()
    }

    private fun displayReminders() {
        val reminderList = loadAllReminders()
        if (reminderList.isNotEmpty()) {
            recyclerView.visibility = View.VISIBLE
            textViewNoReminders.visibility = View.GONE
            for (i in reminderList) {
                val date = Date(i)

                val formatter = SimpleDateFormat("HH:mm")
                formatter.timeZone = TimeZone.getTimeZone("GMT+2")

                val formatted: String = formatter.format(date)
                Log.d("oui", formatted)
            }
            mainAdapter = MainAdapter(reminderList)
            recyclerView.adapter = mainAdapter
        }
    }

    private fun loadAllReminders(): List<Long> {
        val reminders = ArrayList<Long>(ReminderActivity.id.toInt() - 1)
        val sharedPref = this.getSharedPreferences("com.raywenderlich.petmedicinereminder", Context.MODE_PRIVATE)
        for (i in 1..ReminderActivity.id.toInt()) {
            val millis = sharedPref.getLong(i.toString(), 0)
            reminders.add(millis)
        }
        return reminders;
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
        ReminderActivity.instance.saveAlarm(nextUpdateTimeMillis)
    }

    fun cancelAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlertReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0)
        alarmManager.cancel(pendingIntent)
        if (AlertReceiver.isMpInitialised() && AlertReceiver.mp.isPlaying)
            AlertReceiver.mp.stop()
    }
}