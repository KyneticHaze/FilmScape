package com.example.movieland.ui.screens.auth_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _signIn = mutableStateOf(false)
    val signIn : State<Boolean>
        get() = _signIn

    private val _signUp = mutableStateOf(false)
    val signUp : State<Boolean>
        get() = _signUp

    private val _progress = mutableStateOf(false)
    val progress : State<Boolean>
        get() = _progress

    val popUpNotification = mutableStateOf<AuthEvent<String>?>(null)


    fun register(email: String, password: String) {
        _progress.value = true
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful)  {
                    _signUp.value = true
                    handleException(it.exception, "Sign Up Successful")
                } else {
                    handleException(it.exception, "Sign Up Failed")
                }
                _progress.value = false
            }
    }

    fun login(email: String, password: String) {
        _progress.value = true
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful)  {
                    _signIn.value = true
                    handleException(it.exception, "Login Successful")
                } else {
                    handleException(it.exception, "Login Failed")
                }
                _progress.value = false
            }
    }

    private fun handleException(exception: Exception? = null, customMessage : String = "") {
        exception?.printStackTrace()
        val errorMessage = exception?.localizedMessage ?: ""
        val message = if (customMessage.isEmpty()) errorMessage else "$customMessage:  $errorMessage"
        popUpNotification.value = AuthEvent(message)
    }
}