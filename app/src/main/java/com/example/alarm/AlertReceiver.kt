package com.example.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager

/**
 * Alert Receiver
 *
 */
class AlertReceiver : BroadcastReceiver() {

    companion object {
        lateinit var mp: MediaPlayer
        fun isMpInitialised() = ::mp.isInitialized
    }

    /**
     * When alert receiver receive a signal
     *
     * @param context Context of the application
     * @param intent Intent of the application
     */
    override fun onReceive(context: Context, intent: Intent) {
        val notificationHelper = AlertNotificationHelper(context)
        val nb = notificationHelper.channelNotification
        notificationHelper.manager?.notify(1, nb.build())
    }

}