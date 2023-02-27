package com.example.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.model.ObjectDetail
import com.example.domain.repository.DetailRepository
import com.example.domain.usecase.GetObjectDetailUseCase
import com.example.testing.MainDispatcherRule
import com.example.testing.shouldBeEqualTo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetObjectDetailUseCaseTest {

    @Rule
    @JvmField
    val mainDispatcherRule = MainDispatcherRule()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var detailRepository: DetailRepository

    private lateinit var useCase: GetObjectDetailUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun onExecute_objectDetailIsReceived() = runTest {
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
        coEvery { detailRepository.getObjectDetail(any()) } returns testCase

        useCase = GetObjectDetailUseCase(
            dispatcher = Dispatchers.IO,
            repository = detailRepository
        )

        val result = useCase.invoke(5)

        result shouldBeEqualTo testCase

        coVerify(exactly = 1) {
            detailRepository.getObjectDetail(5)
        }
    }

}