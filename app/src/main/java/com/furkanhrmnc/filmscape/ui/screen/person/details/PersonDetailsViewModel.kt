package com.furkanhrmnc.filmscape.ui.screen.person.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkanhrmnc.filmscape.domain.model.PersonDetail
import com.furkanhrmnc.filmscape.domain.repository.MediaRepository
import com.furkanhrmnc.filmscape.util.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PersonDetailsViewModel(
    private val id: Int,
    private val repo: MediaRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PersonDetailsUiState())
    val uiState: StateFlow<PersonDetailsUiState> = _uiState.asStateFlow()


    init {
        getPersonDetails()
    }

    private fun getPersonDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repo.getPersonDetails(id)
                .collect { result ->
                    _uiState.update {
                        when (result) {
                            is Response.Failure -> it.copy(
                                error = result.throwable,
                                isLoading = false
                            )

                            is Response.Success -> it.copy(
                                personDetail = result.data,
                                isLoading = false
                            )
                        }
                    }
                }
        }
    }
}

data class PersonDetailsUiState(
    val error: Throwable? = null,
    val personDetail: PersonDetail? = null,
    val isLoading: Boolean = false,
)
