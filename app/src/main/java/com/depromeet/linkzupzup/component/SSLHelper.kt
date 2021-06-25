package com.depromeet.linkzupzup.component

import okhttp3.OkHttpClient
import java.security.GeneralSecurityException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*

object SSLHelper {

    fun configureClient(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {}
            override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        }).let { certs ->

            var ctx: SSLContext? = null
            try {
                ctx = SSLContext.getInstance("TLS")
                ctx.init(null, certs, SecureRandom())

                /**
                 * 참고 : https://gist.github.com/maiconhellmann/c61a533eca6d41880fd2b3f8459c07f7
                 */
                try {
                    builder.sslSocketFactory(ctx.socketFactory, certs[0] as X509TrustManager)
                        .hostnameVerifier { _, _ -> true }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } catch(e: GeneralSecurityException) {
                e.printStackTrace()
            }

            return builder
        }
    }

    fun initSSL() = arrayOf<TrustManager>(object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) { }
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) { }
        }).let { trustAllCerts ->
            with(SSLContext.getInstance("SSL")) {
                init(null, trustAllCerts, SecureRandom())
                HttpsURLConnection.setDefaultSSLSocketFactory(socketFactory)
                HttpsURLConnection.setDefaultHostnameVerifier { _, _ -> true }
            }
        }

}