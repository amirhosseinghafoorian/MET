package com.example.data.util

import com.example.data.model.GetObjectDetailResponse
import com.example.data.model.SearchObjectsResponse
import com.example.domain.model.ObjectDetail
import com.example.domain.model.SearchObjects

fun SearchObjectsResponse.toSearchObjects(): SearchObjects {
    return SearchObjects(
        objectIDs = objectIDs
    )
}

fun GetObjectDetailResponse.toObjectDetail(): ObjectDetail {
    return ObjectDetail(
        name = objectName,
        imageUrl = primaryImage.ifEmpty { null },
        department = department,
        artistName = artistDisplayName,
        additionalImageUrls = additionalImages.ifEmpty { null },
        accessionNumber = accessionNumber,
        accessionYear = accessionYear,
        artistRole = artistRole,
        classification = classification,
        title = title,
        repository = repository
    )
}