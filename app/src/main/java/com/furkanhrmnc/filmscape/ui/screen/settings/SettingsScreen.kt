package com.furkanhrmnc.filmscape.ui.screen.settings

import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel(),
) {

    val dialogVisibly by viewModel.dialogVisibly.collectAsState()
    val isDynamic by viewModel.isDynamic.collectAsState()
    val themeTypeState by viewModel.themeType.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Appearance",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            )
        }
    ) { padding ->
        if (dialogVisibly) ThemeDialog(viewModel = viewModel, state = themeTypeState)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),

            ) {
            ThemeAndDynamicTile(model = viewModel, isDynamic = isDynamic)
        }
    }
}

@Composable
fun ThemeDialog(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel,
    state: ThemeTypeState,
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { viewModel.setDialogVisibility(false) },
        title = { Text(text = "Choose Theme") },
        text = { RadioButtons(viewModel = viewModel, state = state) },
        confirmButton = {
            Button(onClick = {
                viewModel.setDialogVisibility(false)
            }) {
                Text(text = "Confirm")
            }
        }
    )
}

@Composable
fun RadioButtons(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel,
    state: ThemeTypeState,
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
                Text(text = it.title)
            }
        }
    }
}

@Composable
fun ThemeAndDynamicTile(model: SettingsViewModel, isDynamic: Boolean) {
    Column {
        ListItem(
            headlineContent = {
                Text(
                    "Theme",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            },
            trailingContent = {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                    contentDescription = "Change App Theme"
                )
            },
            modifier = Modifier
                .clickable { model.setDialogVisibility(true) }
        )
        HorizontalDivider()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            ListItem(
                headlineContent = {
                    Text(
                        "Dynamic Color",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                trailingContent = {
                    Switch(
                        checked = (isDynamic),
                        onCheckedChange = { model.updateIsDynamicTheme() }
                    )
                },
                modifier = Modifier
                    .clickable { model.updateIsDynamicTheme() }
            )
    }
}