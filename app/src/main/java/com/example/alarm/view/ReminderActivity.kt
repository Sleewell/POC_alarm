package com.example.alarm.view

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import com.example.alarm.*
import com.example.alarm.presenter.AlarmPresenter
import com.example.alarm.presenter.ReminderPresenter
import kotlinx.android.synthetic.main.fragment_reminder_dialog.*
import kotlin.math.min


class ReminderActivity : AppCompatActivity(), OnTimeSetListener, ReminderContract.View {

    companion object {
        lateinit var instance: ReminderActivity
            private set
        var id = "1"
    }

    private lateinit var presenter: ReminderContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_reminder_dialog)
        instance = this

        this.supportActionBar?.hide();
        setPresenter(ReminderPresenter(this))

        val buttonTimePicker = findViewById<Button>(R.id.buttonTime)
        buttonTimePicker.setOnClickListener {
            val timePicker: DialogFragment = TimePickerFragment()
            timePicker.show(supportFragmentManager, "time picker")
        }

        val buttonSaveReminder = findViewById<FloatingActionButton>(R.id.fabSaveReminder)
        buttonSaveReminder.setOnClickListener {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(this, AlertReceiver::class.java)
            presenter.startAlarm(alarmManager, intent, this, this.getSharedPreferences("com.example.alarm", Context.MODE_PRIVATE))
            changeView()
        }

        val closeButton = findViewById<Toolbar>(R.id.toolbarReminder)
        closeButton.setNavigationOnClickListener {
            changeView()
        }
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {

        if (view.isShown) {
            val formatted: String = presenter.getTime(hourOfDay, minute)
            buttonTime!!.text = formatted
        }
    }

    override fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun setPresenter(presenter: ReminderContract.Presenter) {
        this.presenter = presenter
    }

    fun saveAlarm(time: Long) {
        presenter.saveAlarm(time, this.getSharedPreferences("com.example.alarm", Context.MODE_PRIVATE))
    }

    private fun changeView() {
        val intent = Intent(this@ReminderActivity, AlarmActivity::class.java)
        startActivity(intent)
    }

}