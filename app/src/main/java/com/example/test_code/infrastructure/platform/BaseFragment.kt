package com.example.test_code.infrastructure.platform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.test_code.R
import com.example.test_code.infrastructure.App
import com.example.test_code.infrastructure.di.ApplicationComponent
import com.google.android.material.snackbar.Snackbar
import kotlinx.parcelize.Parcelize

abstract class BaseFragment : Fragment() {

    abstract fun layoutId(): Int

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as App).appComponent
    }

    private val viewContainer: View get() = (activity as BaseActivity).findViewById(R.id.fragmentContainer)

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View =
        inflater.inflate(layoutId(), container, false)

    open fun onBackPressed() {}

    internal fun notify(message: String) =
        Snackbar.make(viewContainer, message, Snackbar.LENGTH_SHORT).show()

    internal fun close() = fragmentManager?.popBackStack()
}