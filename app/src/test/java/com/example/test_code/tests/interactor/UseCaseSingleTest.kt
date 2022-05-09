package com.example.test_code.tests.interactor


import com.example.test_code.common.SchedulersProviderTest
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class UseCaseSingleTest {

    companion object {
        private val TEST_PAYLOAD = Payload(1)
        private val TEST_PARAMS = UseCaseTestImpl.Params()
    }

    private lateinit var useCase: UseCaseTestImpl
    private lateinit var single: Single<Payload>

    @Mock
    private lateinit var disposable: DisposableSingleObserver<Payload>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        single = spy(Single.just(TEST_PAYLOAD))
        useCase = spy(UseCaseTestImpl(single))
    }

    @Test
    fun `single is built`() {
        useCase.execute(disposable, TEST_PARAMS)

        verify(useCase).buildUseCaseSingle(any())
        verify(useCase).addDisposable(any())
    }

    @Test
    fun `single works`() {
        useCase.execute(disposable, TEST_PARAMS)

        single.test().await().assertComplete().assertNoErrors()

        assertFalse(disposable.isDisposed)
        verify(disposable).onSuccess(eq(TEST_PAYLOAD))
        verify(disposable, times(0)).onError(any())
    }

    private class UseCaseTestImpl(private val single: Single<Payload>) :
        UseCaseSingle<Payload, UseCaseTestImpl.Params>(SchedulersProviderTest()) {
        override fun buildUseCaseSingle(params: Params): Single<Payload> = single
        class Params
    }

    private data class Payload(val id: Int)
}