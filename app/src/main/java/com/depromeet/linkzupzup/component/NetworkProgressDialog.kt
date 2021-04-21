package com.depromeet.linkzupzup.component

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

object NetworkProgressDialog {

    val llPadding = 30

    /**
     * 임시
     */
    fun getInstance(ctx: Context): AlertDialog = with(ctx) {
        LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(llPadding, llPadding, llPadding, llPadding)
            gravity = Gravity.CENTER

            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).let { llParam ->
                llParam.gravity = Gravity.CENTER
                layoutParams = llParam
            }

        }.let { ll ->
            ProgressBar(this).apply {
                isIndeterminate = true
                setPadding(0, 0, llPadding, 0)

                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).let { llParam ->
                    llParam.gravity = Gravity.CENTER
                    layoutParams = llParam
                }
            }.let { progressBar ->

                TextView(this).apply {
                    text = "Loading ..."
                    setTextColor(Color.parseColor("#000000"))
                    textSize = 20f

                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).let { llParam ->
                        llParam.gravity = Gravity.CENTER
                        layoutParams = llParam
                    }
                }.let { tvText ->

                    ll.addView(progressBar)
                    ll.addView(tvText)

                    return AlertDialog.Builder(this).apply {
                        setCancelable(true)
                        setView(ll)
                    }.create().also { dialog ->

                        dialog.window?.let { window ->
                            WindowManager.LayoutParams().apply {
                                copyFrom(window.attributes)
                                width = LinearLayout.LayoutParams.WRAP_CONTENT
                                height = LinearLayout.LayoutParams.WRAP_CONTENT
                            }.let { layoutParams ->
                                window.attributes = layoutParams
                            }
                        }
                    }
                }
            }
        }
    }
}