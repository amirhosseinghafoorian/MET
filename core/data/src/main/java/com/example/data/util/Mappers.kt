package com.example.data.util

import com.example.data.model.SearchObjectsResponse
import com.example.domain.model.SearchObjects

fun SearchObjectsResponse.toSearchObjects() : SearchObjects {
    return SearchObjects(
        objectIDs = objectIDs
    )
}