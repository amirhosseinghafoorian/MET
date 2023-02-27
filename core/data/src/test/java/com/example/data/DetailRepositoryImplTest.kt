package com.example.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.datasource.detail.DetailRemoteDataSource
import com.example.data.model.GetObjectDetailResponse
import com.example.data.repository.DetailRepositoryImpl
import com.example.domain.model.ObjectDetail
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
class DetailRepositoryImplTest {

    @Rule
    @JvmField
    val mainDispatcherRule = MainDispatcherRule()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var detailRemoteDataSource: DetailRemoteDataSource

    private lateinit var repository: DetailRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun onSuccess_objectDetailIsReceived() = runTest {
        val testCase1 = GetObjectDetailResponse(
            GalleryNumber = "",
            accessionNumber = "1234",
            accessionYear = "1500",
            additionalImages = listOf(),
            artistAlphaSort = "",
            artistBeginDate = "",
            artistDisplayBio = "",
            artistDisplayName = "Leonardo da Vinci",
            artistEndDate = "",
            artistGender = "",
            artistNationality = "",
            artistPrefix = "",
            artistRole = "polymath",
            artistSuffix = "",
            artistULAN_URL = "",
            artistWikidata_URL = "",
            city = "",
            classification = "",
            constituents = listOf(),
            country = "",
            county = "",
            creditLine = "",
            culture = "",
            department = "some department",
            dimensions = "",
            dynasty = "",
            excavation = "",
            geographyType = "",
            isHighlight = false,
            isPublicDomain = false,
            isTimelineWork = false,
            linkResource = "",
            locale = "",
            locus = "",
            measurements = listOf(),
            medium = "",
            metadataDate = "",
            objectBeginDate = 0,
            objectDate = "",
            objectEndDate = 0,
            objectID = 0,
            objectName = "some object",
            objectURL = "",
            objectWikidata_URL = "",
            period = "",
            portfolio = "",
            primaryImage = "",
            primaryImageSmall = "",
            region = "",
            reign = "",
            repository = "",
            rightsAndReproduction = "",
            river = "",
            state = "",
            subregion = "",
            tags = listOf(),
            title = "",
        )

        val testCase2 = ObjectDetail(
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
        var isExceptionThrown = false
        var result: ObjectDetail? = null

        coEvery { detailRemoteDataSource.getObjectDetail(any()) } returns Response.success(testCase1)

        repository = DetailRepositoryImpl(
            remoteDataSource = detailRemoteDataSource
        )

        try {
            result = repository.getObjectDetail(5)
        } catch (e: Exception) {
            isExceptionThrown = true
        }

        isExceptionThrown shouldBeEqualTo false
        result shouldBeEqualTo testCase2

        coVerify(exactly = 1) {
            detailRemoteDataSource.getObjectDetail(5)
        }
    }

    @Test
    fun onError_exceptionIsThrown() = runTest {
        var isExceptionThrown = false
        var result: ObjectDetail? = null

        coEvery { detailRemoteDataSource.getObjectDetail(any()) }  returns Response.error(
            404, "".toResponseBody(null)
        )

        repository = DetailRepositoryImpl(
            remoteDataSource = detailRemoteDataSource
        )

        try {
            result = repository.getObjectDetail(5)
        } catch (e: Exception) {
            isExceptionThrown = true
        }

        isExceptionThrown shouldBeEqualTo true
        result shouldBeEqualTo null

        coVerify(exactly = 1) {
            detailRemoteDataSource.getObjectDetail(5)
        }
    }
}