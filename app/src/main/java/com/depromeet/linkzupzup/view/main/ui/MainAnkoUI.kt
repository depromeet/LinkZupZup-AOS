package com.depromeet.linkzupzup.view.main.ui

import android.app.Activity
import android.graphics.Color
import android.util.TypedValue
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.architecture.presenterLayer.MainViewModel
import com.depromeet.linkzupzup.base.BaseAnkoView
import com.depromeet.linkzupzup.view.dialog.RegisterLinkBottomDialog
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout

class MainAnkoUI : BaseAnkoView<MainViewModel>(), View.OnClickListener {

    private val bottomDialog: RegisterLinkBottomDialog = RegisterLinkBottomDialog()

    override fun createView(ui: AnkoContext<Activity>) = with(ui) {
        verticalLayout {
            lparams(width = matchParent, height = matchParent)

            /**
             * App bar
             */
            appBarLayout(){

            }

            /**
             * Main title
             */
            textView("김나경님,\n어서오세요.반갑습니다!"){
                textColor = Color.BLACK
                setTextSize(TypedValue.COMPLEX_UNIT_PX, dip(24).toFloat())
                typeface = ResourcesCompat.getFont(context, R.font.spoqa_hansansneo_bold)
            }.lparams(width= matchParent, height = wrapContent){
                margin = dip(16)
            }

            button("링크 줍기") {
                id = R.id.link_save
                setOnClickListener(this@MainAnkoUI)
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.link_save -> {
                fragmentManager?.let { bottomDialog.show(it,"main_bottom_sheet") }
            }
            else ->{}
        }

    }


}