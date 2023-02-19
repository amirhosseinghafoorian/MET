package com.example.domain.repository

import com.example.domain.model.ObjectDetail

interface DetailRepository {

    suspend fun getObjectDetail(id: Int): ObjectDetail

}