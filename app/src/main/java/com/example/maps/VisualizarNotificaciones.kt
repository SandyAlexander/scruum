package com.example.maps

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class VisualizarNotificaciones : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_list)

        val notificationList = findViewById<ListView>(R.id.notificationList)
        val adapter = NotificationAdapter(this, Notification.NotificationManager.getNotifications())
        notificationList.adapter = adapter

        val intent = Intent()
        intent.putExtra("totalNotificaciones", Notification.NotificationManager.getNotifications().size)
        setResult(RESULT_OK, intent)
    }
}