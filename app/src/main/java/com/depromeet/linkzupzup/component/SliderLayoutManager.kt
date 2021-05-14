package com.depromeet.linkzupzup.component
import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * RecyclerView를 스크롤 했을 시, Picker와 같은 효과를 주기 위해 사용합니다.
 * 참고 : https://medium.com/@nbtk123/create-your-own-horizontal-vertical-slider-picker-android-94b6ee32b3ff
 */
class SliderLayoutManager(context: Context?, @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL, reverseLayout: Boolean = false) : LinearLayoutManager(context, orientation, reverseLayout) {

    var callback: OnItemSelectedListener? = null

    var rv: RecyclerView? = null

    override fun onAttachedToWindow(view: RecyclerView?) {
        super.onAttachedToWindow(view)
        rv = view
    }

    /**
     * RecyclerView의 중앙 위치에서 근접한 자식뷰의 position을 찾고 callback을 호출합니다.
     */
    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        rv?.let { recyclerView ->


            // 스크롤이 멈춘 경우
            if (state == RecyclerView.SCROLL_STATE_IDLE) {

                /**
                 * RecyclerView의 중앙 위치에서 가장 가까운 child를 찾고 선택된 아이템의 position을 반환합니다.
                 */
                val recyclerViewCenterX = getRecyclerViewCenterX()
                var minDistance = if (orientation == RecyclerView.HORIZONTAL) recyclerView.width else recyclerView.height
                var position = -1

                for (i in 0 until recyclerView.childCount) {
                    val child = recyclerView.getChildAt(i)
                    val childCenterX = getDecoratedStart(child) + (getDecoratedEnd(child) - getDecoratedStart(child)) / 2
                    var childDistanceFromCenter = Math.abs(childCenterX - recyclerViewCenterX)

                    if (childDistanceFromCenter < minDistance) {
                        minDistance = childDistanceFromCenter
                        position = recyclerView.getChildLayoutPosition(child)
                    }
                }


                // Notify on the selected item
                callback?.onItemSelected(position)
            }

        }
    }



    /**
     * RecyclerView의 중아에서 부터 Scale 을 달리 적용합니다.
     * 멀어질수록 스케일이 작아짐.
     */
    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        // scaleDownView()
    }
    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        var scrolled = super.scrollHorizontallyBy(dx, recycler, state)
//        var scrolled = 0
//        if (orientation == RecyclerView.HORIZONTAL) {
//            scrolled = super.scrollHorizontallyBy(dx, recycler, state)
//            scaleDownView()
//        }
        return scrolled
    }
    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        var scrolled = super.scrollVerticallyBy(dy, recycler, state)
//        var scrolled = 0
//        if (orientation == RecyclerView.VERTICAL) {
//            scrolled = super.scrollVerticallyBy(dy, recycler, state)
//            scaleDownView()
//        }
        return scrolled
    }
    /**
     * 스케일 크기 적용
     */
    private fun scaleDownView() {
        val mid = width / 2.0f
        for (i in 0 until childCount) {

            // RecyclerView의 중앙 위치에서 자식 뷰까지의 거리 계산
            getChildAt(i)?.let { child ->
                val childMid = (getDecoratedStart(child) + getDecoratedEnd(child)) / 2.0f
                val distanceFromCenter = abs(mid - childMid)

                // 스케일 계산식
                val scale = 1 - sqrt((distanceFromCenter/width).toDouble()).toFloat()*0.66f

                // 자식 뷰에 스케일 적용
                child.scaleX = scale
                child.scaleY = scale
            }
        }
    }


















    /**
     * RecyclerView의 크기에서 중앙값을 반환
     */
    private fun getRecyclerViewCenterX(): Int = rv?.let {
        if (orientation == RecyclerView.HORIZONTAL) (it.right - it.left) / 2 + it.left
        else (it.bottom - it.top) / 2 + it.top
    } ?: 0

    private fun getDecoratedStart(view: View): Int
        = if (orientation == RecyclerView.HORIZONTAL) getDecoratedLeft(view)
          else getDecoratedTop(view)

    private fun getDecoratedEnd(view: View): Int
        = if (orientation == RecyclerView.HORIZONTAL) getDecoratedRight(view)
          else getDecoratedBottom(view)

    interface OnItemSelectedListener {
        fun onItemSelected(layoutPosition: Int)
    }



}