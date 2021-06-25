package com.depromeet.linkzupzup.view.scrap

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapDrawableResource
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.depromeet.linkzupzup.AppConst
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.architecture.presenterLayer.ScrapDetailViewModel
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkHashData
import com.depromeet.linkzupzup.base.BaseAnkoActivity
import com.depromeet.linkzupzup.component.ListDecoration
import com.depromeet.linkzupzup.extensions.dip
import com.depromeet.linkzupzup.view.common.adapter.TagAdapter
import com.depromeet.linkzupzup.view.scrap.ui.ScrapDetailAnkoUI
import io.reactivex.Scheduler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ScrapDetailAnkoActivity : BaseAnkoActivity<ScrapDetailAnkoUI, ScrapDetailViewModel>() {

    override var view: ScrapDetailAnkoUI = ScrapDetailAnkoUI(this::onClickListener)
    override fun onCreateViewModel(): ScrapDetailViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        // overridePendingTransition(R.anim.slide_up_in, R.anim.stay)
        super.onCreate(savedInstanceState)
        with(view) {
            rv.addItemDecoration(ListDecoration(RecyclerView.HORIZONTAL, dip(4)))
            rv.adapter = TagAdapter(this@ScrapDetailAnkoActivity).also { adapter = it }

            with(viewModel) {
                val linkId = intent.getIntExtra(AppConst.LINK_ID, 15)
                val linkUrl = intent.getStringExtra(AppConst.LINK_URL) ?: "https://medium.com/nerd-for-tech/test-driven-development-with-terraform-8cb6b86017c"
                getLinkDetail(linkId = linkId) {

                    GlobalScope.launch(Dispatchers.Main) {

                        Glide.with(this@ScrapDetailAnkoActivity)
                            .load(it.imgURL)
                            .centerCrop()
                            .placeholder(R.drawable.img_link_detail_placeholder)
                            .into(mTopBannerImg)

                        Glide.with(this@ScrapDetailAnkoActivity)
                            .load(it.authorImgUrl)
                            .centerCrop()
                            .transform(RoundedCorners(dip(12)), CenterCrop())
                            .placeholder(R.drawable.ic_scrap_profile_img)
                            .into(mWriterImg)

                        mScrapTitleTv.text = it.linkTitle
                        mScrapContentTv.text = it.description

                        adapter?.initList(it.hashtags)
                        adapter?.notifyDataSetChanged()

                    }

                }
            }
        }
    }

    private fun onClickListener(id: Int) {
        when(id) {
            R.id.activity_close -> {
                val isRefresh = viewModel.isRefresh.value ?: false
                setResult(isRefresh)
            }
        }
    }

    private fun setResult(isRefresh: Boolean) {
        Intent().apply {
            putExtra(AppConst.IS_REFRESH, viewModel.isRefresh.value)
        }.let { setResult(Activity.RESULT_OK, it) }
        onBackPressed()
    }

    override fun onBackPressed() {
        //overridePendingTransition(R.anim.stay, R.anim.slide_down_out)
        super.onBackPressed()
    }

}