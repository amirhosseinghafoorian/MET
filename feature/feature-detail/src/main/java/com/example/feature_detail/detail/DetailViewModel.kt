package com.example.feature_detail.detail

import androidx.lifecycle.SavedStateHandle
import com.example.domain.repository.DetailRepository
import com.example.feature_detail.detail.DetailAction.ShowPicture
import com.example.feature_detail.detail.DetailAction.TryAgain
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
        initializeDetail()
    }

    override fun onAction(action: DetailAction) {
        when (action) {
            TryAgain -> {
                initializeDetail()
            }
            is ShowPicture -> {
//                todo call navigate effect
            }
        }
    }

    private fun initializeDetail() {
        uiState.value.objectId?.let { id ->
            getObjectDetail(id)
        } ?: run {
            updateState { copy(isUnknownError = true) }
        }
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
