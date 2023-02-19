package com.example.data.datasource.detail

import com.example.data.model.GetObjectDetailResponse
import com.example.data.network.DetailApi
import retrofit2.Response
import javax.inject.Inject

class DetailRemoteDataSourceImpl @Inject constructor(
    private val api: DetailApi
) : DetailRemoteDataSource {

    override suspend fun getObjectDetail(id: Int): Response<GetObjectDetailResponse> {
        return api.getObjectDetail(id)
    }

}