package com.example.stu.http.listener

/**
 * Created by stu on 2021/1/25.
 *
 */
open interface DisposeDownloadListener : DisposeDataListener {
    fun onProgress(progrss: Int)
}