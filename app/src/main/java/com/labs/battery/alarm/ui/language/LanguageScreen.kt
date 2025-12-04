package com.labs.battery.alarm.ui.language

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.labs.battery.alarm.navigation.AppScreens

@Composable
fun LanguageScreen(navController: NavHostController) {

    var selected by remember { mutableStateOf("English") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Select Language", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(20.dp))

        listOf("English", "Urdu", "Arabic").forEach { lang ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selected = lang }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = selected == lang, onClick = { selected = lang })
                Text(lang, modifier = Modifier.padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                navController.navigate(AppScreens.Onboarding.name)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next")
        }
    }
}
