package com.depromeet.linkzupzup.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.architecture.presenterLayer.MainViewModel
import com.depromeet.linkzupzup.utils.DeviceUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistBottomDialog(val itemClick: (Int) -> Unit) : BottomSheetDialogFragment(), View.OnClickListener {

    private val viewModel:MainViewModel by viewModel()

    lateinit var mInputLinkEt: EditText

    lateinit var mLinkSaveBtn: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = UI {
        verticalLayout {
            val sheetHeight = (DeviceUtils.getDeviceSize(context)?.y ?: dip(667)) - dip(52)
            lparams(width= matchParent, height= sheetHeight)
            mInputLinkEt = editText {

            }.lparams(width= wrapContent, height= wrapContent)



            mLinkSaveBtn = button("저장하기") {
                id = R.id.link_save
                setOnClickListener(this@RegistBottomDialog)
            }.lparams(width= matchParent, height= dip(52))

        }

    }.view

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        view.tv_recommend_order.setOnClickListener {
//            itemClick(0)
//            dialog?.dismiss()
//        }
//        view.tv_review_order.setOnClickListener {
//            itemClick(1)
//            dialog?.dismiss()
//        }
    }

    override fun onClick(view: View?) = with(viewModel) {
        when(view?.id) {
            R.id.link_save -> {
                getLinkList()
            }
            else -> {}
        }
    }
}