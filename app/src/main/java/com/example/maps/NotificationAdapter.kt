package com.example.maps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class NotificationAdapter(context: Context, notifications: List<Notification>) :
    ArrayAdapter<Notification>(context, 0, notifications) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(
                R.layout.notification_list_item,
                parent,
                false
            )
        }

        val currentNotification = getItem(position)

        val titleTextView = listItemView!!.findViewById<TextView>(R.id.notificationTitle)
        titleTextView.text = currentNotification?.title

        val messageTextView = listItemView.findViewById<TextView>(R.id.notificationMessage)
        messageTextView.text = "${currentNotification?.message}\n${currentNotification?.getFormattedDate()}"

        return listItemView
    }
}