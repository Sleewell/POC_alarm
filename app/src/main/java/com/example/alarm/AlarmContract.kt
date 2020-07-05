package com.example.alarm

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.example.alarm.global.BasePresenter
import com.example.alarm.global.BaseView
import java.util.ArrayList

interface AlarmContract {

    interface Model {

        fun snoozeAlarm(alarmManager: AlarmManager, intent: Intent, context: Context)
        fun cancelAlarm(alarmManager: AlarmManager, intent: Intent, context: Context)
        fun loadAllReminders(sharedPreferences: SharedPreferences): ArrayList<Long>

        interface OnFinishedListener {
            //fun onFinished(weather : ApiResult)
            fun onFailure(t : Throwable)
        }
    }

    interface Presenter : BasePresenter {
        fun onViewCreated(sharedPreferences: SharedPreferences)
        fun snoozeAlarm(alarmManager: AlarmManager, intent: Intent, context: Context)
        fun cancelAlarm(alarmManager: AlarmManager, intent: Intent, context: Context)
    }

    interface View : BaseView<Presenter> {
        fun showToast(msg: String)
        fun displayReminders(reminderList: ArrayList<Long>)
        fun snoozeAlarm()
        fun cancelAlarm()
    }
}
