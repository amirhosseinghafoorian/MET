package com.example.data.repository

import com.example.data.datasource.detail.DetailRemoteDataSource
import com.example.data.util.getOrThrow
import com.example.data.util.toObjectDetail
import com.example.domain.model.ObjectDetail
import com.example.domain.repository.DetailRepository
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val remoteDataSource: DetailRemoteDataSource
) : DetailRepository {

    override suspend fun getObjectDetail(id: Int): ObjectDetail {
        return remoteDataSource.getObjectDetail(id).getOrThrow().toObjectDetail()
    }

}