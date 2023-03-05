package com.example.feature_detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.common.AppConstants.keyId
import com.example.domain.model.ObjectDetail
import com.example.domain.usecase.GetObjectDetailUseCase
import com.example.feature_detail.detail.DetailAction.TryAgain
import com.example.feature_detail.detail.DetailViewModel
import com.example.testing.MainDispatcherRule
import com.example.testing.shouldBeEqualTo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    @Rule
    @JvmField
    val mainDispatcherRule = MainDispatcherRule()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var getObjectDetailUseCase: GetObjectDetailUseCase

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun whenObjectIdIsNotPassed_showUnknownError() {
        viewModel = createViewModel()

        viewModel.uiState.value.isUnknownError shouldBeEqualTo true
    }

    @Test
    fun whenObjectIdIsPassed_objectIdIsSet() {
        viewModel = createViewModel(5)

        viewModel.uiState.value.isUnknownError shouldBeEqualTo false
        viewModel.uiState.value.objectId shouldBeEqualTo 5
    }

    @Test
    fun whenObjectIdIsPassed_objectDetailIsReceived() {
        val testCase = ObjectDetail(
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
        coEvery { getObjectDetailUseCase(any()) } returns testCase
        viewModel = createViewModel(5)

        viewModel.uiState.value.objectDetail shouldBeEqualTo testCase

        coVerify(exactly = 1) {
            getObjectDetailUseCase(5)
        }
    }

    @Test
    fun onTryAgain_getObjectDetailIsCalledAgain() {
        viewModel = createViewModel(5)

        viewModel.submitAction(TryAgain)

        coVerify(exactly = 2) {
            getObjectDetailUseCase(5)
        }
    }

    private fun createViewModel(
        objectId: Int? = null
    ): DetailViewModel {
        val arguments = mutableMapOf(keyId to objectId)
        return DetailViewModel(
            SavedStateHandle(arguments),
            getObjectDetailUseCase
        )
    }
}