package com.example.test_code.tests.interactor

import io.reactivex.disposables.Disposable
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class UseCaseTest {

    private lateinit var useCase: UseCaseImpl

    @Before
    fun setUp() {
        useCase = UseCaseImpl()
    }

    @Test
    fun `dispose works`() {
        val disposable = DisposableImpl()

        useCase.addDisposable(disposable)

        assertFalse(disposable.isDisposed)

        useCase.dispose()

        assertTrue(disposable.isDisposed)
    }

    private class DisposableImpl : Disposable {
        private var _isDisposed = false
        override fun dispose() { _isDisposed = true }
        override fun isDisposed(): Boolean = _isDisposed
    }

    private class UseCaseImpl : UseCase()
}