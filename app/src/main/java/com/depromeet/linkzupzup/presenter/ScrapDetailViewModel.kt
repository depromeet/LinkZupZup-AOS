package com.depromeet.linkzupzup.presenter

import com.depromeet.linkzupzup.base.BaseViewModel
import com.depromeet.linkzupzup.domains.ScrapUseCases
import com.depromeet.linkzupzup.presenter.model.TagColor
import com.depromeet.linkzupzup.utils.CommonUtil
import io.reactivex.Single

class ScrapDetailViewModel(private val scrapUseCases: ScrapUseCases): BaseViewModel() {

    companion object {
        val TAG = ScrapDetailViewModel::class.java.simpleName
    }

    fun getTagList(): ArrayList<String> {
        return scrapUseCases.getTagList(-1)
    }

    fun getRandomColor(): Single<TagColor> {
        return Single.just(CommonUtil.getRandomeTagColor())
    }

}