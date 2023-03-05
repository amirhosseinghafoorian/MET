package com.example.feature_search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.model.SearchObjects
import com.example.domain.usecase.SearchObjectsUseCase
import com.example.feature_search.search.SearchAction.OnUpdateTextField
import com.example.feature_search.search.SearchViewModel
import com.example.testing.MainDispatcherRule
import com.example.testing.shouldBeEqualTo
import io.mockk.Called
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    @Rule
    @JvmField
    val mainDispatcherRule = MainDispatcherRule()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var searchObjectsUseCase: SearchObjectsUseCase

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun whenTextFieldIsInValid_objectIdsAreEmpty() = runTest {
        viewModel = createViewModel()

        viewModel.uiState.value.objectIds shouldBeEqualTo listOf()
        viewModel.uiState.value.searchFieldState.text shouldBeEqualTo ""

        viewModel.submitAction(OnUpdateTextField(""))

        viewModel.uiState.value.searchFieldState.text shouldBeEqualTo ""

        delay(300)

        viewModel.uiState.value.isSearchLoading shouldBeEqualTo false

        delay(300)

        viewModel.uiState.value.objectIds shouldBeEqualTo listOf()
        viewModel.uiState.value.isSearchLoading shouldBeEqualTo false

        coVerify {
            searchObjectsUseCase wasNot Called
        }
    }

    @Test
    fun whenTextFieldIsValid_objectIdsAreEmptyBeforeDelay() {
        coEvery { searchObjectsUseCase(any()) } returns SearchObjects(listOf(1, 2, 3))
        viewModel = createViewModel()

        viewModel.uiState.value.objectIds shouldBeEqualTo listOf()
        viewModel.uiState.value.searchFieldState.text shouldBeEqualTo ""

        viewModel.submitAction(OnUpdateTextField("flower"))

        viewModel.uiState.value.searchFieldState.text shouldBeEqualTo "flower"

        viewModel.uiState.value.objectIds shouldBeEqualTo listOf()
        viewModel.uiState.value.isSearchLoading shouldBeEqualTo true

        coVerify {
            searchObjectsUseCase wasNot Called
        }
    }

    @Test
    fun whenTextFieldIsValid_objectIdsAreReceived() = runTest {
        coEvery { searchObjectsUseCase(any()) } returns SearchObjects(listOf(1, 2, 3))
        viewModel = createViewModel()

        viewModel.uiState.value.objectIds shouldBeEqualTo listOf()
        viewModel.uiState.value.searchFieldState.text shouldBeEqualTo ""

        viewModel.submitAction(OnUpdateTextField("flower"))

        viewModel.uiState.value.searchFieldState.text shouldBeEqualTo "flower"

        delay(300)

        viewModel.uiState.value.isSearchLoading shouldBeEqualTo true

        delay(300)

        viewModel.uiState.value.objectIds shouldBeEqualTo listOf(1, 2, 3)
        viewModel.uiState.value.isSearchLoading shouldBeEqualTo false

        coVerify(exactly = 1) {
            searchObjectsUseCase("flower")
        }
    }

    private fun createViewModel(): SearchViewModel {
        return SearchViewModel(searchObjectsUseCase)
    }
}