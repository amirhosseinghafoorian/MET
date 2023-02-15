package com.example.feature_search.search

import com.example.ui.text_field_util.BasicTextFieldState
import com.example.ui.text_field_util.TextFieldState

data class SearchUiState(
    val objectIds : List<Int> = listOf(),
    val searchFieldState : TextFieldState = BasicTextFieldState()
)
