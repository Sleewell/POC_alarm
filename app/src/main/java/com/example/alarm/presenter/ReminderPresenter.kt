package com.example.alarm.presenter

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.Toolbar
import com.example.alarm.R
import com.example.alarm.ReminderContract
import com.example.alarm.model.ReminderModel

/**
 * Reminder Presenter for the Reminder activity
 *
 * @param view View of the presenter
 */
class ReminderPresenter(view: ReminderContract.View) : ReminderContract.Presenter,
        ReminderContract.Model.OnFinishedListener {

    private var model: ReminderContract.Model = ReminderModel()
    private var view: ReminderContract.View? = view

    /**
     * On the destroy of the presenter
     *
     */
    override fun onDestroy() {
        this.view = null
    }

    /**
     * When view is created
     *
     */
    override fun onViewCreated() {
    }

    /**
     * Get time of the alarm
     *
     * @param hourOfDay Hour of the alarm
     * @param minute Minutes of the alarm
     * @return Time in a string
     */
    override fun getTime(hourOfDay: Int, minute: Int): String {
        return model.getTime(hourOfDay, minute)
    }

    /**
     * Start the alarm
     *
     * @param alarmManager Alarm manager of phone
     * @param intent Intent of the activity
     * @param context Context of the activity
     * @param sharedPreferences Shared preferences of the application
     */
    override fun startAlarm(alarmManager: AlarmManager, intent: Intent, context: Context, sharedPreferences: SharedPreferences) {
        model.startAlarm(alarmManager, intent, context, sharedPreferences)
    }

    /**
     * Save the alarm
     *
     * @param time Time of the alarm
     * @param sharedPreferences Shared preferences of the application
     */
    override fun saveAlarm(time: Long, sharedPreferences: SharedPreferences) {
        model.saveAlarm(time, sharedPreferences)
    }

    override fun onFailure(t: Throwable) {
        if (t.message != null)
            view?.showToast(t.message!!)
        else
            view?.showToast("An error occurred")
    }
}