package com.trackiq.messagealarm.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.trackiq.messagealarm.MainActivity
import com.trackiq.messagealarm.R

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("FCM", "Message received from: ${remoteMessage.from}")

        // Handle notification
        remoteMessage.notification?.let {
            sendNotification(it.title ?: "Alert", it.body ?: "")
        }

        // Handle data payload
        if (remoteMessage.data.isNotEmpty()) {
            handleDataMessage(remoteMessage.data)
        }
    }

    override fun onNewToken(token: String) {
        Log.d("FCM", "New token: $token")
        // Save token to Firestore or backend
        sendTokenToServer(token)
    }

    private fun sendNotification(title: String, messageBody: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val channelId = "message_alarm_pro_channel"
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create notification channel for Android 8+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Message Alarm Pro",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }

    private fun handleDataMessage(data: Map<String, String>) {
        val alertType = data["type"] ?: return
        val message = data["message"] ?: ""
        val priority = data["priority"] ?: "medium"

        when (alertType) {
            "upgrade_reminder" -> {
                sendNotification("Upgrade Available", message)
            }
            "plan_expiry" -> {
                sendNotification("Plan Expiring Soon", message)
            }
            "new_feature" -> {
                sendNotification("New Feature", message)
            }
        }
    }

    private fun sendTokenToServer(token: String) {
        // TODO: Send token to your backend or Firestore
        Log.d("FCM", "Token saved: $token")
    }
}
