package com.depromeet.linkzupzup.view.scrap

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.depromeet.linkzupzup.AppConst
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.base.BaseActivity
import com.depromeet.linkzupzup.architecture.presenterLayer.ScrapDetailViewModel
import com.depromeet.linkzupzup.view.scrap.ui.ScrapDetailUI
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ScrapDetailActivity : BaseActivity<ScrapDetailUI, ScrapDetailViewModel>() {

    override var view: ScrapDetailUI = ScrapDetailUI(this::onClickListener)
    override fun onCreateViewModel(): ScrapDetailViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewModel) {
            val linkId = intent.getIntExtra(AppConst.LINK_ID, -1)
            val linkUrl = intent.getStringExtra(AppConst.LINK_URL) ?: ""
            getLinkDetail(linkId = linkId)
        }
    }

    private fun onClickListener(id: Int){
        when(id) {
            R.id.activity_close -> {
                val isRefresh = viewModel.isRefresh.value ?: false
                setResult(isRefresh)
                onBackPressed()
            }
        }
    }

    private fun setResult(isRefresh: Boolean) {
        Intent().apply {
            putExtra(AppConst.IS_REFRESH, viewModel.isRefresh.value)
        }.let { setResult(Activity.RESULT_OK, it) }
    }

    override fun onBackPressed() {

        super.onBackPressed()
    }
}