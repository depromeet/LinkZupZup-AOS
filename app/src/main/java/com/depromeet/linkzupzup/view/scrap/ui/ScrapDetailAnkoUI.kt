package com.depromeet.linkzupzup.view.scrap.ui
import android.app.Activity
import android.graphics.Color
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.architecture.presenterLayer.ScrapDetailViewModel
import com.depromeet.linkzupzup.base.BaseAnkoView
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

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
                    relativeLayout {

                        linearLayout {
                            gravity = Gravity.BOTTOM

                            /**
                             * 작성자 프로필 이미지
                             */
                            imageView {
                            }.lparams(width= dip(48), height= dip(48)) {
                                rightMargin = dip(8)
                            }

                            /**
                             * 작성자 이름
                             */
                            textView("글쓴이") {
                                setTextSize(TypedValue.COMPLEX_UNIT_PX, dip(12).toFloat())
                                textColor = Color.parseColor("#878D91")
                                typeface = ResourcesCompat.getFont(context, R.font.spoqa_hansansneo_medium)
                                gravity = Gravity.CENTER
                            }.lparams(width= wrapContent, height= wrapContent)

                        }.lparams(width= wrapContent, height= dip(48)) {
                            leftMargin = dip(24)
                        }

                        linearLayout {

                            /**
                             * 메뉴 버튼
                             */
                            verticalLayout {
                                gravity = Gravity.CENTER
                                imageView(R.drawable.ic_gray_more) {
                                }.lparams(width = dip(24), height= dip(24))
                            }.lparams(width= dip(44), height= dip(44))

                        }.lparams(width= wrapContent, height= dip(44)) {
                            alignParentRight()
                            topMargin = dip(16)
                            rightMargin = dip(24)
                        }

                    }.lparams(width= matchParent, height= dip(60)) {
                        topMargin = -dip(16)
                        bottomMargin = dip(4)
                    }

                    /**
                     * 링크 타이틀
                     */
                    textView("스타트업과 안맞는 대기업 임원 DNA는 어떻게 찾아낼까?") {

                        setTextSize(TypedValue.COMPLEX_UNIT_PX, dip(18).toFloat())
                        textColor = Color.parseColor("#292A2B")
                        typeface = ResourcesCompat.getFont(context, R.font.spoqa_hansansneo_bold)

                        lines = 2
                        ellipsize = TextUtils.TruncateAt.END

                    }.lparams(width= matchParent, height= wrapContent) {
                        horizontalMargin = dip(24)
                        bottomMargin = dip(8)
                    }

                    /**
                     * 링크 상세
                     */
                    textView("IT 기업을 중심으로 빠르게 사업을 실행을 하는 것이 사업의 중요한 경쟁력이 된다는 것은 이미 공감대가 만들어져 있다. 그리고 서바이벌을 고민해야 하는 스타트업에서는 그 중요성은 더욱더 크게 받...") {

                        setTextSize(TypedValue.COMPLEX_UNIT_PX, dip(12).toFloat())
                        textColor = Color.parseColor("#878D91")
                        typeface = ResourcesCompat.getFont(context, R.font.spoqa_hansansneo_medium)

                        lines = 3
                        ellipsize = TextUtils.TruncateAt.END

                    }.lparams(width= matchParent, height= wrapContent) {
                        horizontalMargin = dip(24)
                        bottomMargin = dip(10)
                    }

                    /**
                     * 링크 해시 태그
                     */


                    view().lparams(width= dip(1), height= wrapContent, weight= 1f)

                    /**
                     * 이 링크는 따로 알람을 받고싶어요!
                     */
                    linearLayout {
                        gravity = Gravity.CENTER_VERTICAL

                        imageView(R.drawable.ic_link_alram_img) {
                        }.lparams(width= dip(16), height= dip(16)) {
                            rightMargin = dip(4)
                        }

                        textView("이 링크는 따로 알람을 받고싶어요!") {
                            setTextSize(TypedValue.COMPLEX_UNIT_PX, dip(12).toFloat())
                            textColor = Color.parseColor("#292A2B")
                            typeface = ResourcesCompat.getFont(context, R.font.spoqa_hansansneo_medium)
                            gravity = Gravity.CENTER_VERTICAL
                        }

                        imageView(R.drawable.ic_next_icon) {
                        }.lparams(width= dip(16), height= dip(16))

                    }.lparams(width= wrapContent, height= dip(36)) {
                        horizontalMargin = dip(24)
                        bottomMargin = dip(10)
                    }

                    /**
                     * 바로 읽기!
                     */
                    cardView {
                        radius = dip(4).toFloat()
                        elevation = 0f

                        verticalLayout {
                            backgroundColor = Color.parseColor("#4076F6")
                            gravity = Gravity.CENTER

                            textView("바로 읽기!") {
                                setTextSize(TypedValue.COMPLEX_UNIT_PX, dip(14).toFloat())
                                textColor = Color.WHITE
                                typeface = ResourcesCompat.getFont(context, R.font.spoqa_hansansneo_bold)
                                gravity = Gravity.CENTER
                            }.lparams(width= wrapContent, height= wrapContent)

                        }.lparams(width= matchParent, height= matchParent)

                    }.lparams(width= matchParent, height= dip(52)) {
                        horizontalMargin = dip(24)
                        bottomMargin = dip(16)
                    }



                }.lparams(width= matchParent, height= wrapContent, weight= 1f)

            }.lparams(width= matchParent, height= matchParent)


            /**
             * 닫기버튼
             */
            verticalLayout {
                gravity = Gravity.CENTER
                backgroundColor = Color.TRANSPARENT

                imageView(R.drawable.ic_gray_close) {
                }.lparams(width= dip(32), height= dip(32))

            }.lparams(width= dip(64), height= dip(64)) {
                alignParentTop()
                alignParentRight()
            }

        }
    }
}