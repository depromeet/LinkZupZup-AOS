package com.depromeet.linkzupzup.extensions

import android.view.ViewManager
import androidx.viewpager2.widget.ViewPager2
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.view.custom.CustomTextPicker
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko.custom.ankoView

//inline fun ViewManager.squareImageView() = squareImageView {}
//inline fun ViewManager.squareImageView(init: SquareImageView.() -> Unit): SquareImageView {
//    return ankoView({ SquareImageView(it) }, theme= 0, init= init)
//}

inline fun ViewManager.viewPager2(): androidx.viewpager2.widget.ViewPager2 = viewPager2 {}
inline fun ViewManager.viewPager2(init: @AnkoViewDslMarker ViewPager2.() -> Unit): androidx.viewpager2.widget.ViewPager2 {
    return ankoView({ ViewPager2(it) }, theme = 0, init= init)
}

inline fun ViewManager.customTextPicker(): CustomTextPicker = customTextPicker {}
inline fun ViewManager.customTextPicker(init: @AnkoViewDslMarker CustomTextPicker.() -> Unit): CustomTextPicker {
    return ankoView({ CustomTextPicker(it) }, theme = R.style.DefaultNumberPickerTheme, init= init)
}