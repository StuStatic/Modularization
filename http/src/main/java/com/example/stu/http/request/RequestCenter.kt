package com.example.stu.http.request

import com.example.stu.http.client.CommonOkhttpClient
import com.example.stu.http.listener.DisposeDataHandle

import com.example.stu.http.listener.DisposeDataListener

/**
 * Created by stu on 2021/1/25.
 *
 */
class RequestCenter {
    /**
     * 发送广告请求
     */
    fun sendImageAdRequest(url: String?, listener: DisposeDataListener?) {
        //stu:2021.1.25日注释，因为缺少AdInstance
//        CommonOkhttpClient.post(
//            CommonRequest.createPostRequest(url, null),
//            DisposeDataHandle(listener, AdInstance::class.java)
//        )
    }
}