package com.labs.battery.alarm.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.labs.battery.alarm.data.AppDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Locale

class LanguageViewModel : ViewModel() {

    // The exact list and order you requested:
    val languages = listOf(
        "English",
        "Spanish",
        "Japanese",
        "French",
        "Russian",
        "Arabic",
        "Hindi",
        "Indonesian",
        "Italian",
        "Malay",
        "Polish",
        "Portuguese",
        "Swedish",
        "Turkish",
        "Chinese",
        "Croatian",
        "Czech",
        "Danish",
        "Vietnamese",
        "German",
        "Hebrew",
        "Hungarian",
        "Korean",
        "Norwegian",
        "Persian (Farsi)",
        "Thai",
        "Urdu",
        "Zulu"
    )

    // Map labels -> locale codes to compare with device language
    // These codes are common ISO language codes; adjust if you prefer different variants.
    private val languageToLocale = mapOf(
        "English" to "en",
        "Spanish" to "es",
        "Japanese" to "ja",
        "French" to "fr",
        "Russian" to "ru",
        "Arabic" to "ar",
        "Hindi" to "hi",
        "Indonesian" to "id",
        "Italian" to "it",
        "Malay" to "ms",
        "Polish" to "pl",
        "Portuguese" to "pt",
        "Swedish" to "sv",
        "Turkish" to "tr",
        "Chinese" to "zh",
        "Croatian" to "hr",
        "Czech" to "cs",
        "Danish" to "da",
        "Vietnamese" to "vi",
        "German" to "de",
        "Hebrew" to "he",
        "Hungarian" to "hu",
        "Korean" to "ko",
        "Norwegian" to "no",
        "Persian" to "fa",
        "Thai" to "th",
        "Urdu" to "ur",
        "Zulu" to "zu"
    )

    // UI state flows
    private val _selectedLanguage = MutableStateFlow<String?>(null)
    val selectedLanguage: StateFlow<String?> = _selectedLanguage.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    /**
     * Initialize the view model for the Language screen.
     *
     * @param context applicationContext required for DataStore lookups.
     * @param isFromSettings whether the screen was opened from Settings (true) or from Splash flow (false)
     */
    fun initialize(context: Context, isFromSettings: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true

            if (isFromSettings) {
                // load previously saved language (if any); otherwise fallback to device language
                val savedLabel = AppDataStore.selectedLanguageLabelFlow(context).first()
                if (!savedLabel.isNullOrBlank() && languages.contains(savedLabel)) {
                    _selectedLanguage.value = savedLabel
                } else {
                    _selectedLanguage.value = detectDeviceLanguageLabel(context)
                }
            } else {
                // opened from splash: choose device language (and move it to top in UI logic done in composable)
                _selectedLanguage.value = detectDeviceLanguageLabel(context)
            }

            _isLoading.value = false
        }
    }

    /** Set selection in VM (UI calls this when user taps) */
    fun selectLanguage(label: String) {
        _selectedLanguage.value = label
    }

    /**
     * Persist selection to DataStore.
     * Saves both label and locale code.
     */
    fun saveSelection(context: Context, onComplete: (() -> Unit)? = null) {
        viewModelScope.launch {
            val label = _selectedLanguage.value ?: detectDeviceLanguageLabel(context)
            val code = languageToLocale[label] ?: Locale.getDefault().language
            AppDataStore.setSelectedLanguage(context, code, label ?: "")
            onComplete?.invoke()
        }
    }

    /**
     * Helper to detect best-matching language label from the device locale.
     */
    private fun detectDeviceLanguageLabel(context: Context): String {
        val deviceLocale = Locale.getDefault().language.lowercase(Locale.getDefault())

        // Try direct match via map
        val match = languageToLocale.entries.firstOrNull { it.value.lowercase() == deviceLocale }?.key
        if (match != null && languages.contains(match)) return match

        // Some languages might be 'zh' vs 'zh-CN' etc - fallback to language display name matching
        val deviceDisplay = Locale.getDefault().displayLanguage
        // Try to find a label that roughly matches display name (case-insensitive contains)
        val fuzzy = languages.firstOrNull { it.equals(deviceDisplay, ignoreCase = true) || it.contains(deviceDisplay, ignoreCase = true) }
        if (fuzzy != null) return fuzzy

        // final fallback: English
        return "English"
    }
}
