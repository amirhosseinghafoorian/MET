package com.example.feature_search

import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import com.example.designsystem.theme.MetTheme
import com.example.feature_search.search.SearchAction
import com.example.feature_search.search.SearchScreen
import com.example.feature_search.search.SearchUiState
import com.example.testing.shouldBeEqualTo
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    private val resources = InstrumentationRegistry.getInstrumentation().targetContext.resources

    @Test
    fun searchFieldPlaceHolder_onStartUp_exists() {
        val uiState = SearchUiState()
        composeTestRule.setContent {
            MetTheme {
                SearchScreen(
                    focusManager = LocalFocusManager.current,
                    uiState = uiState,
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithText(
            resources.getString(R.string.label_search_place_holder)
        ).assertExists()
    }

    @Test
    fun searchFieldPlaceHolder_onTextInput_isHidden() {
        val uiState = SearchUiState()
        composeTestRule.setContent {
            MetTheme {
                SearchScreen(
                    focusManager = LocalFocusManager.current,
                    uiState = uiState,
                    onAction = {}
                )
            }
        }

        uiState.searchFieldState.onChanged("car")

        composeTestRule.onNodeWithText(
            resources.getString(R.string.label_search_place_holder)
        ).assertDoesNotExist()
    }

    @Test
    fun onUpdateTextFieldAction_onTextInput_isCalled() {
        val uiState = SearchUiState()
        var isActionCalled = false
        var actionText = ""

        composeTestRule.setContent {
            MetTheme {
                SearchScreen(
                    focusManager = LocalFocusManager.current,
                    uiState = uiState,
                    onAction = { action ->
                        if (action is SearchAction.OnUpdateTextField) {
                            isActionCalled = true
                            actionText = action.text
                        }
                    }
                )
            }
        }

        composeTestRule.onNodeWithText(
            resources.getString(R.string.label_search_place_holder)
        ).performTextInput("car")

        isActionCalled shouldBeEqualTo true
        actionText shouldBeEqualTo "car"
    }

    @Test
    fun searchFieldText_onTextInput_exists() {
        val uiState = SearchUiState()
        composeTestRule.setContent {
            MetTheme {
                SearchScreen(
                    focusManager = LocalFocusManager.current,
                    uiState = uiState,
                    onAction = {}
                )
            }
        }

        uiState.searchFieldState.onChanged("car")

        composeTestRule.onNodeWithText("car").assertExists()
    }

    @Test
    fun circularLoadingIndicator_onLoading_exists() {
        val uiState = SearchUiState(isSearchLoading = true)
        composeTestRule.setContent {
            MetTheme {
                SearchScreen(
                    focusManager = LocalFocusManager.current,
                    uiState = uiState,
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(resources.getString(R.string.tag_loading)).assertExists()
    }

    @Test
    fun listItems_onObjectIdsReceived_exist() {
        val uiState = SearchUiState(objectIds = listOf(1, 2, 3, 4))
        composeTestRule.setContent {
            MetTheme {
                SearchScreen(
                    focusManager = LocalFocusManager.current,
                    uiState = uiState,
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithText("1").assertExists()
        composeTestRule.onNodeWithText("2").assertExists()
        composeTestRule.onNodeWithText("3").assertExists()
        composeTestRule.onNodeWithText("4").assertExists()
    }

    @Test
    fun onObjectSelectAction_onItemSelect_isCalled() {
        val uiState = SearchUiState(objectIds = listOf(1, 2, 3, 4))
        var isActionCalled = false
        var actionId = -1

        composeTestRule.setContent {
            MetTheme {
                SearchScreen(
                    focusManager = LocalFocusManager.current,
                    uiState = uiState,
                    onAction = { action ->
                        if (action is SearchAction.OnObjectSelect) {
                            isActionCalled = true
                            actionId = action.id
                        }
                    }
                )
            }
        }

        composeTestRule.onNodeWithText("3").performClick()
        isActionCalled shouldBeEqualTo true
        actionId shouldBeEqualTo 3
    }

    @Test
    fun listItemsOutOfScreen_onObjectIdsReceived_isHidden() {
        var uiState = SearchUiState()

        val objectIds = mutableListOf<Int>()

        repeat(500) { id ->
            objectIds.add(id)
        }

        uiState = uiState.copy(objectIds = objectIds)

        composeTestRule.setContent {
            MetTheme {
                SearchScreen(
                    focusManager = LocalFocusManager.current,
                    uiState = uiState,
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithText("400").assertDoesNotExist()
    }

}