package com.labs.battery.alarm.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.labs.battery.alarm.data.AppDataStore
import com.labs.battery.alarm.navigation.AppScreens
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    // simple timer helper; SplashScreen composable can collect this or call decideNextRoute after timer.
    fun startTimerAndDecideNext(context: Context, onResult: (nextRoute: String) -> Unit) {
        viewModelScope.launch {
            delay(3000L) // 3 seconds splash
            val languageSelected = AppDataStore.isLanguageSelectedFlow(context).first()
            val onboardingCompleted = AppDataStore.isOnboardingCompletedFlow(context).first()

            val route = when {
                !languageSelected -> {
                    // show language screen (from splash)
                    AppScreens.Language.name // default isFromSettings=false
                }
                !onboardingCompleted -> {
                    AppScreens.Onboarding.name
                }
                else -> {
                    AppScreens.Home.name
                }
            }
            onResult(route)
        }
    }
}
