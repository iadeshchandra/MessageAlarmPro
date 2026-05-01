package com.trackiq.messagealarm.services

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import android.os.Vibrator
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.trackiq.messagealarm.data.models.Alert
import com.trackiq.messagealarm.data.models.AlertRule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class NotificationListenerService : NotificationListenerService() {

    private val firestore = FirebaseFirestore.getInstance()
    private val scope = CoroutineScope(Dispatchers.Main)
    private lateinit var audioManager: AudioManager
    private lateinit var vibrator: Vibrator
    private lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        Log.d("NotificationListener", "Service created")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        super.onNotificationPosted(sbn)
        
        val notification = sbn.notification
        val extras = notification.extras
        
        val appName = sbn.packageName
        val title = extras.getString(Notification.EXTRA_TITLE) ?: ""
        val text = extras.getString(Notification.EXTRA_TEXT) ?: ""
        val message = "$title: $text"

        Log.d("NotificationListener", "Notification from: $appName - $message")

        // Process notification with rules
        processNotification(appName, message)
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        super.onNotificationRemoved(sbn)
        Log.d("NotificationListener", "Notification removed: ${sbn.packageName}")
    }

    private fun processNotification(appName: String, message: String) {
        scope.launch {
            try {
                // Get current user
                val userId = firestore.collection("users").document("current_user").get().await()
                    .getString("id") ?: return@launch

                // Get user's rules
                val rulesSnapshot = firestore.collection("users").document(userId)
                    .collection("rules").get().await()

                val rules = rulesSnapshot.toObjects(AlertRule::class.java)

                // Check which rules match
                for (rule in rules) {
                    if (!rule.enabled) continue

                    val matches = checkRuleMatch(rule, appName, message)
                    if (matches) {
                        // Create alert
                        val alert = Alert(
                            userId = userId,
                            senderApp = appName,
                            message = message,
                            priority = rule.priority,
                            escalationLevel = rule.escalationLevel,
                            status = "active"
                        )

                        // Save alert to Firestore
                        firestore.collection("users").document(userId)
                            .collection("alerts").add(alert).await()

                        // Execute escalation
                        executeEscalation(rule.escalationLevel, rule.dndOverride)
                    }
                }
            } catch (e: Exception) {
                Log.e("NotificationListener", "Error processing notification", e)
            }
        }
    }

    private fun checkRuleMatch(rule: AlertRule, appName: String, message: String): Boolean {
        var appMatch = rule.apps.isEmpty() || rule.apps.contains(appName)
        var keywordMatch = rule.keywords.isEmpty() || rule.keywords.any { 
            message.contains(it, ignoreCase = true) 
        }

        return if (rule.includeLogic) {
            appMatch && keywordMatch
        } else {
            !appMatch && !keywordMatch
        }
    }

    private fun executeEscalation(level: Int, dndOverride: Boolean) {
        // Level 1: Notification (already shown)
        if (level >= 2) {
            // Level 2: Vibration
            vibrator.vibrate(longArrayOf(0, 500, 200, 500), -1)
        }
        if (level >= 3) {
            // Level 3: Loud Alarm
            if (dndOverride) {
                audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
            }
            val volume = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM)
            audioManager.setStreamVolume(AudioManager.STREAM_ALARM, volume, 0)
        }
        if (level >= 4) {
            // Level 4: Full Screen Alert
            val intent = Intent(this, AlertActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TOP_TASK
            startActivity(intent)
        }
    }
}
