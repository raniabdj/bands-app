package com.example.test_code.application.interactor

import androidx.annotation.VisibleForTesting
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import com.example.test_code.infrastructure.platform.SchedulersProvider
abstract class UseCase {

    private val disposables = CompositeDisposable()

    fun dispose() = disposables.dispose()

    @VisibleForTesting
    internal fun addDisposable(disposable: Disposable) = disposables.add(disposable)

    class None
}

abstract class UseCaseCompletable<Params>
    (private val schedulersProvider: SchedulersProvider) : UseCase() {

    abstract fun buildUseCaseCompletable(params: Params): Completable

    fun execute(observer: DisposableCompletableObserver, params: Params) {
        val observable = buildUseCaseCompletable(params)
            .subscribeOn(schedulersProvider.getIOScheduler())
            .observeOn(schedulersProvider.getUIScheduler())

        addDisposable(observable.subscribeWith(observer))
    }
}

abstract class UseCaseSingle<T, Params>
    (private val schedulersProvider: SchedulersProvider) : UseCase() {

    abstract fun buildUseCaseSingle(params: Params): Single<T>

    fun execute(observer: DisposableSingleObserver<T>, params: Params) {
        val observable = buildUseCaseSingle(params)
            .subscribeOn(schedulersProvider.getIOScheduler())
            .observeOn(schedulersProvider.getUIScheduler())

        addDisposable(observable.subscribeWith(observer))
    }
}

abstract class UseCaseObservable<T, Params>
    (private val schedulersProvider: SchedulersProvider) : UseCase() {

    abstract fun buildUseCaseObservable(params: Params): Observable<T>

    fun execute(observer: DisposableObserver<T>, params: Params) {
        val observable = buildUseCaseObservable(params)
            .subscribeOn(schedulersProvider.getIOScheduler())
            .observeOn(schedulersProvider.getUIScheduler())

        addDisposable(observable.subscribeWith(observer))
    }
}