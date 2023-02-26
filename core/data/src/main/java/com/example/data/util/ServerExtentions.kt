package com.example.data.util

import com.example.common.AppConstants.NULL_RESPONSE_ERROR_MESSAGE
import retrofit2.Response

fun <T> Response<T>.getOrThrow() : T {
    if (!isSuccessful) {
        throw Exception(errorBody().toString())
    } else {
        body()?.let {
            return it
        } ?: run {
            throw Exception(NULL_RESPONSE_ERROR_MESSAGE)
        }
    }
}
