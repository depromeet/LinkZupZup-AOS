package com.depromeet.linkzupzup.view.dialog

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.LinearLayout.HORIZONTAL
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.architecture.domainLayer.entities.api.LinkRegisterEntity
import com.depromeet.linkzupzup.architecture.presenterLayer.MainViewModel
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkHashData
import com.depromeet.linkzupzup.component.ListDecoration
import com.depromeet.linkzupzup.extensions.dip
import com.depromeet.linkzupzup.utils.DeviceUtils
import com.depromeet.linkzupzup.view.common.adapter.TagAdapter
import com.depromeet.linkzupzup.view.common.adapter.TagAdapter.Companion.TAG_TYPE_LARGE
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI

class RegisterLinkBottomDialog(private val viewModel: ()->MainViewModel)
    : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>

    private lateinit var mInputLinkEt: EditText

    private lateinit var mInputHashTagEt: EditText

    private lateinit var mTextClearBtn: ImageView

    private lateinit var mLinkSaveBtn: Button

    private lateinit var mCustomTagTitleTv: TextView

    private lateinit var mCustomTagLayout: LinearLayout

    private lateinit var mTagSizeTv: TextView

    private lateinit var mTagRv1: RecyclerView

    private lateinit var mTagRv2: RecyclerView

    private lateinit var mSelectedTagRv: RecyclerView

    private lateinit var mTagAdapter1: TagAdapter

    private lateinit var mTagAdapter2: TagAdapter

    private lateinit var mSelectedTagAdapter: TagAdapter



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        return super.onCreateDialog(savedInstanceState).apply {
            (this as BottomSheetDialog).behavior
                .also { bottomSheetBehavior = it  }
                .addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {

                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        when (newState) {
                            BottomSheetBehavior.STATE_HIDDEN -> {
                                mInputLinkEt.setText("")
                                mInputHashTagEt.setText("")
                                with(mSelectedTagAdapter.list){
                                    this.removeAll(this)
                                    mTagSizeTv.text = this.size.toString()
                                }
                            }
                            else -> {}
                        }
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {}
                })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view) {
            mTagRv1.addItemDecoration(ListDecoration(RecyclerView.HORIZONTAL, dip(8)))
            mTagRv2.addItemDecoration(ListDecoration(RecyclerView.HORIZONTAL, dip(8)))
            mSelectedTagRv.addItemDecoration(ListDecoration(RecyclerView.HORIZONTAL, dip(8)))
            mTagAdapter1.initList(LinkHashData.tc1)
            mTagAdapter2.initList(LinkHashData.tc2)
            mSelectedTagAdapter.initList(arrayListOf())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = UI {
        verticalLayout {
            val sheetHeight = (DeviceUtils.getDeviceSize(context)?.y ?: dip(667)) - dip(52)
            lparams(width= matchParent, height= sheetHeight)

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

                linearLayout{
                    gravity = Gravity.END
                    mTagSizeTv = textView("0"){
                        textColor = Color.parseColor("#878D91")
                        setTextSize(TypedValue.COMPLEX_UNIT_PX, dip(12).toFloat())
                        typeface = ResourcesCompat.getFont(context,R.font.spoqa_hansansneo_regular)
                    }.lparams(height = wrapContent, width = wrapContent)

                    textView("/3"){
                        textColor = Color.parseColor("#878D91")
                        setTextSize(TypedValue.COMPLEX_UNIT_PX, dip(12).toFloat())
                        typeface = ResourcesCompat.getFont(context,R.font.spoqa_hansansneo_regular)
                    }.lparams(height = wrapContent, width = wrapContent)
                }.lparams(height = wrapContent, width = wrapContent, weight = 1f)



            }.lparams(width= matchParent, height = dip(56)){
                horizontalMargin = dip(24)
            }

            /**
             * 해시태그 리스트 (Recycler)
             */

            mTagRv1 = recyclerView {
                clipToPadding = false
                horizontalPadding = dip(24)
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                adapter = TagAdapter(requireContext(), TAG_TYPE_LARGE).apply {
                    setOnClickListener(this@RegisterLinkBottomDialog::updateTag)
                }.also { mTagAdapter1 = it }

            }.lparams(width = matchParent, height = wrapContent) {
                bottomMargin = dip(12)
            }

            mTagRv2 = recyclerView {
                clipToPadding = false
                horizontalPadding = dip(24)
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                adapter = TagAdapter(requireContext(), TAG_TYPE_LARGE).apply {
                    setOnClickListener(this@RegisterLinkBottomDialog::updateTag)
                }.also { mTagAdapter2 = it }
            }.lparams(width = matchParent, height = wrapContent) {
                bottomMargin = dip(12)
            }


            /**
             * 커스텀 해시태그 Title
             */
            mCustomTagTitleTv = textView("원하는 해시태그가 없으신가요?"){
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
            mCustomTagLayout = linearLayout {
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
                    setOnClickListener {
                        updateTag(0, LinkHashData(hashtagName = mInputHashTagEt.text.toString()))
                    }

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

                mSelectedTagRv = recyclerView {
                    clipToPadding = false
                    horizontalPadding = dip(24)
                    layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    adapter = TagAdapter(requireContext(), TAG_TYPE_LARGE).apply {
                        setOnClickListener(this@RegisterLinkBottomDialog::updateTag)
                    }.also { mSelectedTagAdapter = it }

                }.lparams(width = matchParent, height = wrapContent)

            }.lparams(width= matchParent, height = wrapContent, weight = 1f){
                bottomMargin = dip(4)
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


    override fun onClick(view: View?) = with(viewModel()) {
        when(view?.id) {
            R.id.dialog_close -> bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

            R.id.link_save -> {
                this.registerLink(LinkRegisterEntity(
                    linkURL = mInputLinkEt.text.toString(),
                    hashtags = ArrayList(mSelectedTagAdapter.list.map { it.hashtagName} )
                ))
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                this.getLinkList()
            }

            R.id.custom_hashtag_title -> {
                when(mCustomTagLayout.visibility){
                    View.VISIBLE -> {
                        mCustomTagLayout.visibility = View.GONE
                        mCustomTagTitleTv.text = "원하는 해시태그가 없으신가요?"
                        mCustomTagTitleTv.textColor = Color.parseColor("#878D91")
                    }
                    View.GONE -> {
                        mCustomTagLayout.visibility = View.VISIBLE
                        mCustomTagTitleTv.text = "원하는 해시태그가 없다면 적어주세요!"
                        mCustomTagTitleTv.textColor = Color.parseColor("#292A2B")
                    }
                    else -> { }
                }
            }

            R.id.custom_hashtag_add -> {

            }

            R.id.clear_text -> {
                mInputLinkEt.setText("")
            }
            else -> {}
        }
    }


    private fun updateTag(position: Int, tag: LinkHashData) {
        mSelectedTagAdapter.updateHashTags(tag)
        mSelectedTagAdapter.notifyDataSetChanged()
        mTagSizeTv.text = mSelectedTagAdapter.list.size.toString()
    }


}