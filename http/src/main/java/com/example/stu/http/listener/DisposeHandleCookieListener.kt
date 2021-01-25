package com.example.stu.http.listener

/**
 * Created by stu on 2021/1/25.
 *
 */
open interface DisposeHandleCookieListener : DisposeDataListener {
    fun onCookie(cookieStrLists: ArrayList<String?>?)
}