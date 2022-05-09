package com.example.test_code.presentation.view.activity
import android.content.Context
import android.content.Intent
import com.example.test_code.infrastructure.platform.BaseActivity
import com.example.test_code.infrastructure.platform.BaseFragment
import com.example.test_code.presentation.entity.BandViewEntity
import com.example.test_code.presentation.view.fragment.BandDetailsFragment
class BandDetailsActivity: BaseActivity() {

    companion object {
        private const val INTENT_EXTRA_PARAM_BAND = "com.cesarmauri.INTENT_PARAM_BAND"
        fun intent(context: Context, bandViewEntity: BandViewEntity): Intent {
            val intent = Intent(context, BandDetailsActivity::class.java)
            intent.putExtra(INTENT_EXTRA_PARAM_BAND, bandViewEntity)
            return intent
        }
    }

    override fun fragment(): BaseFragment =
        BandDetailsFragment.forBand(intent.getParcelableExtra(INTENT_EXTRA_PARAM_BAND)!!)

}