package com.furkanhrmnc.filmscape.data.repository

import com.furkanhrmnc.filmscape.domain.repository.AuthRepository
import com.furkanhrmnc.filmscape.util.Response
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
) : AuthRepository {
    override fun loginUser(email: String, password: String): Flow<Response<AuthResult>> = flow {
        val response = auth.signInWithEmailAndPassword(email, password).await()
        emit(value = Response.Success(data = response))
    }

    override fun registerUser(email: String, password: String): Flow<Response<AuthResult>> = flow {
        val response = auth.createUserWithEmailAndPassword(email, password).await()
        emit(value = Response.Success(data = response))
    }
}