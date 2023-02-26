package com.example.data.model

import androidx.annotation.Keep

@Keep
data class SearchObjectsResponse(
    val objectIDs: List<Int>,
    val total: Int
)