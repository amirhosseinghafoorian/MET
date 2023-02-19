package com.example.data.network

import com.example.data.model.GetObjectDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailApi {

    @GET("public/collection/v1/objects/{objectID}")
    suspend fun getObjectDetail(
        @Path("objectID") id: Int,
    ): Response<GetObjectDetailResponse>

}