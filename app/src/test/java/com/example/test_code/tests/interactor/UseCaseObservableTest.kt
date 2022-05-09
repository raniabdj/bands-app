package com.example.test_code.tests.interactor

import com.example.test_code.common.SchedulersProviderTest
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class UseCaseObservableTest {

    companion object {
        private val TEST_PAYLOAD = Payload(1)
        private val TEST_PARAMS = UseCaseTestImpl.Params()
    }

    private lateinit var useCase: UseCaseTestImpl
    private lateinit var observable: Observable<Payload>

    @Mock private lateinit var disposable: DisposableObserver<Payload>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        observable = spy(Observable.just(TEST_PAYLOAD))
        useCase = spy(UseCaseTestImpl(observable))
    }

    @Test
    fun `observable is built`() {
        useCase.execute(disposable, TEST_PARAMS)

        verify(useCase).buildUseCaseObservable(any())
        verify(useCase).addDisposable(any())
    }

    @Test
    fun `observable works`() {
        useCase.execute(disposable, TEST_PARAMS)

        observable.test().await().assertComplete().assertNoErrors()

        assertFalse(disposable.isDisposed)
        verify(disposable).onNext(eq(TEST_PAYLOAD))
        verify(disposable).onComplete()
        verify(disposable, times(0)).onError(any())
    }

    private class UseCaseTestImpl(private val observable: Observable<Payload>) :
        UseCaseObservable<Payload, UseCaseTestImpl.Params>(SchedulersProviderTest()) {
        override fun buildUseCaseObservable(params: Params): Observable<Payload> = observable
        class Params
    }

    private data class Payload(val id: Int)
}