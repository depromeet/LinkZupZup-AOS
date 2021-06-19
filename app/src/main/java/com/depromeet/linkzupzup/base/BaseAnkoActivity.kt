package com.depromeet.linkzupzup.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.component.BackPressCloseHandler
import com.depromeet.linkzupzup.component.NetworkProgressDialog
import com.depromeet.linkzupzup.component.PreferencesManager
import com.depromeet.linkzupzup.extensions.toast
import com.depromeet.linkzupzup.utils.DLog
import io.reactivex.Observable
import org.koin.android.ext.android.inject
import java.util.*
import java.util.concurrent.TimeUnit

abstract class BaseAnkoActivity<VIEW: BaseAnkoView<VIEWMODEL>, VIEWMODEL: BaseViewModel>: ComponentActivity() {

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
            lifecycleOwner = this@BaseAnkoActivity
            // 프로그래스 동작 status 구독
            progressStatus.observe(this@BaseAnkoActivity, Observer {
                DLog.e("Jackson", "progressStatus : $it")
                if (it) onVisibleProgress() else onInvisibleProgress()
            })

            this.isLogin = this@BaseAnkoActivity.isLogin
            this.getContext = this@BaseAnkoActivity.getContext
            this.getIntent = this@BaseAnkoActivity.getIntent
            this.movePageDelay = this@BaseAnkoActivity.movePageDelay
            this.movePage = this@BaseAnkoActivity.movePage
            this.toast = this@BaseAnkoActivity.toast

            this.movePageForResult = this@BaseAnkoActivity.movePageForResult
            this.movePageForResultDelay = this@BaseAnkoActivity.movePageForResultDelay
            this.openActivityForResult = this@BaseAnkoActivity.openActivityForResult
        }
        with(view) {
            lifecycleOwner = this@BaseAnkoActivity
            vm = viewModel
            setContentView(createView(org.jetbrains.anko.AnkoContext.create(this@BaseAnkoActivity, this@BaseAnkoActivity)))
        }

        mProgressView = NetworkProgressDialog.getInstance(this@BaseAnkoActivity)
        backPressHandler = BackPressCloseHandler(this@BaseAnkoActivity)
    }

    var isLogin: ()-> Boolean = { pref.isLogin() }
    var getContext: ()-> Context? = { this@BaseAnkoActivity }
    var getIntent: (Class<*>)->Intent = { cls -> Intent(this@BaseAnkoActivity, cls) }
    var movePageDelay: (intent: Intent, time: Long, isFinish: Boolean)->Unit = { intent, time, isFinish -> movePageDelay(intent, time, isFinish) }
    var movePage: (intent: Intent, isFinish: Boolean)->Unit = { intent, isFinish -> movePage(intent, isFinish) }
    var toast: (msg: String) -> Toast = { msg -> toast(this@BaseAnkoActivity, msg) }

    var movePageForResult: (Intent, Boolean) -> Unit = { intent, isFinish -> movePageForResult(intent, isFinish) }
    var movePageForResultDelay: (Intent, Long, Boolean) -> Unit = { intent, time, isFinish -> movePageForResultDelay(intent, time, isFinish) }
    var openActivityForResult: (Intent) -> Unit = { intent -> openActivityForResult(intent) }

    fun movePageDelay(intent: Intent, time: Long = 300L, isFinish: Boolean = false) {
        Observable.timer(time, TimeUnit.MILLISECONDS)
            .subscribe { movePage(intent, isFinish) }
    }
    fun movePageDelay(cls: Class<*>, time: Long = 300L, isFinish: Boolean = false) {
        Observable.timer(time, TimeUnit.MILLISECONDS)
            .subscribe { movePage(cls, isFinish) }
    }
    fun movePage(cls: Class<*>, isFinish: Boolean = false) {
        movePage(Intent(this@BaseAnkoActivity, cls), isFinish)
    }
    fun movePage(intent: Intent, isFinish: Boolean = false) {
        intent.let(this::startActivity)
        if (isFinish) finish()
    }

    var startForResult: ActivityResultLauncher<Intent>? = null
    fun movePageForResult(intent: Intent, isFinish: Boolean = false) {
        openActivityForResult(intent)
        if (isFinish) finish()
    }
    fun movePageForResultDelay(intent: Intent, time: Long = 300L, isFinish: Boolean = false) {
        Observable.timer(time, TimeUnit.MILLISECONDS)
            .subscribe { movePageForResult(intent, isFinish) }
    }
    fun openActivityForResult(intent: Intent) {
        startForResult?.launch(intent)
    }

    private fun toast(msg: String): Toast {
        return toast(this@BaseAnkoActivity, msg)
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
        overridePendingTransition(R.anim.stay, R.anim.act_slide_right_out)
        super.onBackPressed()
    }

}