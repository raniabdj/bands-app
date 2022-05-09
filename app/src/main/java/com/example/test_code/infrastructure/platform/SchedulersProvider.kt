package com.example.test_code.infrastructure.platform
import io.reactivex.Scheduler
interface SchedulersProvider {
    fun getIOScheduler(): Scheduler
    fun getUIScheduler(): Scheduler
}