package com.example.stu.http.cookie

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * Created by stu on 2021/1/25.
 *
 */
class SimpleCookieJar : CookieJar {
    private val allCookies: MutableList<Cookie> = ArrayList()

    @Synchronized
    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val result: MutableList<Cookie> = ArrayList()
        for (cookie in allCookies) {
            if (cookie.matches(url!!)) {
                result.add(cookie)
            }
        }
        return result
    }

    @Synchronized
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        allCookies.addAll(cookies!!)
    }
}