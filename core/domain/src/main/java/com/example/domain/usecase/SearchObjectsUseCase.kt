package com.example.domain.usecase

import com.example.domain.base.BaseUseCase
import com.example.domain.base.IoDispatcher
import com.example.domain.model.SearchObjects
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SearchObjectsUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repository: SearchRepository
) : BaseUseCase<String, SearchObjects>(dispatcher) {

    override suspend fun execute(params: String): SearchObjects {
        return repository.searchObjects(params)
    }

}