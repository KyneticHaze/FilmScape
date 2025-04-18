package com.furkanhrmnc.filmscape.ui.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.furkanhrmnc.filmscape.R
import com.furkanhrmnc.filmscape.util.Constants
import com.furkanhrmnc.filmscape.util.ThemeDialog
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel(),
) {

    val themeDialogVisibly by viewModel.themeDialogVisibly.collectAsState()
    val languageDialogVisibly by viewModel.languageDialogVisibly.collectAsState()
    val isDynamic by viewModel.isDynamic.collectAsState()
    val themeTypeState by viewModel.themeType.collectAsState()
    val selectedLanguage by viewModel.selectedLanguage.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.appearance),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            )
        }
    ) { padding ->
        if (themeDialogVisibly) ThemeDialog(viewModel = viewModel, state = themeTypeState)
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding),

            ) {
            SettingsTile(model = viewModel, isDynamic = isDynamic)
            LanguageSettingsDialog(
                viewModel = viewModel,
                isVisible = languageDialogVisibly,
                selectedLanguage = selectedLanguage
            )
        }
    }
}

@Composable
fun LanguageSettingsDialog(
    isVisible: Boolean,
    selectedLanguage: String,
    viewModel: SettingsViewModel,
) {
    if (isVisible) {
        AlertDialog(
            onDismissRequest = { viewModel.setLanguageDialogVisibility(false) },
            title = {
                Text(text = stringResource(id = R.string.language_settings))
            },
            text = {
                Column {
                    LanguageRadioButton(
                        language = Constants.TURKISH,
                        label = stringResource(id = R.string.language_turkish),
                        selectedLanguage = selectedLanguage,
                        onSelect = { viewModel.changeLanguage(Constants.TURKISH) }
                    )
                    LanguageRadioButton(
                        language = Constants.ENGLISH,
                        label = stringResource(id = R.string.language_english),
                        selectedLanguage = selectedLanguage,
                        onSelect = { viewModel.changeLanguage(Constants.ENGLISH) }
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.setLanguageDialogVisibility(false) }) {
                    Text(text = stringResource(id = R.string.confirm))
                }
            }
        )
    }
}

@Composable
fun LanguageRadioButton(
    language: String,
    label: String,
    selectedLanguage: String,
    onSelect: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = language == selectedLanguage,
            onClick = onSelect
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label)
    }
}
