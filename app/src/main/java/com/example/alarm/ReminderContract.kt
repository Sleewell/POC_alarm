package com.example.alarm

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.example.alarm.global.BasePresenter
import com.example.alarm.global.BaseView

interface ReminderContract {

    interface Model {

        fun startAlarm(alarmManager: AlarmManager, intent: Intent, context: Context, sharedPreferences: SharedPreferences)
        fun saveAlarm(time: Long, sharedPreferences: SharedPreferences)
        fun getTime(hourOfDay: Int, minute: Int) : String

        interface OnFinishedListener {
            //fun onFinished(weather : ApiResult)
            fun onFailure(t : Throwable)
        }
    }

    interface Presenter : BasePresenter {
        fun onViewCreated()
        fun getTime(hourOfDay: Int, minute: Int): String
        fun startAlarm(alarmManager: AlarmManager, intent: Intent, context: Context, sharedPreferences: SharedPreferences)
        fun saveAlarm(time: Long, sharedPreferences: SharedPreferences)
    }

    interface View : BaseView<Presenter> {
        fun showToast(msg: String)
    }
}
