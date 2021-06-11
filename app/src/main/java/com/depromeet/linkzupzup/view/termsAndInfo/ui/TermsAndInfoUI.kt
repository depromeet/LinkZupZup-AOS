package com.depromeet.linkzupzup.view.termsAndInfo.ui

import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.depromeet.linkzupzup.architecture.presenterLayer.TermsAndInfoViewModel
import com.depromeet.linkzupzup.base.BaseView
import com.depromeet.linkzupzup.component.CustomWebViewClient
import com.depromeet.linkzupzup.ui.theme.LinkZupZupTheme

class TermsAndInfoUI: BaseView<TermsAndInfoViewModel>() {

    var webView: WebView? = null

    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface(color = MaterialTheme.colors.background) {

                vm?.let { viewModel ->

                    val linkUrl = viewModel.urlToRender.value ?: "naver.com"

                    AndroidView(factory = {
                        WebView(it).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT)

                            webViewClient = CustomWebViewClient { state, url ->
                                when(state) {
                                    CustomWebViewClient.PAGE_LOAD_COMPLETE -> { }
                                    else -> { }
                                }
                            }

                            webChromeClient = WebChromeClient()

                            settings.apply {
                                javaScriptEnabled = true    // 자바스크립트 실행 허용
                                javaScriptCanOpenWindowsAutomatically = false   // 자바스크립트에서 새창 실행 허용
                                setSupportMultipleWindows(false)    // 새 창 실행 허용
                                loadWithOverviewMode = true // 메타 태그 허용
                                useWideViewPort = true  // 화면 사이즈 맞추기 허용
                                setSupportZoom(true)    // 화면 줌 허용
                                builtInZoomControls = false // 화면 확대 축소 허용 여부
                                cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK // 브라우저 캐시 허용 여부
                                domStorageEnabled = true    // 로컬저장소 허용
                            }

                            webView = this
                            loadUrl(linkUrl)
                        }
                    }, update = { it.loadUrl(linkUrl) })

                }
            }
        }
    }
}