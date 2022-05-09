package com.example.test_code.infrastructure.di
import com.example.test_code.application.navigation.RouteActivity
import com.example.test_code.presentation.view.fragment.BandDetailsFragment
import com.example.test_code.presentation.view.fragment.BandsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(routeActivity: RouteActivity)
    fun inject(fragment: BandsFragment)
    fun inject(fragment: BandDetailsFragment)
}