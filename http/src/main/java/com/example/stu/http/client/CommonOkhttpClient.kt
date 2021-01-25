package com.example.stu.http.client

import android.net.sip.SipErrorCode.TIME_OUT
import com.example.stu.http.cookie.SimpleCookieJar
import com.example.stu.http.https.HttpsUtils
import com.example.stu.http.listener.DisposeDataHandle
import com.example.stu.http.response.CommonFileCallback
import com.example.stu.http.response.CommonJsonCallback
import okhttp3.*
import okio.IOException
import java.util.concurrent.TimeUnit


/**
 * Created by stu on 2021/1/25.
 * @function
 * 1.请求的发送
 * 2.请求参数的配置
 * 3.https的支持
 *
 */
class CommonOkhttpClient {
    //为client去配置参数
    companion object {
        private val TIME_OUT = 30//超时时间
        private var mOkHttpClient: OkHttpClient? = null

        init {
            val okHttpClientBuilder = OkHttpClient.Builder()
            okHttpClientBuilder.hostnameVerifier { hostname, session -> true }

            /**
             *  为所有请求添加请求头，看个人需求
             */
            /**
             * 为所有请求添加请求头，看个人需求
             */
            okHttpClientBuilder.addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request: Request = chain.request()
                        .newBuilder()
                        .addHeader("User-Agent", "Imooc-Mobile") // 标明发送本次请求的客户端
                        .build()
                    return chain.proceed(request)
                }
            })
            okHttpClientBuilder.cookieJar(SimpleCookieJar())
            okHttpClientBuilder.connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            okHttpClientBuilder.readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            okHttpClientBuilder.writeTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            okHttpClientBuilder.followRedirects(true)
            /**
             * trust all the https point
             */
            /**
             * trust all the https point
             */
            HttpsUtils.initSSLSocketFactory()?.let {
                okHttpClientBuilder.sslSocketFactory(
                    it,
                    HttpsUtils.initTrustManager()
                )
            }
            mOkHttpClient = okHttpClientBuilder.build()
        }


        fun getOkHttpClient(): OkHttpClient? {
            return mOkHttpClient
        }

        //    /**
        //     * 指定cilent信任指定证书
        //     *
        //     * @param certificates
        //     */
        //    public static void setCertificates(InputStream... certificates) {
        //        mOkHttpClient.newBuilder().sslSocketFactory(HttpsUtils.getSslSocketFactory(certificates, null, null)).build();
        //    }
        /**
         * 通过构造好的Request,Callback去发送请求
         *
         * @param request
         * @param callback
         */
        operator fun get(request: Request?, handle: DisposeDataHandle?): Call? {
            val call: Call? = request?.let { mOkHttpClient!!.newCall(it) }
            call?.enqueue(CommonJsonCallback(handle!!))
            return call
        }

        fun post(request: Request?, handle: DisposeDataHandle?): Call? {
            val call: Call? = request?.let { mOkHttpClient!!.newCall(it) }
            call?.enqueue(CommonJsonCallback(handle!!))
            return call
        }

        fun downloadFile(request: Request?, handle: DisposeDataHandle?): Call? {
            val call: Call? = request?.let { mOkHttpClient!!.newCall(it) }
            call?.enqueue(CommonFileCallback(handle!!))
            return call
        }
    }
}