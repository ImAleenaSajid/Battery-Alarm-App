package com.labs.battery.alarm.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.labs.battery.alarm.R
import com.labs.battery.alarm.viewmodel.SplashViewModel

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SplashViewModel = viewModel()
) {
    val context = LocalContext.current.applicationContext

    // NEW LOGIC — Solution A
    LaunchedEffect(true) {
        viewModel.startTimerAndDecideNext(context) { nextRoute ->
            navController.navigate(nextRoute) {
                popUpTo("Splash") { inclusive = true }
            }
        }
    }

    // ------------------ UI (unchanged except removed old logic) ------------------ //

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(250.dp))

            // TODO: Replace this dummy image with your actual app logo later
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "App Logo",
                modifier = Modifier
                    .height(220.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // TODO: Replace below text with your real application name
            Text(
                text = "Battery Alarm Application",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Loading, please wait...",
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
