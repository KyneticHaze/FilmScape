package com.furkanhrmnc.filmscape.ui.screen.similar

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.furkanhrmnc.filmscape.domain.repository.MediaRepository
import com.furkanhrmnc.filmscape.util.Constants.PER_PAGE_COUNT

class SimilarMoviesPager(
    movieId: Int,
    type: String,
    repo: MediaRepository,
    config: PagingConfig = PagingConfig(pageSize = PER_PAGE_COUNT),
) {
    val similarPagingDataFlow = Pager(config = config) {
        SimilarMoviesDataSource(
            type = type,
            movieId = movieId,
            repo = repo
        )
    }.flow
}