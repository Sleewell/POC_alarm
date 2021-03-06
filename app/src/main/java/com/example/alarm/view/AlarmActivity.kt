package com.example.alarm.view

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Toast
import com.example.alarm.*
import com.example.alarm.presenter.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

/**
 * Alarm Activity
 *
 */
class AlarmActivity : AppCompatActivity(), AlarmContract.View {

    companion object {
        lateinit var instance: AlarmActivity
            private set
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var alarmAdapter: AlarmAdapter

    private lateinit var presenter: AlarmContract.Presenter

    /**
     * When view is created
     *
     * @param savedInstanceState Save of the instance state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        instance = this

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fabCreateReminder: FloatingActionButton = findViewById(R.id.fabCreateReminder)
        fabCreateReminder.setOnClickListener {
            val intent = Intent(this@AlarmActivity, ReminderActivity::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recyclerViewReminders)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        setPresenter(AlarmPresenter(this))
        presenter.onViewCreated(this.getSharedPreferences("com.example.alarm", Context.MODE_PRIVATE))
    }

    /**
     * Show message in toast
     *
     * @param msg Message to display
     */
    override fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    /**
     * Set the presenter of the view
     *
     * @param presenter The presenter
     */
    override fun setPresenter(presenter: AlarmContract.Presenter) {
        this.presenter = presenter
    }

    /**
     * Display all the reminders
     *
     * @param reminderList List of reminders
     */
    override fun displayReminders(reminderList: ArrayList<Long>) {
        if (reminderList.isNotEmpty()) {
            recyclerView.visibility = View.VISIBLE
            textViewNoReminders.visibility = View.GONE
            alarmAdapter = AlarmAdapter(reminderList)
            recyclerView.adapter = alarmAdapter
        }
    }

    /**
     * Snooze the alarm
     *
     */
    override fun snoozeAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        presenter.snoozeAlarm(alarmManager, intent, this)
    }

    /**
     * Cancel the alarm
     *
     */
    override fun cancelAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        presenter.cancelAlarm(alarmManager, intent, this)

    }

}