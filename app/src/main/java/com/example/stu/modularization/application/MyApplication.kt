package com.example.stu.modularization.application

import android.app.Application

/**
 * Created by stu on 2021/1/19.
 * @functiion
 * 1.程序的入口
 * 2.初始化的工作
 * 3.为整个应用的其他模块提供上下文环境
 *
 */
class MyApplication : Application() {

    private var mApplication : MyApplication? = null

    override fun onCreate() {
        super.onCreate()
        mApplication = this
    }

    fun getInstance(): MyApplication? {
        return mApplication
    }
}