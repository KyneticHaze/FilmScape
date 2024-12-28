package com.furkanhrmnc.filmscape.ui.screen.similar

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.furkanhrmnc.filmscape.util.BaseViewModel
import com.furkanhrmnc.filmscape.util.MediaType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch

class SimilarMediasViewModel(
    id: Int,
    pager: SimilarMediasPager,
) : BaseViewModel() {

    var error = MutableStateFlow<Throwable?>(null)
        private set

    val similarMedias = pager.similarPagingDataFlow(
        type = MediaType.MOVIE.name.lowercase(),
        id = id
    )
        .catch { throwable -> handleError(throwable) }
        .cachedIn(viewModelScope)
}