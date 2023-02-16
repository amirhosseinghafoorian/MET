package com.example.data.datasource.search

import com.example.data.model.SearchObjectsResponse
import com.example.data.network.SearchApi
import retrofit2.Response
import javax.inject.Inject

class SearchRemoteDataSourceImpl @Inject constructor(
    private val api: SearchApi
) : SearchRemoteDataSource {

    override suspend fun searchObjects(query: String): Response<SearchObjectsResponse> {
        return api.searchObjects(query)
    }

}