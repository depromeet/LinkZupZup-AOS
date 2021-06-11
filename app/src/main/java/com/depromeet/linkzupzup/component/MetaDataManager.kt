package com.depromeet.linkzupzup.component

import com.depromeet.linkzupzup.architecture.domainLayer.entities.db.LinkMetaInfoEntity
import com.depromeet.linkzupzup.extensions.verifyImgUrlDomain
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
            metaData.title = doc.select("meta[property=og:title]")?.first()?.attr("content") ?: ""
            metaData.content = doc.select("meta[property=og:description]")?.first()?.attr("content") ?: ""
            metaData.imgUrl = doc.select("meta[property=og:image]")?.first()?.attr("content")?.verifyImgUrlDomain() ?: ""
            metaData.author = doc.select("meta[property=og:site_name]")?.first()?.attr("content") ?: ""

            DLog.e("MetaData Manager", "글쓴이 : ${metaData.author}")

            // brunch, Medium 두 가지 사이트에 대해서만 저자 정보를 따로 저장해주고, 나머지는 사이트명으로 대체합니다.
            metaData.author = when(metaData.author){
                "brunch" -> doc.select("meta[name=byl]")[0].attr("content")
                "Medium" -> doc.select("meta[name=author]")[0].attr("content")
                else -> metaData.author
            }

        }catch (e : Exception){
            e.printStackTrace()
        }
        return metaData
    }
}