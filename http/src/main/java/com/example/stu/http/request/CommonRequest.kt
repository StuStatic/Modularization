package com.example.stu.http.request

import okhttp3.*
import okhttp3.Headers.Companion.headersOf
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.File


/**
 * Created by stu on 2021/1/24.
 * @function 接收请求参数，生成Request对象
 *
 */
class CommonRequest {
    /**
     * create the key-value Request
     *
     * @param url
     * @param params
     * @return
     */
    companion object{
        fun createPostRequest(url: String?, params: RequestParams?): Request? {
            return createPostRequest(url, params, null)
        }

        /**可以带请求头的Post请求
         * @param url
         * @param params
         * @param headers
         * @return
         */
        fun createPostRequest(url: String?, params: RequestParams?, headers: RequestParams?): Request? {
            val mFormBodyBuild = FormBody.Builder()
            if (params != null) {
                for ((key, value) in params.urlParams) {
                    //将请求参数逐一遍历添加到请求构建类中
                    mFormBodyBuild.add(key, value)
                }
            }
            //添加请求头
            val mHeaderBuild: Headers.Builder = Headers.Builder()
            if (headers != null) {
                for ((key, value) in headers.urlParams) {
                    mHeaderBuild.add(key, value)
                }
            }
            val mFormBody = mFormBodyBuild.build()
            val mHeader: Headers = mHeaderBuild.build()
            return Request.Builder().url(url!!).post(mFormBody).headers(mHeader)
                .build()
        }

        /**
         * ressemble the params to the url
         *
         * @param url
         * @param params
         * @return
         */
        fun createGetRequest(url: String?, params: RequestParams?): Request? {
            return createGetRequest(url, params, null)
        }

        /**
         * 可以带请求头的Get请求
         * @param url
         * @param params
         * @param headers
         * @return
         */
        fun createGetRequest(url: String?, params: RequestParams?, headers: RequestParams?): Request? {
            val urlBuilder = StringBuilder(url).append("?")
            if (params != null) {
                for ((key, value) in params.urlParams) {
                    urlBuilder.append(key).append("=").append(value).append("&")
                }
            }
            //添加请求头
            val mHeaderBuild: Headers.Builder = Headers.Builder()
            if (headers != null) {
                for ((key, value) in headers.urlParams) {
                    mHeaderBuild.add(key, value)
                }
            }
            val mHeader: Headers = mHeaderBuild.build()
            return Request.Builder().url(urlBuilder.substring(0, urlBuilder.length - 1))
                .get()
                .headers(mHeader)
                .build()
        }

        /**
         * @param url
         * @param params
         * @return
         */
        fun createMonitorRequest(url: String?, params: RequestParams?): Request? {
            val urlBuilder = StringBuilder(url).append("&")
            if (params != null && params.hasParams()) {
                for ((key, value) in params.urlParams) {
                    urlBuilder.append(key).append("=").append(value).append("&")
                }
            }
            return Request.Builder().url(urlBuilder.substring(0, urlBuilder.length - 1)).get().build()
        }

        /**
         * 文件上传请求
         *
         * @return
         */
        private val FILE_TYPE: MediaType? = "application/octet-stream".toMediaTypeOrNull()

        fun createMultiPostRequest(url: String?, params: RequestParams?): Request? {
            val requestBody = MultipartBody.Builder()
            requestBody.setType(MultipartBody.FORM)
            if (params != null) {
                for ((key, value) in params.fileParams) {
                    if (value is File) {
                        requestBody.addPart(
                            headersOf("Content-Disposition", "form-data; name=\"$key\""),
                            RequestBody.create(FILE_TYPE, value as File)
                        )
                    } else if (value is CharSequence) { //CharSequence(String in java)
                        requestBody.addPart(
                            headersOf("Content-Disposition", "form-data; name=\"$key\""),
                            RequestBody.create(null, (value as String))
                        )
                    }
                }
            }
            return url?.let { Request.Builder().url(it).post(requestBody.build()).build() }
        }
    }
}