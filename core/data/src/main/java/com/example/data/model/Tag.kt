package com.example.data.model

import androidx.annotation.Keep

@Keep
data class Tag(
    val AAT_URL: String,
    val Wikidata_URL: String,
    val term: String
)