package com.furkanhrmnc.filmscape.ui.screen.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: UserSignUpViewModel = koinViewModel(),
    navController: NavController,
) {
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Register",
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
            Button(
                onClick = {
                    viewModel.registerUser(email = email, password = password)
                    navController.navigate(Destinations.LOGIN.route) {
                        popUpTo(Destinations.REGISTER.route) { inclusive = true }
                    }
                },
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Register")
            }
        }
    }
}