package com.furkanhrmnc.filmscape.ui.screen.auth.account

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.io.IOException
import java.io.File
import java.io.FileOutputStream

class AccountViewModel : ViewModel() {
    private val _uiState = mutableStateOf(AccountUiState())
    val uiState: State<AccountUiState> = _uiState

    private fun saveProfileImage(context: Context, userId: String, imageUri: Uri): Boolean {
        _uiState.value = _uiState.value.copy(isLoading = true)
        return try {
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.createSource(context.contentResolver, imageUri).let { source ->
                    ImageDecoder.decodeBitmap(source)
                }
            } else {
                MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
            }

            val file = File(context.filesDir, "$userId-profile.jpg")

            FileOutputStream(file).use { output ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
            }

            _uiState.value = _uiState.value.copy(
                profileImageUri = imageUri,
                profileBitmap = bitmap,
                isLoading = false
            )
            true
        } catch (e: IOException) {
            e.printStackTrace()
            _uiState.value = _uiState.value.copy(isLoading = false, error = e)
            false
        }
    }

    fun loadProfileImage(context: Context, userId: String): Uri? {
        val file = File(context.filesDir, "$userId-profile.jpg")
        return if (file.exists()) {
            Uri.fromFile(file)
        } else {
            null
        }
    }

    fun deleteProfileImage(context: Context, userId: String): Boolean {
        _uiState.value = _uiState.value.copy(isLoading = true)

        return try {
            val file = File(context.filesDir, "$userId-profile.jpg")
            if (file.exists()) {
                file.delete()
                _uiState.value = _uiState.value.copy(
                    profileImageUri = null,
                    profileBitmap = null,
                    isLoading = false
                )
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            _uiState.value = _uiState.value.copy(isLoading = false, error = e)
            false
        }
    }

    fun onImageSelected(context: Context, userId: String, imageUri: Uri) {
        saveProfileImage(context, userId, imageUri)
    }
}