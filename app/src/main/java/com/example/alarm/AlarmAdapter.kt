package com.example.alarm

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

/**
 * Adapter for Alarm Activity
 *
 * @property reminderList List of the reminders
 */
class AlarmAdapter(private val reminderList: List<Long>) : RecyclerView.Adapter<AlarmAdapter.ViewHolder>() {

    /**
     * View holder class
     *
     * @param itemView Item of the view
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewTimeToAdminister: TextView = itemView.findViewById(R.id.textViewTimeToAdminister)
    }

    /**
     * When create view Holder
     *
     * @param viewGroup The group of the view
     * @param i Index
     * @return The view holder
     */
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_reminder_row, viewGroup, false)
        return ViewHolder(view)
    }

    /**
     * When the view holder is bind
     *
     * @param viewHolder The view holder
     * @param i Index
     */
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val reminderData = reminderList[i]
        val date = Date(reminderData)
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("GMT+2")
        val formatted: String = formatter.format(date)

        viewHolder.textViewTimeToAdminister.text = formatted
            //      viewHolder.itemView.setOnClickListener {}
    }

    /**
     * Get size of the list
     *
     * @return Size of the list
     */
    override fun getItemCount(): Int {
        return reminderList.size ?: 0
    }
}