package com.labs.battery.alarm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.labs.battery.alarm.ui.splash.SplashScreen
import com.labs.battery.alarm.ui.language.LanguageScreen
import com.labs.battery.alarm.ui.onboarding.OnboardingScreen
import com.labs.battery.alarm.ui.home.HomeScreen
import com.labs.battery.alarm.ui.settings.SettingsScreen

@Composable
fun BatteryAlarmApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.Splash.name
    ) {
        composable(AppScreens.Splash.name) { SplashScreen(navController) }
        composable(AppScreens.Language.name) { LanguageScreen(navController) }
        composable(AppScreens.Onboarding.name) { OnboardingScreen(navController) }
        composable(AppScreens.Home.name) { HomeScreen(navController) }
        composable(AppScreens.Settings.name) { SettingsScreen(navController) }
    }
}
