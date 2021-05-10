package com.depromeet.linkzupzup.view.custom

import android.widget.CompoundButton
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.SwitchCompat
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.depromeet.linkzupzup.R

/**
 * Modifier.width(46.dp) -> Thumb 여백 2dp로 인해 width를 딱맞게 지정할 경우, SwitchCompat이 짤리는 현상발생
 * height만 지정해서 사용해야 될것 같습니다.
 *
 * switchCompatInstance callback : SwitchCompat Instance를 참조하기 위한 용도이며,
 * 생성 시, SwitchCompat Instance를 반환합니다.
 *
 * updateCallback callback : View가 확장될 때 호출되는 update 콜백, 사용할 필요가 없을걸로 예상됩니다.
 */
@Composable
fun CustomSwitchCompat(modifier: Modifier = Modifier.height(24.dp),
                       @DrawableRes thumbDrawableRes: Int = R.drawable.custom_switch_compat_thumb,
                       @DrawableRes trackDrawableRes: Int = R.drawable.custom_switch_compat_track,
                       switchCompatInstance: (SwitchCompat)->Unit = {},
                       updateCallback: (SwitchCompat)->Unit = {},
                       checkedOnChangeListener: CompoundButton.OnCheckedChangeListener? = null) {
    AndroidView(
        modifier = modifier,
        factory = { ctx ->

            SwitchCompat(ctx).apply {
                thumbDrawable = ContextCompat.getDrawable(context, thumbDrawableRes)
                trackDrawable = ContextCompat.getDrawable(context, trackDrawableRes)
                checkedOnChangeListener?.let { listener -> setOnCheckedChangeListener(listener) }
                switchCompatInstance.invoke(this)
            }
        },
        update = { view ->
            updateCallback.invoke(view)
        }
    )

}