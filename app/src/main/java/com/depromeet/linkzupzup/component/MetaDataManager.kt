package com.depromeet.linkzupzup.component

import android.util.Log
import com.depromeet.linkzupzup.architecture.domainLayer.entities.db.LinkMetaInfoEntity
import com.depromeet.linkzupzup.architecture.presenterLayer.model.LinkData
import com.depromeet.linkzupzup.utils.DLog
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.net.MalformedURLException
import java.net.URISyntaxException
import java.net.URL
import java.util.regex.Matcher
import java.util.regex.Pattern

object MetaDataManager {

    private fun isUrl(url : String) :Boolean{

        val urlPattern : Pattern = Pattern.compile("^(?:https?:\\/\\/)?(?:www\\.)?[a-zA-Z0-9./]+$")
        val urlMatcher : Matcher = urlPattern.matcher(url)
        val checkUrl : URL

        if(urlMatcher.matches()) return true

        try {
            checkUrl = URL(url)
        } catch (e : MalformedURLException) {
            return false;
        }

        try {
            checkUrl.toURI()
        } catch (e : URISyntaxException) {
            return false
        }

        return true
    }

    fun extractUrlFormText(url : String) : String? {
        DLog.e("TEST",url)
        val urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)"
        val urlPattern : Pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE)
        val urlMatcher : Matcher = urlPattern.matcher(url)

        val urls : ArrayList<String> = arrayListOf()
        while(urlMatcher.find()){
            urls.add(url.substring(urlMatcher.start(0),urlMatcher.end(0)))
        }

        var resultUrl : String? = null
        for(extractedUrl in urls){
            if(isUrl(extractedUrl)){
                resultUrl = extractedUrl
                break
            }
        }

        return resultUrl
    }

    fun getMetaDataFromUrl(url : String) : LinkMetaInfoEntity{
        val metaData = LinkMetaInfoEntity(url = url)
        try{
            val doc : Document = Jsoup.connect(url).get()
            metaData.title = doc.select("meta[property=og:title]").first().attr("content")
            metaData.content = doc.select("meta[property=og:description]")[0].attr("content")
            metaData.imgUrl = doc.select("meta[property=og:image]")[0].attr("content")

            DLog.e(
                "TEST",
                "${metaData.title} ${metaData.content} ${metaData.imgUrl}"
            )
        }catch (e : Exception){
            e.printStackTrace()
        }
        return metaData
    }
}