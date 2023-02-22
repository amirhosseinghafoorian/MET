package com.example.domain.usecase

import com.example.domain.base.BaseUseCase
import com.example.domain.base.IoDispatcher
import com.example.domain.model.ObjectDetail
import com.example.domain.repository.DetailRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetObjectDetailUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repository: DetailRepository
) : BaseUseCase<Int, ObjectDetail>(dispatcher) {

    override suspend fun execute(params: Int): ObjectDetail {
        return repository.getObjectDetail(params)
    }

}