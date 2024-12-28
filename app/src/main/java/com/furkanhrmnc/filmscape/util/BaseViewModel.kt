package com.furkanhrmnc.filmscape.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkanhrmnc.filmscape.util.Constants.UNKNOWN_ERROR
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    private val _uiEvent = MutableSharedFlow<UiEvent>()
    open val uiEvent = _uiEvent.asSharedFlow()

    protected fun handleError(throwable: Throwable) {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.ShowSnackbar(throwable.message ?: UNKNOWN_ERROR))
        }
    }

    protected fun handleToast(message: String) {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.Toast(message))
        }
    }
}