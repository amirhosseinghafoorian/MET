package com.example.domain.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class BaseUseCase<in Param, out Result>(private val dispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(params: Param): Result {
        return withContext(dispatcher) {
            execute(params)
        }
    }

    protected abstract suspend fun execute(params: Param): Result
}