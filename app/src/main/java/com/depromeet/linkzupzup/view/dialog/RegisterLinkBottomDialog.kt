package com.depromeet.linkzupzup.view.dialog

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.LinearLayout.HORIZONTAL
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.marginRight
import androidx.core.widget.addTextChangedListener
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.architecture.presenterLayer.MainViewModel
import com.depromeet.linkzupzup.utils.DLog
import com.depromeet.linkzupzup.utils.DeviceUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.support.v4.UI
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterLinkBottomDialog() : BottomSheetDialogFragment(), View.OnClickListener {

    private val viewModel: MainViewModel by viewModel()

    private lateinit var behavior: BottomSheetBehavior<View>

    lateinit var mInputLinkEt: EditText

    lateinit var mInputHashTagEt: EditText

    lateinit var mTextClearBtn: ImageView

    lateinit var mLinkSaveBtn: Button

    lateinit var mCustomTagTitle: TextView

    lateinit var mCustomTagInput: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setStyle(STYLE_NORMAL, custom style 적용)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = UI {
        verticalLayout {
//            id = R.id.main_bottom_sheet
            val sheetHeight = (DeviceUtils.getDeviceSize(context)?.y ?: dip(667)) - dip(52)
            lparams(width= matchParent, height= sheetHeight)
            backgroundColor = Color.WHITE

            /**
             * 닫기 버튼
             */
             linearLayout {
                 orientation = HORIZONTAL
                 gravity = Gravity.END

                 linearLayout {
                     id = R.id.dialog_close
                     gravity = Gravity.CENTER
                     setOnClickListener(this@RegisterLinkBottomDialog)

                     imageView(R.drawable.ic_close)
                         .lparams(width= dip(24), height= dip(24))

                 }.lparams(width= dip(72), height= dip(56))

             }.lparams(width= matchParent, height = dip(56))


            /**
             * 타이틀
             */
            textView("읽고 싶은 링크를\n추가해주세요!"){
                textColor = Color.parseColor("#292A2B")
                setTextSize(TypedValue.COMPLEX_UNIT_PX, dip(24).toFloat())
                typeface = ResourcesCompat.getFont(context,R.font.spoqa_hansansneo_bold)
            }.lparams(width= matchParent, height= dip(64)){
                horizontalMargin = dip(24)
                bottomMargin = dip(8)
            }


            /**
             * 링크 입력창
             */
            relativeLayout {
                gravity = Gravity.CENTER_VERTICAL

                mInputLinkEt = editText {
                    textColor = Color.parseColor("#292A2B")
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, dip(12).toFloat())
                    typeface = ResourcesCompat.getFont(context,R.font.spoqa_hansansneo_regular)
                    hint = "\uD83D\uDC49 링크주소를 여기에 붙여넣기 해주세요. "
                    backgroundColor = Color.parseColor("#F1F2F5")
                    padding = dip(12)
                    addTextChangedListener{
                        it?.let {
                            if(it.isNotEmpty()){
                                mTextClearBtn.visibility = View.VISIBLE
                                mLinkSaveBtn.backgroundColor = Color.parseColor("#4076F6")
                                mLinkSaveBtn.textColor = Color.parseColor("#ffffff")
                            }else{
                                mTextClearBtn.visibility = View.INVISIBLE
                                mLinkSaveBtn.backgroundColor = Color.parseColor("#CED3D6")
                                mLinkSaveBtn.textColor = Color.parseColor("#878D91")
                            }

                        }
                    }
                }.lparams(width= matchParent, height= dip(40)){
                    centerInParent()
                }

                mTextClearBtn = imageView(R.drawable.ic_gray_close){
                    id = R.id.clear_text
                    visibility = View.INVISIBLE
                    setOnClickListener(this@RegisterLinkBottomDialog)
                }.lparams(width= dip(24), height= dip(24)){
                    rightMargin = dip(12)
                    alignParentEnd()
                    centerInParent()
                }


            }.lparams(width= matchParent, height= dip(40)){
                horizontalMargin = dip(24)
                bottomMargin = dip(40)
            }



            /**
             * 해시태그 선택
             */
            linearLayout {
                orientation = HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL

                textView("해시태그를 선택해주세요."){
                    textColor = Color.parseColor("#292A2B")
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, dip(14).toFloat())
                    typeface = ResourcesCompat.getFont(context,R.font.spoqa_hansansneo_bold)
                }.lparams(width= wrapContent, height = wrapContent)

                textView("0/3"){
                    gravity = Gravity.END
                    textColor = Color.parseColor("#878D91")
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, dip(12).toFloat())
                    typeface = ResourcesCompat.getFont(context,R.font.spoqa_hansansneo_regular)
                }.lparams(height = wrapContent, weight = 1f)

            }.lparams(width= matchParent, height = dip(56)){
                bottomMargin = dip(12)
                horizontalMargin = dip(24)
            }

            /**
             * 해시태그 리스트 (Recycler)
             */

            /**
             * 커스텀 해시태그 Title
             */
            mCustomTagTitle = textView("원하는 해시태그가 없으신가요?"){
                id = R.id.custom_hashtag_title
                textColor = Color.parseColor("#878D91")
                setTextSize(TypedValue.COMPLEX_UNIT_PX, dip(12).toFloat())
                typeface = ResourcesCompat.getFont(context,R.font.spoqa_hansansneo_regular)
                setOnClickListener(this@RegisterLinkBottomDialog)
            }.lparams(width= matchParent, height = wrapContent){
                bottomMargin = dip(12)
                horizontalMargin = dip(24)
            }

            /**
             * 커스텀 해시태그 입력
             */
            mCustomTagInput = linearLayout {
                orientation = HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                visibility = View.GONE

                mInputHashTagEt = editText {
                    textColor = Color.parseColor("#292A2B")
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, dip(12).toFloat())
                    typeface = ResourcesCompat.getFont(context,R.font.spoqa_hansansneo_regular)
                    hint = "#"
                    backgroundColor = Color.parseColor("#F1F2F5")
                    padding = dip(12)
                }.lparams(width= wrapContent, height= matchParent, weight= 1f){
                    rightMargin = dip(8)
                }

                linearLayout {
                    id = R.id.custom_hashtag_add
                    backgroundColor = Color.parseColor("#878D91")

                    gravity = Gravity.CENTER
                    setOnClickListener(this@RegisterLinkBottomDialog)

                    imageView(R.drawable.ic_white_plus)
                        .lparams(width= dip(24), height= dip(24))
                }.lparams(width= dip(40), height= dip(40))

            }.lparams(width= matchParent, height = dip(40)){
                bottomMargin = dip(12)
                horizontalMargin = dip(24)
            }

            /**
             * 선택된 해시태그
             */
            verticalLayout {
                gravity = Gravity.BOTTOM

            }.lparams(width= matchParent, height = wrapContent, weight = 1f){
                bottomMargin = dip(4)
                horizontalMargin = dip(24)
            }

            /**
             * 저장하기 버튼
             */
            mLinkSaveBtn = button("저장하기") {
                id = R.id.link_save
                setOnClickListener(this@RegisterLinkBottomDialog)
                backgroundColor = Color.parseColor("#CED3D6")
                textColor = Color.parseColor("#878D91")
                setTextSize(TypedValue.COMPLEX_UNIT_PX, dip(14).toFloat())
                typeface = ResourcesCompat.getFont(context,R.font.spoqa_hansansneo_bold)
            }.lparams(width= matchParent, height= dip(52)){
                horizontalMargin = dip(24)
                verticalMargin = dip(16)
            }

        }

    }.view


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        DLog.e("TEST bottom dialog","on View Created")
//        dialog?.findViewById<LinearLayout>(R.id.main_bottom_sheet)?.let{
//            DLog.e("TEST bottom dialog","success")
//            behavior = BottomSheetBehavior.from(it)
//            behavior.state = BottomSheetBehavior.STATE_EXPANDED
//            behavior.addBottomSheetCallback(bottomSheetCallback)
//        }
    }

    private val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_DRAGGING
                || newState == BottomSheetBehavior.STATE_HALF_EXPANDED ) {
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }

    override fun onClick(view: View?) = with(viewModel) {
        when(view?.id) {
            R.id.dialog_close -> {
                // dialog?.hide()
            }
            R.id.link_save -> {
                getLinkList()
            }
            R.id.custom_hashtag_title -> {
                when(mCustomTagInput.visibility){
                    View.VISIBLE -> {
                        mCustomTagInput.visibility = View.GONE
                        mCustomTagTitle.text = "원하는 해시태그가 없으신가요?"
                        mCustomTagTitle.textColor = Color.parseColor("#878D91")
                    }
                    View.GONE -> {
                        mCustomTagInput.visibility = View.VISIBLE
                        mCustomTagTitle.text = "원하는 해시태그가 없다면 적어주세요!"
                        mCustomTagTitle.textColor = Color.parseColor("#292A2B")
                    }
                    else -> { }
                }
            }
            R.id.custom_hashtag_add -> {
                // add hashtag
            }
            R.id.clear_text -> {
                mInputLinkEt.setText("")
            }
            else -> {}
        }
    }


}