package com.trackiq.messagealarm.data.models

import com.google.firebase.firestore.DocumentId
import java.util.Date

// User Profile
data class UserProfile(
    @DocumentId val id: String = "",
    val email: String = "",
    val name: String = "",
    val plan: String = "free", // free or pro
    val planExpiryDate: Date? = null,
    val deviceLimit: Int = 1,
    val apiKey: String = "",
    val apiProvider: String = "openai", // openai, gemini, claude, generic
    val dataMaskingEnabled: Boolean = false,
    val noStorageMode: Boolean = false,
    val notificationPreferences: NotificationPreferences = NotificationPreferences(),
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

// Notification Preferences
data class NotificationPreferences(
    val soundEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true,
    val volumeOverride: Boolean = false,
    val dndOverride: Boolean = false
)

// Alert Rule
data class AlertRule(
    @DocumentId val id: String = "",
    val userId: String = "",
    val name: String = "",
    val apps: List<String> = emptyList(),
    val contacts: List<String> = emptyList(),
    val keywords: List<String> = emptyList(),
    val includeLogic: Boolean = true,
    val priority: String = "medium", // low, medium, high, critical
    val enabled: Boolean = true,
    val escalationLevel: Int = 1, // 1-4
    val retryCount: Int = 3,
    val delaySeconds: Int = 0,
    val frequencyDetection: Boolean = false,
    val timeRulesEnabled: Boolean = false,
    val timeRulesStart: String = "00:00",
    val timeRulesEnd: String = "23:59",
    val timeRulesDays: List<Int> = (1..7).toList(),
    val dndOverride: Boolean = false,
    val repeatUntilDismissed: Boolean = false,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

// Alert
data class Alert(
    @DocumentId val id: String = "",
    val userId: String = "",
    val senderApp: String = "",
    val senderContact: String = "",
    val message: String = "",
    val priority: String = "medium",
    val status: String = "active", // active, dismissed, snoozed
    val escalationLevel: Int = 1,
    val retryCount: Int = 0,
    val createdAt: Date = Date(),
    val dismissedAt: Date? = null,
    val snoozedUntil: Date? = null
)

// Mode/Preset
data class Mode(
    @DocumentId val id: String = "",
    val userId: String = "",
    val name: String = "", // Freelancer, Trader, Family, Sleep, Business, Custom
    val type: String = "", // freelancer, trader, family, sleep, business, custom
    val active: Boolean = false,
    val soundEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true,
    val volumeOverride: Boolean = false,
    val dndOverride: Boolean = false,
    val escalationLevel: Int = 2,
    val notificationPreferences: NotificationPreferences = NotificationPreferences(),
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

// Template
data class Template(
    @DocumentId val id: String = "",
    val userId: String = "",
    val category: String = "", // Freelancer, Trader, Business, Ecommerce, Family, Student
    val title: String = "",
    val content: String = "",
    val tone: String = "professional", // friendly, professional, sales, support, formal
    val messageType: String = "client_reply", // client_reply, order_delivery, revision, follow_up, upsell, feedback
    val createdAt: Date = Date()
)

// Analytics
data class Analytics(
    @DocumentId val id: String = "",
    val userId: String = "",
    val totalAlerts: Int = 0,
    val activeAlerts: Int = 0,
    val dismissedAlerts: Int = 0,
    val snoozedAlerts: Int = 0,
    val appWiseStats: Map<String, Int> = emptyMap(),
    val contactWiseStats: Map<String, Int> = emptyMap(),
    val priorityStats: Map<String, Int> = emptyMap(),
    val date: Date = Date()
)

// Smart Reply Response
data class SmartReplyResponse(
    val originalMessage: String = "",
    val tone: String = "professional",
    val messageType: String = "client_reply",
    val generatedReply: String = "",
    val alternatives: List<String> = emptyList()
)
