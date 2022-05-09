package com.example.test_code.application.service
import android.content.Context
import com.example.test_code.R
import com.example.test_code.application.exception.NetworkConnectionException
import com.example.test_code.application.exception.ServerErrorException
import com.example.test_code.application.exception.YouTubeNotInstalled
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorMessageFormatter
@Inject constructor(context: Context) {

    private val r = context.resources

    fun getErrorMessage(e: Throwable): String =
        when (e) {
            is NetworkConnectionException -> r.getString(R.string.failure_network_connection)
            is ServerErrorException -> r.getString(R.string.failure_server_error)
            is YouTubeNotInstalled -> r.getString(R.string.failure_youtube_not_installed)
            else -> r.getString(R.string.failure_unknown_error, e.localizedMessage)
        }
}