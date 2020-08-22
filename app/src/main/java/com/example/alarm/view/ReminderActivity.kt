package com.example.alarm.view

import android.app.AlarmManager
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.*
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import com.example.alarm.*
import com.example.alarm.presenter.ReminderPresenter
import kotlinx.android.synthetic.main.fragment_reminder_dialog.*
import java.util.*

/**
 * Reminder Activity
 *
 */
class ReminderActivity : AppCompatActivity(), ReminderContract.View {

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
            val date = Calendar.getInstance()
            val hour = date.get(Calendar.HOUR_OF_DAY)
            val minute = date.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(  this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val formatted: String = presenter.getTime(hourOfDay, minute)
                buttonTime!!.text = formatted
            }, hour, minute, true)
            timePickerDialog.show()
        }

        val buttonSaveReminder = findViewById<FloatingActionButton>(R.id.fabSaveReminder)
        buttonSaveReminder.setOnClickListener {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intentAlarm = Intent(this, AlarmReceiver::class.java)
            presenter.startAlarm(alarmManager, intentAlarm, this, this.getSharedPreferences("com.example.alarm", Context.MODE_PRIVATE))
            val intentAlert = Intent(this, AlertReceiver::class.java)
            presenter.startAlert(alarmManager, intentAlert, this, getSharedPreferences("com.example.alarm", Context.MODE_PRIVATE))

            changeView()
        }

        val closeButton = findViewById<Toolbar>(R.id.toolbarReminder)
        closeButton.setNavigationOnClickListener {
            changeView()
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