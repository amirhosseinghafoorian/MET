package com.example.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.model.SearchObjects
import com.example.domain.repository.SearchRepository
import com.example.domain.usecase.SearchObjectsUseCase
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
class SearchObjectsUseCaseTest {

    @Rule
    @JvmField
    val mainDispatcherRule = MainDispatcherRule()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var searchRepository: SearchRepository

    private lateinit var useCase: SearchObjectsUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun onExecute_searchObjectsAreReceived() = runTest {
        val testCase = SearchObjects(listOf(1, 2, 3))
        coEvery { searchRepository.searchObjects(any()) } returns testCase

        useCase = SearchObjectsUseCase(
            dispatcher = Dispatchers.IO,
            repository = searchRepository
        )

        val result = useCase.invoke("flower")

        result shouldBeEqualTo testCase

        coVerify(exactly = 1) {
            searchRepository.searchObjects("flower")
        }
    }

}