package com.example.healthbuddy   // <- if your project had a different package on this line before, keep that one

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    HealthBuddyScreen()
                }
            }
        }
    }
}

@Composable
fun HealthBuddyScreen() {
    // which page is active: "home" or "tips"
    var selectedTab by remember { mutableStateOf("home") }

    // ---- STATE (values that change) ----
    var waterMl by remember { mutableStateOf(0) }
    val waterGoal = 2000

    var steps by remember { mutableStateOf(0) }

    var mood by remember { mutableStateOf("🙂") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // -------- TITLE --------
        Text(
            text = "HealthBuddy",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        // -------- TABS (Dashboard / Tips) --------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { selectedTab = "home" },
                enabled = selectedTab != "home",
                modifier = Modifier.weight(1f)
            ) {
                Text("Dashboard")
            }
            Button(
                onClick = { selectedTab = "tips" },
                enabled = selectedTab != "tips",
                modifier = Modifier.weight(1f)
            ) {
                Text("Health tips")
            }
        }

        // -------- CONTENT: show one page at a time --------
        if (selectedTab == "home") {
            // ===== DASHBOARD: WATER + STEPS + MOOD =====

            // WATER CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Water tracker", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("Goal: $waterGoal ml / day")

                    val progress =
                        (waterMl.toFloat() / waterGoal.toFloat()).coerceIn(0f, 1f)

                    LinearProgressIndicator(
                        progress = progress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                    )

                    Text("Today: $waterMl ml")

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(onClick = {
                            waterMl += 250
                        }) {
                            Text("+250 ml")
                        }
                        Button(onClick = {
                            if (waterMl >= 250) waterMl -= 250
                        }) {
                            Text("-250 ml")
                        }
                    }
                }
            }

            // STEPS CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Steps today", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("Steps: $steps")

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(onClick = { steps += 500 }) {
                            Text("+500")
                        }
                        Button(onClick = { steps += 1000 }) {
                            Text("+1000")
                        }
                        Button(onClick = { steps = 0 }) {
                            Text("Reset")
                        }
                    }

                    val message = when {
                        steps >= 8000 -> "Amazing! You walked a lot today 🎉"
                        steps >= 4000 -> "Good job, keep going 💪"
                        else -> "Try to walk a bit more 🙂"
                    }
                    Text(message)
                }
            }

            // MOOD CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Mood today", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(
                        text = mood,
                        fontSize = 32.sp,
                        textAlign = TextAlign.Center
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(onClick = { mood = "😃" }) { Text("😃") }
                        Button(onClick = { mood = "🙂" }) { Text("🙂") }
                        Button(onClick = { mood = "😐" }) { Text("😐") }
                        Button(onClick = { mood = "😔" }) { Text("😔") }
                    }
                }
            }
        } else {
            // ===== HEALTH TIPS PAGE =====
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Daily health tips", fontWeight = FontWeight.Bold, fontSize = 18.sp)

                    Text("• Drink water regularly during the day, not all at once.")
                    Text("• Try to walk at least 30 minutes every day.")
                    Text("• Take short breaks when studying or working long hours.")
                    Text("• Avoid heavy food late at night, sleep 7–8 hours if possible.")
                    Text("• Stretch your neck, shoulders and back a few times a day.")
                    Text("• Spend a few minutes outside in fresh air every day.")
                }
            }
        }
    }
}
