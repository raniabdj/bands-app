package com.example.test_code.tests.presenter


import com.example.test_code.application.exception.NetworkConnectionException
import com.example.test_code.application.exception.YouTubeNotInstalled
import com.example.test_code.application.interactor.GetBandDetails
import com.example.test_code.application.navigation.Navigator
import com.example.test_code.application.service.ErrorMessageFormatter
import com.example.test_code.domain.BandDetails
import com.example.test_code.presentation.entity.AlbumViewEntity
import com.example.test_code.presentation.entity.BandViewEntity
import com.example.test_code.presentation.view.fragment.BandDetailsFragment
import com.nhaarman.mockitokotlin2.*
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.net.URL

class BandDetailsPresenterTest {

    companion object {
        val SAMPLE_BAND= BandDetails(0, "", "", "",
            0, null, listOf())
    }

    @Mock private lateinit var navigator: Navigator
    @Mock private lateinit var errorFormatter: ErrorMessageFormatter
    @Mock private lateinit var view: BandDetailsFragment
    @Mock private lateinit var getBandDetails: GetBandDetails

    private lateinit var presenter: BandDetailsPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = BandDetailsPresenter(getBandDetails, navigator, errorFormatter)

        whenever(errorFormatter.getErrorMessage(any())).thenReturn("error")
        whenever(view.activity).thenReturn(mock())

        verifyZeroInteractions(getBandDetails)
    }

    @Test
    fun `render data`() {
        var response : DisposableSingleObserver<BandDetails>? = null
        whenever(getBandDetails.execute(any(), any())).thenAnswer {
            response = it.getArgument(0)
            Unit
        }

        presenter.init(view, BandViewEntity(1, "name", ""))

        response!!.onSuccess(SAMPLE_BAND)

        verify(view).render(any())
    }

    @Test
    fun `show error`() {
        var response : DisposableSingleObserver<BandDetails>? = null
        whenever(getBandDetails.execute(any(), any())).thenAnswer {
            response = it.getArgument(0)
            Unit
        }

        presenter.init(view, BandViewEntity(1, "name", ""))

        response!!.onError(NetworkConnectionException())

        verify(view).notify(anyString())
        verify(view).close()
    }

    @Test
    fun `album clicked`() {
        presenter.init(view, BandViewEntity(1, "name", ""))
        presenter.onAlbumClicked(AlbumViewEntity(
            "title", "date", URL("https://pic")))
        verify(navigator).openYouTubeSearch(any(), eq("name title"))
    }

    @Test
    fun `album clicked no youtube`() {
        whenever(navigator.openYouTubeSearch(any(), any()))
            .thenAnswer { throw YouTubeNotInstalled() }
        presenter.init(view, BandViewEntity(1, "name", ""))
        presenter.onAlbumClicked(AlbumViewEntity(
            "title", "date", URL("https://pic")))
        verify(view).notify(any())
    }
}