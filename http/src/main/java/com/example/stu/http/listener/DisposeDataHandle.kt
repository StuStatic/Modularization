package com.example.stu.http.listener

/**
 * Created by stu on 2021/1/25.
 *
 */
class DisposeDataHandle {
    var mListener: DisposeDataListener? = null
    var mClass: Class<*>? = null
    var mSource: String? = null

    constructor(listener: DisposeDataListener?) {
        mListener = listener
    }

    constructor(listener: DisposeDataListener?, clazz: Class<*>?) {
        mListener = listener
        mClass = clazz
    }

    constructor(listener: DisposeDataListener?, source: String?) {
        mListener = listener
        mSource = source
    }
}