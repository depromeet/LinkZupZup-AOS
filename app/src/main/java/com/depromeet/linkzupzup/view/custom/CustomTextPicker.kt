package com.depromeet.linkzupzup.view.custom

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.NumberPicker


class CustomTextPicker: NumberPicker {

    var list: ArrayList<String> = arrayListOf()
    var onValueChangeListener: NumberPicker.OnValueChangeListener? = null

    constructor(context: Context, attrs: AttributeSet? = null): super(context, attrs) {
        wrapSelectorWheel = false
        descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        //showDividers = LinearLayout.SHOW_DIVIDER_NONE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            selectionDividerHeight = 0
        }
    }

    fun initData(list: ArrayList<String> = arrayListOf()) {
        this.list.clear()
        this.list.addAll(list)
        minValue = 0
        maxValue = this.list.size - 1
        displayedValues = this.list.toTypedArray()
        invalidate()
    }

    @JvmName("setOnValueChangeListener1")
    fun setOnValueChangeListener(changeListener: OnValueChangeListener) {
        this.onValueChangeListener = changeListener
        setOnValueChangedListener(changeListener)
        invalidate()
    }

}