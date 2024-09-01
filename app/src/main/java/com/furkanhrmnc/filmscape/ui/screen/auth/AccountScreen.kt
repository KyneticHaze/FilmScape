package com.furkanhrmnc.filmscape.ui.screen.auth

import android.accounts.Account
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AccountScreen(modifier: Modifier = Modifier, signOut: () -> Unit) {
    Box(modifier = modifier
        .fillMaxSize()
        .padding(top = 120.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize(), horizontalAlignment =  Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            Box(modifier = Modifier
                .size(250.dp)
                .clip(CircleShape)
                .background(Color(0xFF3F51B5))
            )
            AccountInfo(key = "Email", value = "nice34@hotmail.com")
            AccountInfo(key = "Password", value = "******************")
            ElevatedButton(modifier = Modifier.size(width = 200.dp, height = 50.dp),onClick = { signOut() },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.elevatedButtonColors(containerColor = Color(0xFFF44336),
                    contentColor = Color(0xFFFFFFFF)
                )

            ) {
                Text(text = "Sign Out")

            }
        }
    }
   
}

@Composable
fun AccountInfo(modifier: Modifier = Modifier, key: String, value: String) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = key, style = MaterialTheme.typography.h4)
        Text(text = value, style = MaterialTheme.typography.body1)
    }
}