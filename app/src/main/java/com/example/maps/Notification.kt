package com.example.maps

import java.text.SimpleDateFormat
import java.util.Date

data class Notification(val title: String, val message: String, val timestamp: Long = System.currentTimeMillis()) {
    //
    var vista: Boolean = false
    //

    fun getFormattedDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        return dateFormat.format(Date(timestamp))
    }

    object NotificationManager {
        private val notifications = mutableListOf<Notification>()

        fun sendNotification(title: String, message: String, timestamp: Long) {
            notifications.add(Notification(title, message))
        }

        fun getNotifications(): List<Notification> {
            return notifications
        }
        //

        fun marcarNotificacionesComoVistas() {
            for (notification in notifications) {
                notification.vista = true
            }
        }
        //
    }


}

