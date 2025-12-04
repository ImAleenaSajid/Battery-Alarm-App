package com.labs.battery.alarm.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.labs.battery.alarm.navigation.AppScreens

@Composable
fun HomeScreen(navController: NavHostController) {

    Column(modifier = Modifier.padding(20.dp)) {

        Text("Home Screen")

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { navController.navigate(AppScreens.Settings.name) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Settings")
        }
    }
}
