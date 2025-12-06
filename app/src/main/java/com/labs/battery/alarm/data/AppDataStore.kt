package com.labs.battery.alarm.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATASTORE_NAME = "app_prefs"
private val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

object AppDataStore {
    private val KEY_SELECTED_LANGUAGE = stringPreferencesKey("selected_language_code")
    private val KEY_SELECTED_LANGUAGE_LABEL = stringPreferencesKey("selected_language_label")
    private val KEY_LANGUAGE_SELECTED_FLAG = booleanPreferencesKey("language_selected_flag")
    private val KEY_ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")

    // getters (Flows)
    fun selectedLanguageCodeFlow(context: Context): Flow<String?> =
        context.dataStore.data.map { prefs -> prefs[KEY_SELECTED_LANGUAGE] }

    fun selectedLanguageLabelFlow(context: Context): Flow<String?> =
        context.dataStore.data.map { prefs -> prefs[KEY_SELECTED_LANGUAGE_LABEL] }

    fun isLanguageSelectedFlow(context: Context): Flow<Boolean> =
        context.dataStore.data.map { prefs -> prefs[KEY_LANGUAGE_SELECTED_FLAG] ?: false }

    fun isOnboardingCompletedFlow(context: Context): Flow<Boolean> =
        context.dataStore.data.map { prefs -> prefs[KEY_ONBOARDING_COMPLETED] ?: false }

    // setters
    suspend fun setSelectedLanguage(context: Context, languageCode: String, languageLabel: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_SELECTED_LANGUAGE] = languageCode
            prefs[KEY_SELECTED_LANGUAGE_LABEL] = languageLabel
            prefs[KEY_LANGUAGE_SELECTED_FLAG] = true
        }
    }

    suspend fun setLanguageSelectedFlag(context: Context, value: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[KEY_LANGUAGE_SELECTED_FLAG] = value
        }
    }

    suspend fun setOnboardingCompleted(context: Context, value: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[KEY_ONBOARDING_COMPLETED] = value
        }
    }

    // small helper to clear (optional)
    suspend fun clearAll(context: Context) {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
