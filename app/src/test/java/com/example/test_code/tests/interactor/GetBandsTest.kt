package com.example.test_code.tests.interactor

import com.example.test_code.application.repository.BandRepository
import com.example.test_code.common.SchedulersProviderTest
import com.example.test_code.domain.Band
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.net.URL

class GetBandsTest {

    private lateinit var getBands: GetBands

    private lateinit var response: Single<List<Band>>

    @Mock private lateinit var bandRepository: BandRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        getBands = GetBands(bandRepository, SchedulersProviderTest())

        response = Single.just(listOf(
            Band(1,"name1", URL("http://url1.com")),
            Band(2,"name2", URL("http://url2.com")),
        ))

        doReturn(response).`when`(bandRepository).bands()
    }

    @Test
    fun `build observable`() {
        val response = getBands.buildUseCaseSingle(UseCase.None())

        verify(bandRepository, times(1)).bands()

        assertEquals(this.response, response)
    }
}