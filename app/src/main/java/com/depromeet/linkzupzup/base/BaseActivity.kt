package com.depromeet.linkzupzup.base

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.component.BackPressCloseHandler
import com.depromeet.linkzupzup.component.NetworkProgressDialog
import com.depromeet.linkzupzup.component.PreferencesManager
import com.depromeet.linkzupzup.utils.DLog
import io.reactivex.Observable
import org.koin.android.ext.android.inject
import java.util.*
import java.util.concurrent.TimeUnit

abstract class BaseActivity<VIEW: BaseView<VIEWMODEL>, VIEWMODEL: BaseViewModel>: ComponentActivity() {

    companion object {
        const val DEFAULT_PROGRESS_TIME: Long = 1000L
    }

    lateinit var mProgressView: AlertDialog
    lateinit var backPressHandler: BackPressCloseHandler
    val pref: PreferencesManager by inject()

    abstract var view: VIEW
    lateinit var viewModel: VIEWMODEL
    abstract fun onCreateViewModel(): VIEWMODEL

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.act_slide_right_in, R.anim.stay)
        super.onCreate(savedInstanceState)
        // top status bar color
        window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar_color)
        // bottom navigation bar color
        window.navigationBarColor = ContextCompat.getColor(this, R.color.navigation_bar_color)

        // TimeZone Asia/Seoul로 지정
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
        viewModel = onCreateViewModel().apply {
            preference = pref
            lifecycleOwner = this@BaseActivity
            // 프로그래스 동작 status 구독
            progressStatus.observe(this@BaseActivity, Observer {
                DLog.e("Jackson", "progressStatus : $it")
                if (it) onVisibleProgress() else onInvisibleProgress()
            })
        }
        with(view) {
            lifecycleOwner = this@BaseActivity
            vm = viewModel
            setContent { onCreateViewContent() }
        }

        mProgressView = NetworkProgressDialog.getInstance(this@BaseActivity)
        backPressHandler = BackPressCloseHandler(this@BaseActivity)
    }

    fun isLogin(): Boolean = pref.isLogin()

    fun movePageDelay(cls: Class<*>, time: Long = 300L, isFinish: Boolean = false) {
        Observable.timer(time, TimeUnit.MILLISECONDS)
            .subscribe { movePage(cls, isFinish) }
    }
    private fun movePage(cls: Class<*>, isFinish: Boolean = false) {
        Intent(this@BaseActivity, cls).let(this::startActivity)
        if (isFinish) finish()
    }

    fun onVisibleProgress() {
        DLog.e("Jackson", "onVisibleProgress")
        mProgressView.show()
    }

    fun onInvisibleProgress() {
        DLog.e("Jackson", "onInvisibleProgress")
        mProgressView.let {
            if (it.isShowing) Handler(Looper.getMainLooper()).postDelayed({
                DLog.e("Jackson", "dismiss")
                it.dismiss()
            }, DEFAULT_PROGRESS_TIME)
        }
    }

    override fun onBackPressed() {
        overridePendingTransition(R.anim.stay, R.anim.slide_down_out)
        super.onBackPressed()
    }

}