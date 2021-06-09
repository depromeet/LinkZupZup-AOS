package com.depromeet.linkzupzup.view.scrap

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.depromeet.linkzupzup.base.BaseActivity
import com.depromeet.linkzupzup.architecture.presenterLayer.ScrapDetailViewModel
import com.depromeet.linkzupzup.utils.DLog
import com.depromeet.linkzupzup.view.scrap.ui.ScrapDetailUI
import com.depromeet.linkzupzup.view.webView.WebViewActivity
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ScrapDetailActivity : BaseActivity<ScrapDetailUI, ScrapDetailViewModel>() {

    override var view: ScrapDetailUI = ScrapDetailUI(::onClick)
    override fun onCreateViewModel(): ScrapDetailViewModel = getViewModel()

    private var linkId: Int = 0
    private var linkUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(!intent.hasExtra("LINK_ID") || !intent.hasExtra("LINK_URL")){
            DLog.e("ScrapDetail","No Link Id or Url")
            finish()
        }

        linkId = intent.getIntExtra("LINK_ID",0)
        linkUrl = intent.getStringExtra("LINK_URL").toString()
    }

    fun onClick(){
        // 웹뷰 개발을 위해 임시로 추가한 코드
        Intent(this, WebViewActivity::class.java).apply {
            putExtra("LINK_ID", linkId)
            putExtra("LINK_URL",linkUrl)
        }.run {
            startActivity(this)
        }
    }

}