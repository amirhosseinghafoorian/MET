package com.example.data.repository

import com.example.data.datasource.search.SearchRemoteDataSource
import com.example.data.util.getOrThrow
import com.example.data.util.toSearchObjects
import com.example.domain.model.SearchObjects
import com.example.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: SearchRemoteDataSource
) : SearchRepository {

    override suspend fun searchObjects(query: String): SearchObjects {
        return remoteDataSource.searchObjects(query).getOrThrow().toSearchObjects()
    }

}