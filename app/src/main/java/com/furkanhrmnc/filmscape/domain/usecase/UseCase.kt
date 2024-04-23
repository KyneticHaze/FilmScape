package com.furkanhrmnc.filmscape.domain.usecase

import com.furkanhrmnc.filmscape.util.Resource
import kotlinx.coroutines.flow.Flow

interface UseCase<in Param, out Output> {
    operator fun invoke(param: Param): Flow<Resource<Output>>
}