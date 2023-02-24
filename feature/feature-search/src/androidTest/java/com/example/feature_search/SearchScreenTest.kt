package com.example.feature_search

import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.example.designsystem.theme.MetTheme
import com.example.feature_search.search.SearchScreen
import com.example.feature_search.search.SearchUiState
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    private val resources = InstrumentationRegistry.getInstrumentation().targetContext.resources

    @Test
    fun myTest(){
        composeTestRule.setContent {
            MetTheme {
                SearchScreen(
                    focusManager = LocalFocusManager.current,
                    uiState = SearchUiState()
                ) {}
            }
        }

        composeTestRule.onNodeWithText(
            resources.getString(R.string.label_search_place_holder)
        ).assertExists()
    }

}