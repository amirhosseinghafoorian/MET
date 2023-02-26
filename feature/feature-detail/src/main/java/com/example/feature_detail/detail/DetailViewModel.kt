package com.example.feature_detail.detail

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import com.example.common.AppConstants.keyId
import com.example.domain.usecase.GetObjectDetailUseCase
import com.example.feature_detail.detail.DetailAction.ShowPicture
import com.example.feature_detail.detail.DetailAction.TryAgain
import com.example.feature_detail.navigation.keyImage
import com.example.ui.base.BaseEffect
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getObjectDetailUseCase: GetObjectDetailUseCase,
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
                val navigateBundle = Bundle()
                navigateBundle.putString(keyImage, action.url)

                sendEffect(BaseEffect.Navigate(navigateBundle))
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
                getObjectDetailUseCase(id)
            },
            onSuccess = { result ->
                updateState { copy(objectDetail = result) }
            },
            onLoading = { isLoading ->
                updateState { copy(isObjectDetailLoading = isLoading) }
            },
            onError = { exception ->
                updateState {
                    copy(serverError = exception.message)
                }
            }
        )
    }
}