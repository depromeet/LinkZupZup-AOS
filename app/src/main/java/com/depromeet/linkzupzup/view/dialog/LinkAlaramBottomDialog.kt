package com.depromeet.linkzupzup.view.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.TypefaceSpan
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.LinearLayout.HORIZONTAL
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.architecture.presenterLayer.ScrapDetailViewModel
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkData
import com.depromeet.linkzupzup.extensions.*
import com.depromeet.linkzupzup.utils.DLog
import com.depromeet.linkzupzup.utils.DeviceUtils
import com.depromeet.linkzupzup.view.common.adapter.DateAdapter
import com.depromeet.linkzupzup.view.common.adapter.TextSpinnerAdapter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.jetbrains.anko.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

/** 이렇게도 호출 가능
BottomSheetDialog(context, R.style.CustomBottomSheetDialog).apply {
    setContentView(context.UI {
        verticalLayout {
            val sheetHeight = (DeviceUtils.getDeviceSize(context)?.y ?: dip(667)) - dip(52)
            lparams(width= matchParent, height= sheetHeight)
            gravity = Gravity.CENTER

            textView("test")
        }
    }.view)
}.show()
*/

class LinkAlaramBottomDialog(private val viewModel: ()->ScrapDetailViewModel) : BottomSheetDialogFragment(), View.OnClickListener {

    companion object {
        const val ALARM_REGIST_TYPE = 23121
        const val ALARM_UPDATE_TYPE = ALARM_REGIST_TYPE.plus(1)
    }

    /**
     * 숨김: BottomSheetBehavior.STATE_HIDDEN
     * 펼침: BottomSheetBehavior.STATE_EXPANDED
     */
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>

    private var state: Int = ALARM_REGIST_TYPE

    private var calendar: Calendar = Calendar.getInstance().clearMillis()

    lateinit var linkData: LinkData

    lateinit var mAlarmTimeTv: TextView

    lateinit var rv: RecyclerView

    private lateinit var dateAdapter: DateAdapter

    lateinit var mLinkSaveBtn: Button

    fun setDraggable(isDragg: Boolean) {
        (dialog as? BottomSheetDialog)?.let { d ->
            d.behavior.isDraggable = isDragg
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        with(viewModel()) {
            linkInfo.value?.let {
                linkData = it

                if (it.alarmDt != null) {
                    // 수정
                    state = ALARM_UPDATE_TYPE
                    calendar = calendar.apply { time = it.alarmDt }
                } else {
                    // 등록
                    state = ALARM_REGIST_TYPE
                    calendar = Calendar.getInstance().clearMillis()
                }
            }
        }

        return super.onCreateDialog(savedInstanceState).apply {
            (this as BottomSheetDialog).behavior
                .also { bottomSheetBehavior = it }
                .addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_EXPANDED -> DLog.e("BOTTOM_SHEET", "[$newState] 열림")
                        else -> DLog.e("BOTTOM_SHEET", "[$newState] 닫힘")
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    // Slide ...
                    // 1이면 완전 펼쳐진 상태
                    // 0이면 peekHeight인 상태
                    // -1이면 숨김 상태
                    DLog.e("BOTTOM_SHEET", "slideOffset $slideOffset");
                }
            })

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCalendar(calendar)
        dateAdapter.notifyDataSetChanged()
    }

    @ExperimentalPagerApi
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
                    setOnClickListener(this@LinkAlaramBottomDialog)

                    imageView(R.drawable.ic_close)
                        .lparams(width= dip(24), height= dip(24))

                }.lparams(width= dip(72), height= dip(56))

            }.lparams(width= matchParent, height = dip(56))

            textView("이 아티클은\n언제 읽으실건가요?") {

                typeface = ResourcesCompat.getFont(context, R.font.spoqa_hansansneo_bold)
                setTextSize(TypedValue.COMPLEX_UNIT_PX, dip(24).toFloat())
                // setLineSpacing(resources.getDimension(fontSet.textLineHeightRes), 1.0f)
                textColor = Color.parseColor("#292A2B")

            }.lparams(width= wrapContent, height= wrapContent) {
                horizontalMargin = dip(24)
                bottomMargin = dip(8)
            }

            mAlarmTimeTv = textView("4월 10일 오전 12:50분에 알림이 울려요!") {

                typeface = ResourcesCompat.getFont(context, R.font.spoqa_hansansneo_bold)
                setTextSize(TypedValue.COMPLEX_UNIT_PX, dip(12).toFloat())
                // setLineSpacing(resources.getDimension(fontSet.textLineHeightRes), 1.0f)
                textColor = Color.parseColor("#292A2B")

            }.lparams(width= wrapContent, height= wrapContent) {
                horizontalMargin = dip(24)
                bottomMargin = dip(32)
            }

            rv = recyclerView {
                clipToPadding = false
                verticalPadding = dip(20)
                horizontalPadding = dip(24)
                backgroundColor = Color.parseColor("#F8FAFB")
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                adapter = DateAdapter(requireContext()).apply {
                    setOnClickListener(this@LinkAlaramBottomDialog::pickDate)
                }.also { dateAdapter = it }
            }.lparams(width= matchParent, height= wrapContent) {
                bottomMargin = dip(20)
            }

            /**
             * TODO: 데이트 피커
             * RecyclerView.Adapter로 어뎁터 사용
             * https://dudmy.net/android/2019/03/02/try-viewpager2-simply/
             */

            linearLayout {
                orientation = HORIZONTAL
                verticalPadding = dip(20)
                horizontalPadding = dip(24)
                gravity = Gravity.CENTER

                /**
                 * AM or PM
                 */
                customTextPicker {
                    backgroundColor = Color.TRANSPARENT

                    // value = 0 // position
                    initData(arrayListOf("\uD83C\uDF19 오후", "\u2600\uFE0F 오전"))
                    setOnValueChangeListener { picker, beforeIdx, afterIdx ->
                        DLog.e("TEXT_PICKER", "beforeIdx: $beforeIdx, afterIdx: $afterIdx")
                    }

                }.lparams(width= dip(92), height= matchParent)

                space().lparams(height= dip(1), weight = 1f)

                /**
                 * HOUR
                 */
                customTextPicker {
                    backgroundColor = Color.TRANSPARENT

                    // value = 0 // position
                    initData(arrayListOf<String>().apply {
                        (1..12).forEach { i -> add(String.format("%02d", i)) }
                    })
                    setOnValueChangeListener { picker, beforeIdx, afterIdx ->
                        DLog.e("TEXT_PICKER", "beforeIdx: $beforeIdx, afterIdx: $afterIdx")
                    }

                }.lparams(width= dip(56), height= matchParent)

                space().lparams(width= dip(32))

                /**
                 * MINUTE
                 */
                customTextPicker {
                    backgroundColor = Color.TRANSPARENT

                    // value = 0 // position
                    initData(arrayListOf<String>().apply {
                        (0..5).forEach { i -> add("${i}0") }
                    })
                    setOnValueChangeListener { picker, beforeIdx, afterIdx ->
                        DLog.e("TEXT_PICKER", "beforeIdx: $beforeIdx, afterIdx: $afterIdx")
                    }

                }.lparams(width= dip(56), height= matchParent)

            }.lparams(width= matchParent, height= dip(210))


            /**
             * 저장하기 버튼
             */
            mLinkSaveBtn = button("저장하기") {
                id = R.id.alarm_save
                setOnClickListener(this@LinkAlaramBottomDialog)
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

    /**
     * BottomSheet 상단에 명시되는 시간 변경
     */
    private fun setCalendar(cal: Calendar = Calendar.getInstance().clearMillis()) {
        calendar = cal

        val dateStr = cal.getAlarmDateStr()
        val dateLastStr = "에 알림이 울려요!"
        val alarmDateStr = "${dateStr}${dateLastStr}"

        SpannableStringBuilder(alarmDateStr).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                setSpan(
                    TypefaceSpan(Typeface.create(ResourcesCompat.getFont(requireContext(), R.font.spoqa_hansansneo_medium), Typeface.BOLD)),
                    0,
                    dateStr.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                setSpan(
                    TypefaceSpan(Typeface.create(ResourcesCompat.getFont(requireContext(), R.font.spoqa_hansansneo_medium), Typeface.NORMAL)),
                    dateStr.length,
                    alarmDateStr.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }.let { alarmTimeStr ->
            mAlarmTimeTv.text = alarmTimeStr
        }
    }

    /**
     * 데이트 피커, 클릭 리스너
     */
    private fun pickDate(position: Int, date: Pair<String, Calendar>) {
        setCalendar(date.second)
        dateAdapter.notifyDataSetChanged()
    }

    override fun onClick(view: View?) = with(viewModel) {
        when(view?.id) {
            R.id.dialog_close -> bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            R.id.alarm_save -> bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            else -> {}
        }
    }

}