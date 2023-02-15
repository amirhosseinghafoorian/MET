package com.example.feature_search.search

sealed interface SearchAction {
    data class OnUpdateTextField(val text : String) : SearchAction
    data class OnObjectSelect(val id : Int) : SearchAction
}