package com.example.test_code.tests.common

import com.example.test_code.infrastructure.platform.SchedulersProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/*
 * Provide IO schedulers for RxJava for testing
 */
class SchedulersProviderTest: SchedulersProvider {
    override fun getIOScheduler(): Scheduler = Schedulers.trampoline()
    override fun getUIScheduler(): Scheduler = Schedulers.trampoline()
}