package com.labs.battery.alarm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.labs.battery.alarm.ui.home.HomeScreen
import com.labs.battery.alarm.ui.language.LanguageScreen
import com.labs.battery.alarm.ui.onboarding.OnboardingScreen
import com.labs.battery.alarm.ui.settings.SettingsScreen
import com.labs.battery.alarm.ui.splash.SplashScreen

@Composable
fun BatteryAlarmApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.Splash.name
    ) {
        composable(AppScreens.Splash.name) { SplashScreen(navController) }

        // language screen with optional boolean arg (default false)
        composable(
            route = AppScreens.Language.name + "?isFromSettings={isFromSettings}",
            arguments = listOf(
                navArgument("isFromSettings") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { backStackEntry ->
            val isFromSettings = backStackEntry.arguments?.getBoolean("isFromSettings") ?: false
            LanguageScreen(navController = navController, isFromSettings = isFromSettings)
        }

        composable(AppScreens.Onboarding.name) { OnboardingScreen(navController) }
        composable(AppScreens.Home.name) { HomeScreen(navController) }
        composable(AppScreens.Settings.name) { SettingsScreen(navController) }
    }
}
