package com.depromeet.linkzupzup.utils

import com.depromeet.linkzupzup.presenter.model.LinkData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.net.MalformedURLException
import java.net.URISyntaxException
import java.net.URL
import java.util.regex.Matcher
import java.util.regex.Pattern

object MetaDataUtil {

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

    fun getMetaDataFromUrl(url : String) : LinkData{
        val linkData = LinkData(linkURL = url)
        try{
            val doc : Document = Jsoup.connect(url).get()
            linkData.linkTitle = doc.select("meta[property=og:title]").first().attr("content")
            linkData.description = doc.select("meta[property=og:description]")[0].attr("content")
            linkData.imgURL = doc.select("meta[property=og:image]")[0].attr("content")

            DLog.d("LinkMetaData","${linkData.linkTitle} ${linkData.description} ${linkData.imgURL}")
        }catch (e : Exception){
            e.printStackTrace()
        }
        return linkData
    }
}