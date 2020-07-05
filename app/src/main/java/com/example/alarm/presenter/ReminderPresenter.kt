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

class ReminderPresenter(view: ReminderContract.View) : ReminderContract.Presenter,
        ReminderContract.Model.OnFinishedListener {

    private var model: ReminderContract.Model = ReminderModel()
    private var view: ReminderContract.View? = view

    override fun onDestroy() {
        this.view = null
    }

    override fun onViewCreated() {
    }

    override fun getTime(hourOfDay: Int, minute: Int): String {
        return model.getTime(hourOfDay, minute)
    }

    override fun startAlarm(alarmManager: AlarmManager, intent: Intent, context: Context, sharedPreferences: SharedPreferences) {
        model.startAlarm(alarmManager, intent, context, sharedPreferences)
    }

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