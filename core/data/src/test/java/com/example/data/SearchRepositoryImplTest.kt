package com.example.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.datasource.search.SearchRemoteDataSource
import com.example.data.model.SearchObjectsResponse
import com.example.data.repository.SearchRepositoryImpl
import com.example.domain.model.SearchObjects
import com.example.testing.MainDispatcherRule
import com.example.testing.shouldBeEqualTo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class SearchRepositoryImplTest {

    @Rule
    @JvmField
    val mainDispatcherRule = MainDispatcherRule()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var searchRemoteDataSource: SearchRemoteDataSource

    private lateinit var repository: SearchRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun onSuccess_searchObjectsAreReceived() = runTest {
        val testCase = SearchObjectsResponse(
            objectIDs = listOf(1, 2, 3),
            total = 3
        )
        var isExceptionThrown = false
        var result: SearchObjects? = null

        coEvery { searchRemoteDataSource.searchObjects(any()) } returns Response.success(testCase)

        repository = SearchRepositoryImpl(
            remoteDataSource = searchRemoteDataSource
        )

        try {
            result = repository.searchObjects("flower")
        } catch (e: Exception) {
            isExceptionThrown = true
        }

        isExceptionThrown shouldBeEqualTo false
        result shouldBeEqualTo SearchObjects(testCase.objectIDs)

        coVerify(exactly = 1) {
            searchRemoteDataSource.searchObjects("flower")
        }
    }

    @Test
    fun onError_exceptionIsThrown() = runTest {
        var isExceptionThrown = false
        var result: SearchObjects? = null

        coEvery { searchRemoteDataSource.searchObjects(any()) } returns Response.error(
            404, "".toResponseBody(null)
        )

        repository = SearchRepositoryImpl(
            remoteDataSource = searchRemoteDataSource
        )

        try {
            result = repository.searchObjects("flower")
        } catch (e: Exception) {
            isExceptionThrown = true
        }

        isExceptionThrown shouldBeEqualTo true
        result shouldBeEqualTo null

        coVerify(exactly = 1) {
            searchRemoteDataSource.searchObjects("flower")
        }
    }
}