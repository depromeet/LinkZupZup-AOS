package com.depromeet.linkzupzup.component

import okhttp3.OkHttpClient
import java.security.GeneralSecurityException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object SSLHelper {

    fun configureClient(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {}
            override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {}
            override fun getAcceptedIssuers(): Array<X509Certificate>? {
                return arrayOf()
            }
        }).let { certs ->

            var ctx: SSLContext? = null
            try {
                ctx = SSLContext.getInstance("TLS")
                ctx.init(null, certs, SecureRandom())
            } catch(e: GeneralSecurityException) {
                e.printStackTrace()
            }

            /**
             * 참고 : https://gist.github.com/maiconhellmann/c61a533eca6d41880fd2b3f8459c07f7
             */
            ctx?.let {
                try {
                    builder.sslSocketFactory(it.socketFactory, certs[0] as X509TrustManager)
                        .hostnameVerifier { _, _ -> true }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            return builder
        }
    }
}