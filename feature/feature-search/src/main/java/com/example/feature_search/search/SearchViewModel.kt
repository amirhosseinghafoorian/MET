package com.example.feature_search.search

import androidx.lifecycle.viewModelScope
import com.example.domain.repository.SearchRepository
import com.example.feature_search.search.SearchAction.OnObjectSelect
import com.example.feature_search.search.SearchAction.OnUpdateTextField
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : BaseViewModel<SearchAction, SearchUiState>(SearchUiState()) {

    // todo this should be replaced with effect
    private val _confirmFlow = MutableSharedFlow<Int>()
    val confirmFlow = _confirmFlow.asSharedFlow()

    private var searchJob: Job? = null

    override fun onAction(action: SearchAction) {
        when (action) {
            is OnObjectSelect -> {
                viewModelScope.launch {
                    _confirmFlow.emit(action.id)
                }
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
            suspendJob = { searchJob = it }
        )
    }

}