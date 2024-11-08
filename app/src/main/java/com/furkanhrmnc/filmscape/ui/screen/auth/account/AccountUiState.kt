package com.furkanhrmnc.filmscape.ui.screen.auth.account

import android.graphics.Bitmap
import android.net.Uri

data class AccountUiState(
    val profileImageUri: Uri? = null,
    val profileBitmap: Bitmap? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)