package com.example.domain.repository

import com.example.domain.model.SearchObjects

interface SearchRepository {

    suspend fun searchObjects(query: String) : SearchObjects

}