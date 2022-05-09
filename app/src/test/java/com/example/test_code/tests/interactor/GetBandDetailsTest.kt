package com.example.test_code.tests.interactor


import com.example.test_code.application.repository.BandRepository
import com.example.test_code.common.SchedulersProviderTest
import com.example.test_code.domain.BandDetails
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.net.URL

class GetBandDetailsTest {

    private lateinit var getBandDetails: GetBandDetails

    private lateinit var response: Single<BandDetails>

    @Mock
    private lateinit var bandRepository: BandRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        getBandDetails = GetBandDetails(bandRepository, SchedulersProviderTest())
        response = Single.just(
            BandDetails(1, "name", "genre", "description",
                2020, URL("http://url.com"), listOf()))

        whenever(bandRepository.bandDetails(anyInt())).thenReturn(response)
    }

    @Test
    fun `build observable`() {
        val response = getBandDetails.buildUseCaseSingle(1)

        verify(bandRepository, times(1)).bandDetails(1)

        assertEquals(this.response, response)
    }
}