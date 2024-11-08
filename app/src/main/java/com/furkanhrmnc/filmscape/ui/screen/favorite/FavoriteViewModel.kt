package com.furkanhrmnc.filmscape.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.util.Constants.FAVORITES
import com.furkanhrmnc.filmscape.util.Constants.MEDIA
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoriteViewModel(private val uid: String) : ViewModel() {

    private val firestore = Firebase.firestore

    private val _uiState = MutableStateFlow(FavoriteUiState())
    val uiState: StateFlow<FavoriteUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            loadFavorites(uid)
        }
    }

    private fun loadFavorites(uid: String) {
        _uiState.update { it.copy(isLoading = true) }
        firestore
            .collection(FAVORITES)
            .document(uid)
            .collection(MEDIA)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    _uiState.update { it.copy(error = error, isLoading = false) }
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val favorites = snapshot.documents.mapNotNull { it.toObject(Media::class.java) }
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            favorites = favorites
                        )
                    }
                }
            }
    }


    fun deleteMediaFromFavorites(mediaId: String) {
        firestore
            .collection(FAVORITES)
            .document(uid)
            .collection(MEDIA)
            .document(mediaId).delete()
            .addOnSuccessListener {
                // Delete
            }
            .addOnFailureListener { e ->
                _uiState.update { it.copy(error = e) }
            }
    }

    fun onError(error: Throwable) {
        _uiState.update { it.copy(error = error) }
    }

    fun onErrorConsumed() {
        _uiState.update { it.copy(error = null) }
    }
}

data class FavoriteUiState(
    val favorites: List<Media> = emptyList(),
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)