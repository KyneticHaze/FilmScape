package com.furkanhrmnc.filmscape.ui.screen.person

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch

class PersonViewModel(pager: PersonsPager) : ViewModel() {

    var error = MutableStateFlow<Throwable?>(null)
        private set

    val popularPersons = pager.personsPagingFlow
        .catch { throwable -> onError(throwable) }
        .cachedIn(viewModelScope)

    fun onError(throwable: Throwable) {
        error.value = throwable
    }

    fun onErrorConsumed() {
        error.value = null
    }
}