package com.example.feature_detail.detail

sealed interface DetailAction {
    data class ShowPicture(val url: String) : DetailAction
    object TryAgain: DetailAction
}