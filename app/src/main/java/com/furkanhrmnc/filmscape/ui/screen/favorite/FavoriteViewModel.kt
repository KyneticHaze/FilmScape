package com.furkanhrmnc.filmscape.ui.screen.favorite

import androidx.lifecycle.viewModelScope
import com.furkanhrmnc.filmscape.domain.model.Media
import com.furkanhrmnc.filmscape.util.BaseViewModel
import com.furkanhrmnc.filmscape.util.Constants.FAVORITES
import com.furkanhrmnc.filmscape.util.Constants.MEDIA
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoriteViewModel(private val uid: String) : BaseViewModel() {

    private val firestore = Firebase.firestore

    private val _uiState = MutableStateFlow(FavoriteUiState())
    val uiState: StateFlow<FavoriteUiState> = _uiState.asStateFlow()

    override val uiEvent = super.uiEvent

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
                    handleError(error)
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


    fun deleteMediaFromFavorites(mediaId: String, deleteString: String) {
        firestore
            .collection(FAVORITES)
            .document(uid)
            .collection(MEDIA)
            .document(mediaId).delete()
            .addOnSuccessListener {
                handleToast(deleteString)
            }
            .addOnFailureListener { e ->
                handleError(e)
            }
    }
}