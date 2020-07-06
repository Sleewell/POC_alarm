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

/**
 * Reminder Activity
 *
 */
class ReminderActivity : AppCompatActivity(), OnTimeSetListener, ReminderContract.View {

    companion object {
        lateinit var instance: ReminderActivity
            private set
        var id = "1"
    }

    private lateinit var presenter: ReminderContract.Presenter

    /**
     * When view is created
     *
     * @param savedInstanceState Save of the instance state
     */
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

    /**
     * When time is set on Time Picker
     *
     * @param view The time picker
     * @param hourOfDay Hours of the alarm
     * @param minute Minutes of the alarm
     */
    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {

        if (view.isShown) {
            val formatted: String = presenter.getTime(hourOfDay, minute)
            buttonTime!!.text = formatted
        }
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
    override fun setPresenter(presenter: ReminderContract.Presenter) {
        this.presenter = presenter
    }

    /**
     * Save the alarm
     *
     * @param time Time of the alarm
     */
    fun saveAlarm(time: Long) {
        presenter.saveAlarm(time, this.getSharedPreferences("com.example.alarm", Context.MODE_PRIVATE))
    }

    /**
     * Change the current view
     *
     */
    private fun changeView() {
        val intent = Intent(this@ReminderActivity, AlarmActivity::class.java)
        startActivity(intent)
    }

}