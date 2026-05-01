package com.trackiq.messagealarm.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.trackiq.messagealarm.data.models.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    // Auth Operations
    suspend fun registerUser(email: String, password: String): Result<String> = try {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val userId = result.user?.uid ?: throw Exception("User creation failed")
        
        // Create user profile
        val userProfile = UserProfile(
            id = userId,
            email = email,
            name = email.substringBefore("@")
        )
        firestore.collection("users").document(userId).set(userProfile).await()
        Result.success(userId)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun loginUser(email: String, password: String): Result<String> = try {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        Result.success(result.user?.uid ?: throw Exception("Login failed"))
    } catch (e: Exception) {
        Result.failure(e)
    }

    fun logoutUser() {
        auth.signOut()
    }

    fun getCurrentUserId(): String? = auth.currentUser?.uid

    // User Profile Operations
    suspend fun getUserProfile(userId: String): Result<UserProfile> = try {
        val doc = firestore.collection("users").document(userId).get().await()
        val profile = doc.toObject(UserProfile::class.java) ?: throw Exception("Profile not found")
        Result.success(profile)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun updateUserProfile(userId: String, profile: UserProfile): Result<Unit> = try {
        firestore.collection("users").document(userId).set(profile).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    // Alert Rules Operations
    suspend fun createAlertRule(userId: String, rule: AlertRule): Result<String> = try {
        val docRef = firestore.collection("users").document(userId)
            .collection("rules").add(rule.copy(userId = userId)).await()
        Result.success(docRef.id)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun getAlertRules(userId: String): Result<List<AlertRule>> = try {
        val snapshot = firestore.collection("users").document(userId)
            .collection("rules").get().await()
        val rules = snapshot.toObjects(AlertRule::class.java)
        Result.success(rules)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun updateAlertRule(userId: String, ruleId: String, rule: AlertRule): Result<Unit> = try {
        firestore.collection("users").document(userId)
            .collection("rules").document(ruleId).set(rule).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun deleteAlertRule(userId: String, ruleId: String): Result<Unit> = try {
        firestore.collection("users").document(userId)
            .collection("rules").document(ruleId).delete().await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    // Alerts Operations
    suspend fun createAlert(userId: String, alert: Alert): Result<String> = try {
        val docRef = firestore.collection("users").document(userId)
            .collection("alerts").add(alert.copy(userId = userId)).await()
        Result.success(docRef.id)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun getAlerts(userId: String): Result<List<Alert>> = try {
        val snapshot = firestore.collection("users").document(userId)
            .collection("alerts").orderBy("createdAt").get().await()
        val alerts = snapshot.toObjects(Alert::class.java)
        Result.success(alerts)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun updateAlert(userId: String, alertId: String, alert: Alert): Result<Unit> = try {
        firestore.collection("users").document(userId)
            .collection("alerts").document(alertId).set(alert).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    // Modes Operations
    suspend fun createMode(userId: String, mode: Mode): Result<String> = try {
        val docRef = firestore.collection("users").document(userId)
            .collection("modes").add(mode.copy(userId = userId)).await()
        Result.success(docRef.id)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun getModes(userId: String): Result<List<Mode>> = try {
        val snapshot = firestore.collection("users").document(userId)
            .collection("modes").get().await()
        val modes = snapshot.toObjects(Mode::class.java)
        Result.success(modes)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun updateMode(userId: String, modeId: String, mode: Mode): Result<Unit> = try {
        firestore.collection("users").document(userId)
            .collection("modes").document(modeId).set(mode).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    // Templates Operations
    suspend fun getTemplates(userId: String, category: String): Result<List<Template>> = try {
        val snapshot = firestore.collection("users").document(userId)
            .collection("templates")
            .whereEqualTo("category", category)
            .get().await()
        val templates = snapshot.toObjects(Template::class.java)
        Result.success(templates)
    } catch (e: Exception) {
        Result.failure(e)
    }

    // Analytics Operations
    suspend fun getAnalytics(userId: String): Result<Analytics> = try {
        val snapshot = firestore.collection("users").document(userId)
            .collection("analytics").orderBy("date").limit(1).get().await()
        val analytics = snapshot.toObjects(Analytics::class.java).firstOrNull()
            ?: Analytics(userId = userId)
        Result.success(analytics)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
