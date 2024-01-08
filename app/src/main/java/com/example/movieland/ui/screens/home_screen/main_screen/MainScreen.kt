package com.example.movieland.ui.screens.home_screen.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.movieland.ui.navigation.Routes
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun MainScreen(
    navController: NavController
) {

    val auth = Firebase.auth
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(text = "Main Screen")
            Button(onClick = {
                auth.signOut()
                navController.navigate(Routes.Auth.route) {
                    popUpTo(Routes.Home.route) {
                        inclusive = true
                    }
                }
            }) {
                Text(text = "Sign Out")
            }
        }
    }
}