package com.depromeet.linkzupzup.view.scrap.ui
import android.app.Activity
import android.graphics.Color
import android.widget.ImageView
import androidx.compose.ui.unit.dp
import androidx.core.view.marginTop
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.architecture.presenterLayer.ScrapDetailViewModel
import com.depromeet.linkzupzup.base.BaseAnkoView
import org.jetbrains.anko.*

class ScrapDetailAnkoUI(private val clickListener: (Int) -> Unit): BaseAnkoView<ScrapDetailViewModel>() {

    lateinit var mTopBannerImg: ImageView

    override fun createView(ui: AnkoContext<Activity>) = with(ui) {
        relativeLayout {
            lparams(width= matchParent, height= matchParent)

            verticalLayout {

                /**
                 * 상단 링크 메타 이미지
                 */
                mTopBannerImg = imageView(R.drawable.img_link_detail_placeholder) {
                }.lparams(width= matchParent, height= dip(240))

                verticalLayout {
                    linearLayout {



                        linearLayout {
                            imageView()
                        }.lparams(width= wrapContent, height= dip(48))

                    }.lparams(width= matchParent, height= dip(60)) {
                        topMargin = -dip(16)
                    }
                }.lparams(width= matchParent, height= wrapContent, weight= 1f)

            }.lparams(width= matchParent, height= matchParent)


            /**
             * 닫기버튼
             */
            relativeLayout {
                backgroundColor = Color.TRANSPARENT
                imageView(R.drawable.ic_gray_close) {
                }.lparams(width= dip(32), height= dip(32))
            }.lparams(width= dip(64), height= dip(64)) {
                centerInParent()
            }



        }
    }
}