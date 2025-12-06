package com.labs.battery.alarm.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.labs.battery.alarm.navigation.AppScreens

@Composable
fun SettingsScreen(navController: NavHostController) {

    Column(modifier = Modifier.padding(20.dp)) {

        Text("Settings")

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                // navigate to language screen from Settings -> pass flag true
                navController.navigate(AppScreens.Language.name + "?isFromSettings=true")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Change Language")
        }
    }
}
