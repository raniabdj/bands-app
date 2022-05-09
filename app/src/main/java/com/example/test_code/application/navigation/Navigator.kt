package com.example.test_code.application.navigation

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.example.test_code.application.exception.YouTubeNotInstalled
import com.example.test_code.application.service.AuthenticationService
import com.example.test_code.presentation.entity.BandViewEntity
import com.example.test_code.presentation.view.activity.BandDetailsActivity
import com.example.test_code.presentation.view.activity.BandsActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator
    @Inject constructor(private val authenticationService: AuthenticationService) {

    fun showMain(activity: FragmentActivity) {
        when (authenticationService.isAuthenticated) {
            true -> showOther(activity)
            false -> showLogin()
        }
    }
    private fun showLogin() {
        // Open here your own login experience
    }
    private fun showOther(activity: FragmentActivity)  {
        activity.startActivity(BandsActivity.intent(activity))
    }
    fun showBandDetails(activity: FragmentActivity, bandViewEntity: BandViewEntity) {
        val intent = BandDetailsActivity.intent(activity, bandViewEntity)
        activity.startActivity(intent)
    }
    fun openYouTubeSearch(activity: FragmentActivity, query: String) {
        val intent = Intent(Intent.ACTION_SEARCH)
        intent.setPackage("com.google.android.youtube")
        intent.putExtra("query", query)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        try {
            activity.startActivity(intent)
        }
        catch (e: ActivityNotFoundException) {
            throw YouTubeNotInstalled(e)
        }
    }}