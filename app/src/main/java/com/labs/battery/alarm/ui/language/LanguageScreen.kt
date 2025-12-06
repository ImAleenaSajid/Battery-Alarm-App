package com.labs.battery.alarm.ui.language

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.labs.battery.alarm.navigation.AppScreens
import com.labs.battery.alarm.viewmodel.LanguageViewModel
import kotlinx.coroutines.launch

@Composable
fun LanguageScreen(
    navController: NavHostController,
    isFromSettings: Boolean = false,
    viewModel: LanguageViewModel = viewModel()
) {
    val context = LocalContext.current.applicationContext
    val scope = rememberCoroutineScope()

    LaunchedEffect(isFromSettings) {
        viewModel.initialize(context, isFromSettings)
    }

    val isLoading by viewModel.isLoading.collectAsState()
    val selected by viewModel.selectedLanguage.collectAsState()
    val languages = viewModel.languages

    // Pin selected language at top
    val displayLanguages = remember(selected, isFromSettings) {
        if (selected != null) {
            listOf(selected!!) + languages.filter { it != selected }
        } else {
            languages
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Single Select Language text with back arrow in settings
            Row(
                verticalAlignment = Alignment.Top, // align arrow to top
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp) // minimal padding
            ) {
                if (isFromSettings) {
                    // Big, bold arrow
                    Text(
                        text = "←",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 40.sp),
                        modifier = Modifier
                            .clickable { navController.popBackStack() }
                            .padding(end = 8.dp)
                    )

                    // Slightly lower Select Language text
                    Text(
                        text = "Select Language",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.offset(y = 21.dp))
                } else {
                    Text(
                        text = "Select Language",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .offset(y = 21.dp)    // moves the text down
                            .padding(bottom = 8.dp) // move text down
                    )
                }
            }


            Spacer(modifier = Modifier.height(12.dp))

            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    displayLanguages.forEach { lang ->
                        LanguageCard(
                            context = context,
                            label = lang,
                            selected = (lang == selected),
                            onSelect = { viewModel.selectLanguage(it) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Bottom buttons: only Save in settings, Next/Done in splash
                if (isFromSettings) {
                    Button(
                        onClick = {
                            scope.launch {
                                viewModel.saveSelection(context) {
                                    navController.popBackStack()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Save")
                    }
                } else {
                    Button(
                        onClick = {
                            scope.launch {
                                viewModel.saveSelection(context) {
                                    navController.navigate(AppScreens.Onboarding.name) {
                                        popUpTo(AppScreens.Splash.name) { inclusive = true }
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Done")
                    }
                }
            }
        }
    }
}

@Composable
private fun LanguageCard(
    context: Context,
    label: String,
    selected: Boolean,
    onSelect: (String) -> Unit
) {
    val drawableName = labelToDrawableName(label)

    val drawableId = remember(drawableName) {
        context.resources.getIdentifier(drawableName, "drawable", context.packageName)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect(label) },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (drawableId != 0) {
                Image(
                    painter = painterResource(id = drawableId),
                    contentDescription = "$label flag",
                    modifier = Modifier.size(48.dp)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
            )

            Spacer(modifier = Modifier.weight(1f))

            RadioButton(selected = selected, onClick = { onSelect(label) })
        }
    }
}

@Composable
fun labelToDrawableName(label: String): String {
    return label
        .lowercase()
        .replace(" ", "_")
        .replace("(", "")
        .replace(")", "")
}
