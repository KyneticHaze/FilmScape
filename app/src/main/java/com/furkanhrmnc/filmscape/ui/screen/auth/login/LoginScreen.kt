package com.furkanhrmnc.filmscape.ui.screen.auth.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.furkanhrmnc.filmscape.R
import com.furkanhrmnc.filmscape.navigation.Destinations
import com.furkanhrmnc.filmscape.ui.screen.auth.AuthTextField
import com.furkanhrmnc.filmscape.util.TestTags
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel(),
    navController: NavController,
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.successMessage, uiState.errorMessage) {
        uiState.successMessage.takeIf { it.isNotEmpty() }?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            navController.navigate(Destinations.HOME.route) {
                popUpTo(Destinations.LOGIN.route) { inclusive = true }
            }
            viewModel.clearSuccessMessage()
        }

        uiState.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearErrorMessage()
        }
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
                text = stringResource(R.string.login_text),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            AuthTextField(
                modifier = Modifier.testTag(TestTags.EDIT_TEXT_EMAIL),
                value = uiState.email,
                onValueChange = viewModel::updateEmail,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                label = { Text(text = stringResource(R.string.email_address_text)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = stringResource(id = R.string.email_address_text)
                    )
                },
                passwordVisible = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            AuthTextField(
                modifier = Modifier
                    .onFocusChanged { focusState -> viewModel.updateFocusedState(focusState.isFocused) }
                    .testTag(TestTags.EDIT_TEXT_PASSWORD),
                value = uiState.password,
                onValueChange = viewModel::updatePassword,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                label = { Text(text = stringResource(R.string.password_text)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = stringResource(id = R.string.password_text)
                    )
                },
                passwordVisible = uiState.isPasswordVisible,
                trailingIcon = {
                    if (uiState.isFocused) {
                        val icon = if (uiState.isPasswordVisible) {
                            Icons.Outlined.Visibility
                        } else {
                            Icons.Outlined.VisibilityOff
                        }
                        val description =
                            if (uiState.isPasswordVisible) stringResource(R.string.hide_password)
                            else stringResource(R.string.show_password)
                        IconButton(onClick = viewModel::togglePasswordVisibility) {
                            Icon(imageVector = icon, contentDescription = description)
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp),
                    onClick = { viewModel.signIn(uiState.email, uiState.password) },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = stringResource(id = R.string.login_text))
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp),
                    onClick = { navController.navigate(Destinations.REGISTER.route) },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = stringResource(R.string.register_text))
                }
            }
        }
    }
}