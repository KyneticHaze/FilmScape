package com.furkanhrmnc.filmscape.util

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.furkanhrmnc.filmscape.R
import com.furkanhrmnc.filmscape.ui.screen.settings.SettingsUiState
import com.furkanhrmnc.filmscape.ui.screen.settings.SettingsViewModel

@Composable
fun ThemeDialog(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel,
    state: SettingsUiState,
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { viewModel.setThemeDialogVisibility(false) },
        title = { Text(text = stringResource(R.string.choose_theme)) },
        text = { RadioButtons(viewModel = viewModel, state = state) },
        confirmButton = {
            Button(onClick = {
                viewModel.setThemeDialogVisibility(false)
            }) {
                Text(text = stringResource(R.string.confirm))
            }
        }
    )
}