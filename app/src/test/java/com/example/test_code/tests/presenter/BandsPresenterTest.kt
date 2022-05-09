package com.example.test_code.tests.presenter


import com.example.test_code.application.service.ErrorMessageFormatter
import com.example.test_code.application.exception.NetworkConnectionException
import com.example.test_code.application.interactor.GetBands
import com.example.test_code.application.navigation.Navigator
import com.example.test_code.domain.Band
import com.example.test_code.presentation.entity.BandViewEntity
import com.example.test_code.presentation.view.fragment.BandsFragment
import com.nhaarman.mockitokotlin2.*
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class BandsPresenterTest {

    private lateinit var presenter: BandsPresenter

    @Mock private lateinit var getBands: GetBands
    @Mock private lateinit var navigator: Navigator
    @Mock private lateinit var errorFormatter: ErrorMessageFormatter
    @Mock private lateinit var view: BandsFragment

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = BandsPresenter(getBands, navigator, errorFormatter)

        whenever(errorFormatter.getErrorMessage(any())).thenReturn("error")

        whenever(view.activity).thenReturn(mock())

        verifyZeroInteractions(getBands)
        verifyZeroInteractions(view)
        verifyZeroInteractions(navigator)
        verifyZeroInteractions(errorFormatter)
    }

    @Test
    fun `render band list`() {
        var response : DisposableSingleObserver<List<Band>>? = null
        whenever(getBands.execute(any(), any())).thenAnswer {
            response = it.getArgument(0)
            Unit
        }

        presenter.init(view)

        response!!.onSuccess(listOf())

        verify(view).render(any())

        verifyNoMoreInteractions(view)
        verifyZeroInteractions(navigator)
    }

    @Test
    fun `show error`() {
        var response : DisposableSingleObserver<List<Band>>? = null
        whenever(getBands.execute(any(), any())).thenAnswer {
            response = it.getArgument(0)
            Unit
        }

        presenter.init(view)

        response!!.onError(NetworkConnectionException())

        verify(view).notify(anyString())
        verify(view).close()

        verifyNoMoreInteractions(view)
        verifyZeroInteractions(navigator)
    }

    @Test
    fun `click band`() {
        val band = BandViewEntity(1, "name", "url")

        presenter.init(view)
        presenter.onBandClicked(band)

        verify(navigator).showBandDetails(any(), eq(band))
        verifyNoMoreInteractions(navigator)
    }
}