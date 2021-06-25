package com.depromeet.linkzupzup.view.scrap

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.linkzupzup.AppConst
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.architecture.presenterLayer.ScrapDetailViewModel
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkHashData
import com.depromeet.linkzupzup.base.BaseAnkoActivity
import com.depromeet.linkzupzup.component.ListDecoration
import com.depromeet.linkzupzup.extensions.dip
import com.depromeet.linkzupzup.view.common.adapter.TagAdapter
import com.depromeet.linkzupzup.view.scrap.ui.ScrapDetailAnkoUI
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ScrapDetailAnkoActivity : BaseAnkoActivity<ScrapDetailAnkoUI, ScrapDetailViewModel>() {

    override var view: ScrapDetailAnkoUI = ScrapDetailAnkoUI(this::onClickListener)
    override fun onCreateViewModel(): ScrapDetailViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(view) {
            rv.addItemDecoration(ListDecoration(RecyclerView.HORIZONTAL, dip(4)))
            rv.adapter = TagAdapter(this@ScrapDetailAnkoActivity).apply {
                addList(arrayListOf(LinkHashData(hashtagName = "#디자인"), LinkHashData(hashtagName = "#포트폴리오")))
            }.also { adapter = it }
            adapter?.notifyDataSetChanged()
        }

        with(viewModel) {
            val linkId = intent.getIntExtra(AppConst.LINK_ID, -1)
            val linkUrl = intent.getStringExtra(AppConst.LINK_URL) ?: ""
            getLinkDetail(linkId = linkId)
        }
    }

    private fun onClickListener(id: Int) {
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