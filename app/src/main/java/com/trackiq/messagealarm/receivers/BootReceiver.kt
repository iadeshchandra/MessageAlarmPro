package com.trackiq.messagealarm.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.trackiq.messagealarm.services.AlertService

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d("BootReceiver", "Device booted, starting AlertService")
            
            // Start the AlertService
            val serviceIntent = Intent(context, AlertService::class.java)
            context?.startService(serviceIntent)
            
            // Start the NotificationListenerService
            // Note: NotificationListenerService needs to be enabled by user in settings
        }
    }
}
