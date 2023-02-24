package com.example.feature_detail

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.example.designsystem.theme.MetTheme
import com.example.domain.model.ObjectDetail
import com.example.feature_detail.detail.DetailAction
import com.example.feature_detail.detail.DetailScreen
import com.example.feature_detail.detail.DetailUiState
import com.example.testing.shouldBeEqualTo
import org.junit.Rule
import org.junit.Test

class DetailScreenTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    private val resources = InstrumentationRegistry.getInstrumentation().targetContext.resources

    @Test
    fun retryButton_onEmptyObjectDetail_exists() {
        val uiState = DetailUiState()
        composeTestRule.setContent {
            MetTheme {
                DetailScreen(
                    uiState = uiState,
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithText(
            resources.getString(R.string.label_internet_problem_massage)
        ).assertExists()
    }

    @Test
    fun tryAgainAction_onRetryButtonClick_isCalled() {
        val uiState = DetailUiState()
        var isActionCalled = false

        composeTestRule.setContent {
            MetTheme {
                DetailScreen(
                    uiState = uiState,
                    onAction = { action ->
                        if (action is DetailAction.TryAgain) {
                            isActionCalled = true
                        }
                    }
                )
            }
        }

        composeTestRule.onNodeWithText(
            resources.getString(R.string.label_internet_problem_massage)
        ).performClick()

        isActionCalled shouldBeEqualTo true
    }

    @Test
    fun retryButton_onNotEmptyObjectDetail_isHidden() {
        val uiState = DetailUiState(
            objectDetail = ObjectDetail(
                name = "some object",
                imageUrl = null,
                department = "some department",
                artistName = "Leonardo da Vinci",
                additionalImageUrls = null,
                accessionNumber = "1234",
                accessionYear = "1500",
                artistRole = "polymath",
                classification = "",
                title = "",
                repository = ""
            )
        )
        composeTestRule.setContent {
            MetTheme {
                DetailScreen(
                    uiState = uiState,
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithText(
            resources.getString(R.string.label_internet_problem_massage)
        ).assertDoesNotExist()
    }

    @Test
    fun objectName_onNotEmptyObjectName_exists() {
        val uiState = DetailUiState(
            objectDetail = ObjectDetail(
                name = "some object",
                imageUrl = null,
                department = "some department",
                artistName = "Leonardo da Vinci",
                additionalImageUrls = null,
                accessionNumber = "1234",
                accessionYear = "1500",
                artistRole = "polymath",
                classification = "",
                title = "",
                repository = ""
            )
        )
        composeTestRule.setContent {
            MetTheme {
                DetailScreen(
                    uiState = uiState,
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithText("some object").assertExists()
    }

    @Test
    fun headerSection_onNotEmptyHeader_exists() {
        val uiState = DetailUiState(
            objectDetail = ObjectDetail(
                name = "",
                imageUrl = "",
                department = "some department",
                artistName = "Leonardo da Vinci",
                additionalImageUrls = null,
                accessionNumber = "1234",
                accessionYear = "1500",
                artistRole = "polymath",
                classification = "",
                title = "",
                repository = ""
            )
        )
        composeTestRule.setContent {
            MetTheme {
                DetailScreen(
                    uiState = uiState,
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(
            resources.getString(R.string.tag_header)
        ).assertExists()
    }

    @Test
    fun headerSection_onEmptyHeader_isHidden() {
        val uiState = DetailUiState(
            objectDetail = ObjectDetail(
                name = "",
                imageUrl = null,
                department = "some department",
                artistName = "Leonardo da Vinci",
                additionalImageUrls = null,
                accessionNumber = "1234",
                accessionYear = "1500",
                artistRole = "polymath",
                classification = "",
                title = "",
                repository = ""
            )
        )
        composeTestRule.setContent {
            MetTheme {
                DetailScreen(
                    uiState = uiState,
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(
            resources.getString(R.string.tag_header)
        ).assertDoesNotExist()
    }

    @Test
    fun overallSection_onNotEmptyOverall_exists() {
        val uiState = DetailUiState(
            objectDetail = ObjectDetail(
                name = "some object",
                imageUrl = null,
                department = "some department",
                artistName = "",
                additionalImageUrls = null,
                accessionNumber = "1234",
                accessionYear = "1500",
                artistRole = "polymath",
                classification = "",
                title = "",
                repository = ""
            )
        )
        composeTestRule.setContent {
            MetTheme {
                DetailScreen(
                    uiState = uiState,
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(
            resources.getString(R.string.tag_overall)
        ).assertExists()
    }

    @Test
    fun overallSection_onEmptyOverall_isHidden() {
        val uiState = DetailUiState(
            objectDetail = ObjectDetail(
                name = "some object",
                imageUrl = null,
                department = "",
                artistName = "",
                additionalImageUrls = null,
                accessionNumber = "1234",
                accessionYear = "1500",
                artistRole = "polymath",
                classification = "",
                title = "",
                repository = ""
            )
        )
        composeTestRule.setContent {
            MetTheme {
                DetailScreen(
                    uiState = uiState,
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(
            resources.getString(R.string.tag_overall)
        ).assertDoesNotExist()
    }

    @Test
    fun descriptionFieldLabels_onNotEmptyDescriptionFields_exists() {
        val uiState = DetailUiState(
            objectDetail = ObjectDetail(
                name = "some object",
                imageUrl = null,
                department = "some department",
                artistName = "Leonardo da Vinci",
                additionalImageUrls = null,
                accessionNumber = "1234",
                accessionYear = "1500",
                artistRole = "polymath",
                classification = "some classification",
                title = "some title",
                repository = "some repo"
            )
        )
        composeTestRule.setContent {
            MetTheme {
                DetailScreen(
                    uiState = uiState,
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithText(resources.getString(R.string.label_accession_number))
            .assertExists()
        composeTestRule.onNodeWithText(resources.getString(R.string.label_accession_year))
            .assertExists()
        composeTestRule.onNodeWithText(resources.getString(R.string.label_artist_role))
            .assertExists()
        composeTestRule.onNodeWithText(resources.getString(R.string.label_classification))
            .assertExists()
        composeTestRule.onNodeWithText(resources.getString(R.string.label_title)).assertExists()
        composeTestRule.onNodeWithText(resources.getString(R.string.label_repository))
            .assertExists()

        composeTestRule.onNodeWithText("1234").assertExists()
        composeTestRule.onNodeWithText("1500").assertExists()
        composeTestRule.onNodeWithText("polymath").assertExists()
        composeTestRule.onNodeWithText("some classification").assertExists()
        composeTestRule.onNodeWithText("some title").assertExists()
        composeTestRule.onNodeWithText("some repo").assertExists()
    }

    @Test
    fun descriptionFieldLabels_onEmptyDescriptionFields_isHidden() {
        val uiState = DetailUiState(
            objectDetail = ObjectDetail(
                name = "some object",
                imageUrl = null,
                department = "some department",
                artistName = "Leonardo da Vinci",
                additionalImageUrls = null,
                accessionNumber = "",
                accessionYear = "",
                artistRole = "",
                classification = "",
                title = "",
                repository = ""
            )
        )
        composeTestRule.setContent {
            MetTheme {
                DetailScreen(
                    uiState = uiState,
                    onAction = {}
                )
            }
        }

        composeTestRule.onNodeWithText(resources.getString(R.string.label_accession_number))
            .assertDoesNotExist()
        composeTestRule.onNodeWithText(resources.getString(R.string.label_accession_year))
            .assertDoesNotExist()
        composeTestRule.onNodeWithText(resources.getString(R.string.label_artist_role))
            .assertDoesNotExist()
        composeTestRule.onNodeWithText(resources.getString(R.string.label_classification))
            .assertDoesNotExist()
        composeTestRule.onNodeWithText(resources.getString(R.string.label_title))
            .assertDoesNotExist()
        composeTestRule.onNodeWithText(resources.getString(R.string.label_repository))
            .assertDoesNotExist()
    }

}