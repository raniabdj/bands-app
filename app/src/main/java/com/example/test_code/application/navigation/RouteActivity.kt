package com.example.test_code.application.navigation
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test_code.infrastructure.App
import com.example.test_code.infrastructure.di.ApplicationComponent
import javax.inject.Inject


class RouteActivity: AppCompatActivity() {
    private val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as App).appComponent
    }
    @Inject
    internal lateinit var navigator: Navigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        navigator.showMain(this)
    }
}