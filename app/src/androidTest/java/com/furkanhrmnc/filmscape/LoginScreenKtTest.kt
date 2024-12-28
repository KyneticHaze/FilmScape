package com.furkanhrmnc.filmscape

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavController
import com.furkanhrmnc.filmscape.ui.screen.auth.AuthUiState
import com.furkanhrmnc.filmscape.ui.screen.auth.login.LoginScreen
import com.furkanhrmnc.filmscape.ui.screen.auth.login.LoginViewModel
import com.furkanhrmnc.filmscape.util.TestTags
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class LoginScreenKtTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val navController: NavController = mock()
    private val viewModel: LoginViewModel = mock()

    @Test
    fun test_Login_Screen_UI_And_Navigation() {

        val uiState = AuthUiState(
            email = "test@example.com",
            password = "password123",
            isPasswordVisible = false,
            isFocused = false,
            successMessage = "Login Successful",
            errorMessage = null
        )

        val state = MutableStateFlow(uiState).also { it.asStateFlow() }

        whenever(viewModel.uiState).thenReturn(state)

        composeRule.setContent {
            LoginScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composeRule.onNodeWithTag(TestTags.EDIT_TEXT_EMAIL).performTextInput("test@example.com")
        composeRule.onNodeWithTag(TestTags.EDIT_TEXT_PASSWORD).performTextInput("password123")
    }
}