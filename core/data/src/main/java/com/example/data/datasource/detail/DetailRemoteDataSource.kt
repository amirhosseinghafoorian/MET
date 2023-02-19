package com.example.data.datasource.detail

import com.example.data.model.GetObjectDetailResponse
import retrofit2.Response

interface DetailRemoteDataSource {

    suspend fun getObjectDetail(id : Int) : Response<GetObjectDetailResponse>

}