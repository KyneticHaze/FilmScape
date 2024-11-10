package com.furkanhrmnc.filmscape.data.repository

import com.furkanhrmnc.filmscape.domain.repository.AuthRepository
import com.furkanhrmnc.filmscape.util.Response
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
) : AuthRepository {
    override fun loginUser(email: String, password: String): Flow<Response<AuthResult>> = flow {
        try {
            val response = auth.signInWithEmailAndPassword(email, password).await()
            emit(value = Response.Success(data = response))
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }

    override fun registerUser(email: String, password: String): Flow<Response<AuthResult>> = flow {
        try {
            val response = auth.createUserWithEmailAndPassword(email, password).await()
            emit(Response.Success(response))
        } catch (e: FirebaseAuthUserCollisionException) {
            emit(Response.Failure(e)) // Kullanıcı zaten kayıtlı
        } catch (e: FirebaseAuthWeakPasswordException) {
            emit(Response.Failure(e)) // Zayıf şifre
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            emit(Response.Failure(e)) // Geçersiz kimlik bilgileri
        } catch (e: Exception) {
            emit(Response.Failure(e)) // Diğer hatalar
        }
    }
}