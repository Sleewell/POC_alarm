package com.raywenderlich.petmedicinereminder

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainAdapter(private val reminderList: List<Long>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewTimeToAdminister: TextView = itemView.findViewById(R.id.textViewTimeToAdminister)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_reminder_row, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val reminderData = reminderList[i]
        val date = Date(reminderData)
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("GMT+2")
        val formatted: String = formatter.format(date)

        viewHolder.textViewTimeToAdminister.text = formatted
            //      viewHolder.itemView.setOnClickListener {}
    }

    override fun getItemCount(): Int {
        return reminderList.size ?: 0
    }
}