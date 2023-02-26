package com.example.feature_detail.detail

import com.example.domain.model.ObjectDetail

data class DetailUiState(
    val objectId : Int? = null,
    val objectDetail: ObjectDetail? = null,
    val serverError: String? = null,
    val isObjectDetailLoading: Boolean = false,
    val isUnknownError : Boolean = false,
)
