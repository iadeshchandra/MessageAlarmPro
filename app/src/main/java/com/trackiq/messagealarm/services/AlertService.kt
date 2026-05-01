package com.trackiq.messagealarm.services

import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.os.Binder
import android.os.IBinder
import android.os.Vibrator
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.trackiq.messagealarm.data.models.Alert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class AlertService : Service() {

    private val firestore = FirebaseFirestore.getInstance()
    private val scope = CoroutineScope(Dispatchers.Main)
    private val binder = LocalBinder()
    private lateinit var audioManager: AudioManager
    private lateinit var vibrator: Vibrator

    inner class LocalBinder : Binder() {
        fun getService(): AlertService = this@AlertService
    }

    override fun onCreate() {
        super.onCreate()
        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        Log.d("AlertService", "Service created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("AlertService", "Service started")
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder = binder

    fun dismissAlert(userId: String, alertId: String) {
        scope.launch {
            try {
                firestore.collection("users").document(userId)
                    .collection("alerts").document(alertId)
                    .update("status", "dismissed", "dismissedAt", Date()).await()
                Log.d("AlertService", "Alert dismissed: $alertId")
            } catch (e: Exception) {
                Log.e("AlertService", "Error dismissing alert", e)
            }
        }
    }

    fun snoozeAlert(userId: String, alertId: String, minutes: Int) {
        scope.launch {
            try {
                val snoozeUntil = Date(System.currentTimeMillis() + minutes * 60 * 1000)
                firestore.collection("users").document(userId)
                    .collection("alerts").document(alertId)
                    .update("status", "snoozed", "snoozedUntil", snoozeUntil).await()
                Log.d("AlertService", "Alert snoozed: $alertId for $minutes minutes")
            } catch (e: Exception) {
                Log.e("AlertService", "Error snoozing alert", e)
            }
        }
    }

    fun playAlertSound(priority: String) {
        val volume = when (priority) {
            "critical" -> audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM)
            "high" -> (audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM) * 0.8).toInt()
            else -> (audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM) * 0.5).toInt()
        }
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, volume, 0)
    }

    fun triggerVibration(pattern: LongArray = longArrayOf(0, 500, 200, 500)) {
        vibrator.vibrate(pattern)
    }

    fun overrideDND() {
        audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("AlertService", "Service destroyed")
    }
}
