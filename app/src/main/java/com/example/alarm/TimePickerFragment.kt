package com.example.alarm

import android.app.Dialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.format.DateFormat
import java.util.*

/**
 * Time picker class
 *
 */
class TimePickerFragment : DialogFragment() {

    /**
     * When open TimePicker
     *
     * @param savedInstanceState Save of the instance state
     * @return Time ticker
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c[Calendar.HOUR_OF_DAY]
        val minute = c[Calendar.MINUTE]
        return TimePickerDialog(activity, activity as OnTimeSetListener?, hour, minute, DateFormat.is24HourFormat(activity))
    }


}