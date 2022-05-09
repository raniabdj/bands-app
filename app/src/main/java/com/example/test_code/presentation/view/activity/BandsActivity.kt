package com.example.test_code.presentation.view.activity
import android.content.Context
import android.content.Intent
import com.example.test_code.infrastructure.platform.BaseActivity
import com.example.test_code.infrastructure.platform.BaseFragment
import com.example.test_code.presentation.view.fragment.BandsFragment
class BandsActivity : BaseActivity() {

    companion object {
        fun intent(context: Context) = Intent(context, BandsActivity::class.java)
    }

    override fun fragment(): BaseFragment = BandsFragment()
}