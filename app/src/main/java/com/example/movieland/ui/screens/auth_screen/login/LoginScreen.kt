package com.example.movieland.ui.screens.auth_screen.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieland.R
import com.example.movieland.ui.navigation.Routes
import com.example.movieland.ui.navigation.Screens
import com.example.movieland.ui.screens.auth_screen.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController, viewModel: AuthViewModel = hiltViewModel()
) {

    val signIn = viewModel.signIn.value
    val progress = viewModel.progress.value
    val notifyState = viewModel.popUpNotification.value
    val notifyMessage = notifyState?.getContentOrNull()
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val empty by remember { mutableStateOf("") }
    var errorPassLength by remember { mutableStateOf(false) }

    Surface(
        color = MaterialTheme.colorScheme.surface, modifier = Modifier.fillMaxSize(1f)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (progress) {
                CircularProgressIndicator()
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Welcome Movie Land",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.W600
            )
            Spacer(modifier = Modifier.height(22.dp))
            Image(
                painter = painterResource(id = R.drawable.login),
                contentDescription = "Login",
                modifier = Modifier.size(250.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column {
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onSurface,
                            shape = MaterialTheme.shapes.medium
                        ),
                    shape = MaterialTheme.shapes.medium,
                    label = { Text(text = "Enter Email") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "Email")
                    },
                    trailingIcon = {
                        if (email.isNotEmpty()) {
                            Icon(painter = painterResource(id = R.drawable.close),
                                contentDescription = "Clear",
                                modifier = Modifier.clickable {
                                    email = empty
                                })
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                    ),
                    maxLines = 1,
                    singleLine = true
                )
                TextField(
                    value = password,
                    onValueChange = {
                        password = it
                        errorPassLength = it.length < 8
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onSurface,
                            shape = MaterialTheme.shapes.medium
                        ),
                    shape = MaterialTheme.shapes.medium,
                    label = { Text(text = "Enter Password") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = "Password")
                    },
                    trailingIcon = {
                        val visibilityIcon = if (passwordVisibility) {
                            painterResource(id = R.drawable.visible)
                        } else {
                            painterResource(id = R.drawable.nonvisible)
                        }
                        if (password.isNotEmpty()) {
                            Icon(painter = visibilityIcon,
                                contentDescription = if (passwordVisibility) "Hide Password" else "Show Password",
                                modifier = Modifier.clickable {
                                    passwordVisibility = !passwordVisibility
                                })
                        }
                    },
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                    ),
                    maxLines = 1,
                    singleLine = true
                )
                if (errorPassLength) {
                    Text(
                        text = "Password must be 8 characters",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        viewModel.login(email, password)
                    }
                    if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(context, "Enter Email or password!", Toast.LENGTH_SHORT).show()
                    }
                    if (notifyMessage != null) {
                        Toast.makeText(context, notifyMessage, Toast.LENGTH_SHORT).show()
                    }
                },
                shape = RoundedCornerShape(20),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(end = 20.dp, start = 20.dp)
            ) {
                Text(text = "Login", style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(text = "Don't you have an account?")
                Text(
                    text = "Sign Up", modifier = Modifier.clickable {
                        navController.navigate(Screens.Register.screen)
                    }, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.W600
                )
            }

            if (signIn) {
                navController.navigate(Routes.Home.route) {
                    popUpTo(Routes.Auth.route) {
                        inclusive = true
                    }
                }
            }
        }
    }
}