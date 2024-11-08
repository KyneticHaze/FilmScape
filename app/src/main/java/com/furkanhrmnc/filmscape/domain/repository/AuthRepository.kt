package com.furkanhrmnc.filmscape.domain.repository

import com.furkanhrmnc.filmscape.util.Response
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun loginUser(email: String, password: String): Flow<Response<AuthResult>>
    fun registerUser(email: String, password: String): Flow<Response<AuthResult>>
}