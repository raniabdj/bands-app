package com.example.test_code.infrastructure
import android.app.Application
import com.example.test_code.infrastructure.di.ApplicationComponent
import com.example.test_code.infrastructure.di.ApplicationModule
import com.example.test_code.infrastructure.di.DaggerApplicationComponent
class App : Application() {
    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
}
