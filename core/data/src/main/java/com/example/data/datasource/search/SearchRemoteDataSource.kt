package com.example.data.datasource.search

import com.example.data.model.SearchObjectsResponse
import retrofit2.Response

interface SearchRemoteDataSource {

    suspend fun searchObjects(query: String) : Response<SearchObjectsResponse>

}