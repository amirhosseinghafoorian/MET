package com.example.feature_search.search

import androidx.lifecycle.viewModelScope
import com.example.feature_search.search.SearchAction.OnObjectSelect
import com.example.feature_search.search.SearchAction.OnUpdateTextField
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() :
    BaseViewModel<SearchAction, SearchUiState>(SearchUiState()) {

    // todo this should be replaced with effect
    private val _confirmFlow = MutableSharedFlow<Int>()
    val confirmFlow = _confirmFlow.asSharedFlow()

    init {
        updateState {
            copy(
                objectIds = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
            )
        }
    }

    override fun onAction(action: SearchAction) {
        when (action) {
            is OnObjectSelect -> {
                viewModelScope.launch {
                    _confirmFlow.emit(action.id)
                }
            }
            is OnUpdateTextField -> {
                uiState.value.searchFieldState.onChanged(action.text)
            }
        }
    }

}