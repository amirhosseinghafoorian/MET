package com.example.domain.model

data class ObjectDetail(
    val name : String,
    val imageUrl : String?,
    val department : String,
    val artistName : String,
    val additionalImageUrls : List<String>?,
    val accessionNumber : String,
    val accessionYear : String,
    val artistRole : String,
    val classification : String,
    val title : String,
    val repository : String,
)