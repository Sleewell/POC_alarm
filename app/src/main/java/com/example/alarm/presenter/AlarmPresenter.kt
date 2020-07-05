package com.example.alarm.presenter

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.example.alarm.AlarmContract
import com.example.alarm.model.AlarmModel

class AlarmPresenter(view: AlarmContract.View) : AlarmContract.Presenter,
        AlarmContract.Model.OnFinishedListener {

    private var model: AlarmContract.Model = AlarmModel()
    private var view: AlarmContract.View? = view

    override fun onDestroy() {
        this.view = null
    }

    override fun onViewCreated(sharedPreferences: SharedPreferences) {
        val reminderList: ArrayList<Long> = model.loadAllReminders(sharedPreferences)
        view?.displayReminders(reminderList)
    }

    override fun snoozeAlarm(alarmManager: AlarmManager, intent: Intent, context: Context) {
        model.snoozeAlarm(alarmManager, intent, context)
    }

    override fun cancelAlarm(alarmManager: AlarmManager, intent: Intent, context: Context) {
        model.cancelAlarm(alarmManager, intent, context)
    }

    override fun onFailure(t: Throwable) {
        if (t.message != null)
            view?.showToast(t.message!!)
        else
            view?.showToast("An error occurred")
    }
}