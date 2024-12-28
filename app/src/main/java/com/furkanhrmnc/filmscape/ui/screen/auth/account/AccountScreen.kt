package com.furkanhrmnc.filmscape.ui.screen.auth.account

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.furkanhrmnc.filmscape.R
import com.furkanhrmnc.filmscape.util.Constants
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountScreen(
    context: Context,
    viewModel: AccountViewModel = koinViewModel(),
    exit: () -> Unit,
) {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    val uiState = viewModel.uiState.value

    LaunchedEffect(key1 = user?.uid) {
        user?.uid?.let {
            val savedProfileUri = viewModel.loadProfileImage(context, it)
            if (savedProfileUri != null) {
                viewModel.onImageSelected(context, it, savedProfileUri)
            }
        }
    }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            uri?.let { uri2 ->
                user?.let {
                    viewModel.onImageSelected(context, user.uid, uri2)
                    Toast.makeText(
                        context,
                        context.getString(R.string.photo_selected),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } ?: run {
                Toast.makeText(
                    context,
                    context.getString(R.string.photo_unselected),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 24.dp)
                .align(Alignment.TopCenter)
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable { imagePickerLauncher.launch(Constants.IMAGE_STORAGE_PATH) }
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else if (uiState.profileBitmap != null) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        bitmap = uiState.profileBitmap.asImageBitmap(),
                        contentDescription = stringResource(R.string.profile_image),
                        contentScale = ContentScale.Crop,
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = stringResource(R.string.default_photo),
                        tint = Color.White,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Text(
                text = stringResource(R.string.account_info),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
            user?.let { user ->
                Text(
                    text = "${stringResource(id = R.string.email_address_text)}: ${user.email}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            uiState.error?.let {
                Text(
                    text = "${stringResource(id = R.string.error_text)}: ${it.localizedMessage}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Button(
                onClick = exit,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(text = stringResource(R.string.exit))
            }
            Button(
                onClick = {
                    user?.uid?.let {
                        val isDeleted = viewModel.deleteProfileImage(context, it)
                        if (!isDeleted) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.photo_undeleted),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            Toast.makeText(
                                context,
                                context.getString(R.string.photo_deleted),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(text = stringResource(R.string.photo_delete))
            }
        }
    }
}