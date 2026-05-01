package com.trackiq.messagealarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.trackiq.messagealarm.ui.screens.SplashScreen
import com.trackiq.messagealarm.ui.screens.LoginScreen
import com.trackiq.messagealarm.ui.screens.RegisterScreen
import com.trackiq.messagealarm.ui.screens.DashboardScreen
import com.trackiq.messagealarm.ui.screens.RuleBuilderScreen
import com.trackiq.messagealarm.ui.screens.SmartReplyScreen
import com.trackiq.messagealarm.ui.screens.ModesScreen
import com.trackiq.messagealarm.ui.screens.AnalyticsScreen
import com.trackiq.messagealarm.ui.screens.SettingsScreen
import com.trackiq.messagealarm.ui.theme.MessageAlarmProTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessageAlarmProTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    
                    NavHost(
                        navController = navController,
                        startDestination = "splash"
                    ) {
                        composable("splash") {
                            SplashScreen(navController)
                        }
                        composable("login") {
                            LoginScreen(navController)
                        }
                        composable("register") {
                            RegisterScreen(navController)
                        }
                        composable("dashboard") {
                            DashboardScreen(navController)
                        }
                        composable("rule_builder") {
                            RuleBuilderScreen(navController)
                        }
                        composable("smart_reply") {
                            SmartReplyScreen(navController)
                        }
                        composable("modes") {
                            ModesScreen(navController)
                        }
                        composable("analytics") {
                            AnalyticsScreen(navController)
                        }
                        composable("settings") {
                            SettingsScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
