package com.furkanhrmnc.filmscape.ui.screen.settings

import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.furkanhrmnc.filmscape.R

@Composable
fun SettingsTile(model: SettingsViewModel, isDynamic: Boolean) {
    Column {
        ListItem(
            headlineContent = {
                Text(
                    text = stringResource(R.string.theme_text),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            },
            trailingContent = {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.change_app_theme_text)
                )
            },
            modifier = Modifier.clickable { model.setThemeDialogVisibility(true) }
        )
        HorizontalDivider()
        ListItem(
            headlineContent = {
                Text(
                    text = stringResource(R.string.change_language),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            },
            trailingContent = {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                    contentDescription = stringResource(id = R.string.change_language)
                )
            },
            modifier = Modifier.clickable { model.setLanguageDialogVisibility(true) }
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            ListItem(
                headlineContent = {
                    Text(
                        text = stringResource(R.string.dynamic_color_text),
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
                modifier = Modifier.clickable { model.updateIsDynamicTheme() }
            )
    }
}