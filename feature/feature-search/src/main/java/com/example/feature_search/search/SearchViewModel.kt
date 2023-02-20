package com.example.feature_search.search

import android.os.Bundle
import com.example.common.AppConstants.keyId
import com.example.domain.repository.SearchRepository
import com.example.feature_search.search.SearchAction.OnObjectSelect
import com.example.feature_search.search.SearchAction.OnUpdateTextField
import com.example.ui.base.BaseEffect.Navigate
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : BaseViewModel<SearchAction, SearchUiState>(SearchUiState()) {

    private var searchJob: Job? = null

    override fun onAction(action: SearchAction) {
        when (action) {
            is OnObjectSelect -> {
                val navigateBundle = Bundle()
                navigateBundle.putInt(keyId, action.id)

                sendEffect(Navigate(navigateBundle))
            }
            is OnUpdateTextField -> {
                updateTextField(action.text)
            }
        }
    }

    private fun updateTextField(text: String) {
        uiState.value.searchFieldState.onChanged(text)

        searchJob?.cancel()
        if (uiState.value.searchFieldState.validate()) {
            searchObjects(text)
        } else {
            updateState {
                copy(
                    isSearchLoading = false,
                    objectIds = listOf()
                )
            }
        }
    }

    private fun searchObjects(query: String) {
        makeSuspendCall(
            block = {
                delay(500)
                repository.searchObjects(query)
            },
            onSuccess = { result ->
                updateState {
                    copy(objectIds = result.objectIDs)
                }
            },
            suspendJob = { searchJob = it },
            onLoading = { isLoading ->
                updateState { copy(isSearchLoading = isLoading) }
            }
        )
    }

}