package com.trackiq.messagealarm.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun DashboardScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF6366F1))
                .padding(24.dp)
        ) {
            Column {
                Text(
                    "Message Alarm Pro",
                    fontSize = 24.sp,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    "Free Plan",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

        // Stats Cards
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard("5", "Active Alerts", Color(0xFFEF4444), Modifier.weight(1f))
            StatCard("23", "Total", Color(0xFF3B82F6), Modifier.weight(1f))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard("18", "Rules", Color(0xFF10B981), Modifier.weight(1f))
            StatCard("6", "Modes", Color(0xFFF59E0B), Modifier.weight(1f))
        }

        // Quick Actions
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickActionButton(
                "Create Rule",
                Icons.Filled.Add,
                Color(0xFF6366F1),
                onClick = { navController.navigate("rule_builder") }
            )
            QuickActionButton(
                "Smart Reply",
                Icons.Filled.Edit,
                Color(0xFF10B981),
                onClick = { navController.navigate("smart_reply") }
            )
            QuickActionButton(
                "View Analytics",
                Icons.Filled.BarChart,
                Color(0xFF3B82F6),
                onClick = { navController.navigate("analytics") }
            )
        }

        // Navigation Buttons
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NavigationButton("Modes", Icons.Filled.Settings) {
                navController.navigate("modes")
            }
            NavigationButton("Settings", Icons.Filled.Info) {
                navController.navigate("settings")
            }
        }
    }
}

@Composable
fun RuleBuilderScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        TopAppBar(navController, "Create Rule")
        
        Text(
            "Multi-Condition Rule Builder",
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        
        // Rule builder form
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Rule Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )

        Text("Select Apps:", fontSize = 14.sp, modifier = Modifier.padding(top = 12.dp))
        // TODO: Add app selection checkboxes

        Text("Select Contacts:", fontSize = 14.sp, modifier = Modifier.padding(top = 12.dp))
        // TODO: Add contact selection

        Text("Keywords:", fontSize = 14.sp, modifier = Modifier.padding(top = 12.dp))
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Enter keywords (comma separated)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Text("Save Rule")
        }
    }
}

@Composable
fun SmartReplyScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TopAppBar(navController, "Smart Reply")

        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Original Message") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(bottom = 12.dp),
            maxLines = 5
        )

        Text("Tone:", fontSize = 14.sp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Friendly", "Professional", "Sales", "Support", "Formal").forEach { tone ->
                AssistChip(
                    onClick = {},
                    label = { Text(tone) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Text("Message Type:", fontSize = 14.sp, modifier = Modifier.padding(top = 12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Reply", "Delivery", "Revision").forEach { type ->
                AssistChip(
                    onClick = {},
                    label = { Text(type) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Generate Reply")
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(
                "Generated reply will appear here...",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun ModesScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TopAppBar(navController, "Modes")

        listOf("Freelancer", "Trader", "Family", "Sleep", "Business", "Custom").forEach { mode ->
            ModeCard(mode)
        }
    }
}

@Composable
fun AnalyticsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TopAppBar(navController, "Analytics")

        Text("Alert Statistics", fontSize = 18.sp, modifier = Modifier.padding(bottom = 12.dp))
        
        StatCard("150", "Total Alerts", Color(0xFF3B82F6), Modifier.fillMaxWidth())
        StatCard("5", "Active", Color(0xFFEF4444), Modifier.fillMaxWidth())
        StatCard("100", "Dismissed", Color(0xFF10B981), Modifier.fillMaxWidth())
    }
}

@Composable
fun SettingsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TopAppBar(navController, "Settings")

        Text("Account", fontSize = 16.sp, modifier = Modifier.padding(vertical = 12.dp))
        SettingItem("Email", "user@example.com")
        SettingItem("Plan", "Free Plan")
        SettingItem("Device Limit", "1/1")

        Divider(modifier = Modifier.padding(vertical = 12.dp))

        Text("Privacy", fontSize = 16.sp, modifier = Modifier.padding(vertical = 12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Data Masking")
            Switch(checked = false, onCheckedChange = {})
        }

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Text("Upgrade to Pro")
        }
    }
}

// Helper Composables
@Composable
fun StatCard(value: String, label: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(bottom = 12.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, fontSize = 24.sp, color = Color.White)
            Text(label, fontSize = 12.sp, color = Color.White.copy(alpha = 0.8f))
        }
    }
}

@Composable
fun QuickActionButton(
    label: String,
    icon: androidx.compose.material.icons.Icons.Filled,
    color: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Icon(icon, contentDescription = label, tint = Color.White, modifier = Modifier.padding(end = 8.dp))
        Text(label, color = Color.White)
    }
}

@Composable
fun NavigationButton(label: String, icon: androidx.compose.material.icons.Icons.Filled, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3F4F6))
    ) {
        Icon(icon, contentDescription = label, tint = Color.Black)
        Text(label, color = Color.Black, modifier = Modifier.padding(start = 8.dp))
    }
}

@Composable
fun TopAppBar(navController: NavController, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
        }
        Text(title, fontSize = 20.sp, modifier = Modifier.padding(start = 8.dp))
    }
}

@Composable
fun ModeCard(modeName: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(modeName, fontSize = 16.sp)
            Switch(checked = false, onCheckedChange = {})
        }
    }
}

@Composable
fun SettingItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 14.sp)
        Text(value, fontSize = 14.sp, color = Color.Gray)
    }
}
