package com.furkanhrmnc.filmscape.ui.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun RadioButtons(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel,
    state: SettingsUiState,
) {
    Column(modifier = modifier) {
        state.radioItems.forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (it.value == state.selectedRadio),
                        onClick = { viewModel.updateThemeType(it.value) }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (it.value == state.selectedRadio),
                    onClick = { viewModel.updateThemeType(it.value) }
                )
                Text(text = stringResource(id = it.titleResId))
            }
        }
    }
}