package com.example.data.network

import com.example.data.model.SearchObjectsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("public/collection/v1/search")
    suspend fun searchObjects(
        @Query("q") query: String,
    ): Response<SearchObjectsResponse>

}