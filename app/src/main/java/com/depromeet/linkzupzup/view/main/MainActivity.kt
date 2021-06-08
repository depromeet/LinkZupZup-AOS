package com.depromeet.linkzupzup.view.main

import android.os.Bundle
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.base.BaseActivity
import com.depromeet.linkzupzup.architecture.presenterLayer.MainViewModel
import com.depromeet.linkzupzup.extensions.toast
import com.depromeet.linkzupzup.utils.DLog
import com.depromeet.linkzupzup.view.alarm.AlarmDetailActivity
import com.depromeet.linkzupzup.view.login.LoginActivity
import com.depromeet.linkzupzup.view.main.ui.MainUI
import com.depromeet.linkzupzup.view.mypage.MyPageActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : BaseActivity<MainUI, MainViewModel>() {

    override var view: MainUI = MainUI(this::onClickListener, pref.getUserName())
    override fun onCreateViewModel(): MainViewModel = getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewModel) {
            // 링크 리스트 호출
            getLinkList()
            getTodayLinkList()
        }
    }

    private fun onClickListener(id: Int) {
        when(id) {
            R.drawable.ic_alram -> movePageDelay(AlarmDetailActivity::class.java, 500L)
            R.drawable.ic_ranking -> toast(this@MainActivity, "랭킹")
            R.drawable.ic_mypage -> if(isLogin()) movePageDelay(MyPageActivity::class.java, 500L)
            else movePageDelay(LoginActivity::class.java, 500L, true)
        }
    }

    override fun onBackPressed() {
        // super.onBackPressed()
        backPressHandler.onBackPressed()
    }

}