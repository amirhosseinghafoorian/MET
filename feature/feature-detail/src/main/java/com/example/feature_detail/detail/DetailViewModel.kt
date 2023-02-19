package com.example.feature_detail.detail

import androidx.lifecycle.SavedStateHandle
import com.example.domain.repository.DetailRepository
import com.example.feature_detail.navigation.keyId
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: DetailRepository
) : BaseViewModel<DetailAction, DetailUiState>(
    DetailUiState(
        objectId = savedStateHandle.get<Int>(keyId)
    )
) {

    init {
        uiState.value.objectId?.let { id ->
            getObjectDetail(id)
        } ?: run {
            updateState { copy(isUnknownError = true) }
        }
    }

    override fun onAction(action: DetailAction) {
        TODO("Not yet implemented")
    }

    private fun getObjectDetail(id: Int) {
        makeSuspendCall(
            block = {
                repository.getObjectDetail(id)
            },
            onSuccess = { result ->
                updateState { copy(objectDetail = result) }
            },
            onLoading = { isLoading ->
                updateState { copy(isObjectDetailLoading = isLoading) }
            }
        )
    }
}
