package com.furkanhrmnc.filmscape.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.furkanhrmnc.filmscape.navigation.Destinations
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: UserSignInViewModel = koinViewModel(),
    navController: NavController,
) {
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            AuthTextField(
                value = email,
                onValueChange = { email = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                label = { Text(text = "Email Address") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            AuthTextField(
                value = password,
                onValueChange = { password = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                label = { Text(text = "Password") },
                applyVisualTransformation = true
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        viewModel.signInUser(email = email, password = password)
                        navController.navigate(Destinations.MAIN.route) {
                            popUpTo(Destinations.LOGIN.route) { inclusive = true }
                        }
                    },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Login")
                }
                Spacer(modifier = Modifier.width(12.dp))
                Button(
                    onClick = { navController.navigate(Destinations.REGISTER.route) },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Register")
                }
            }
        }
    }
}