package com.example.feature_detail.detail

interface DetailAction {
    data class ShowPicture(val url: String) : DetailAction
    object TryAgain: DetailAction
}