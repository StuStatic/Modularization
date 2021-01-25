package com.example.stu.http.request

import java.io.FileNotFoundException
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by stu on 2021/1/24.
 * @function 封装所有的请求参数到hashmap中
 */
class RequestParams {

    public val urlParams: ConcurrentHashMap<String, String> = ConcurrentHashMap<String, String>()
    public val fileParams: ConcurrentHashMap<String, Object> = ConcurrentHashMap<String, Object>()

    /**
     * Constructs a new empty `RequestParams` instance.
     */
    constructor() {
        (null as Map<String?, String?>?)
    }

    /**
     * Constructs a new RequestParams instance containing the key/value string
     * params from the specified map.
     *
     * @param source the source key/value string map to add.
     */
    constructor(source: Map<String?, String?>?) {
        if (source != null) {
            for ((key, value) in source) {
                put(key, value)
            }
        }
    }

    /**
     * Constructs a new RequestParams instance and populate it with a single
     * initial key/value string param.
     *
     * @param key   the key name for the intial param.
     * @param value the value string for the initial param.
     */
    constructor(key: String?, value: String?) {
        (object : HashMap<String?, String?>() {
            init {
                put(key, value)
            }
        })
    }

    /**
     * Adds a key/value string pair to the request.
     *
     * @param key   the key name for the new param.
     * @param value the value string for the new param.
     */
    fun put(key: String?, value: String?) {
        if (key != null && value != null) {
            urlParams[key] = value
        }
    }

    @Throws(FileNotFoundException::class)
    fun put(key: String?, `object`: Any?) {
        if (key != null) {
            fileParams[key] = `object` as Object
        }
    }

    fun hasParams(): Boolean {
        return if (urlParams.size > 0 || fileParams.size > 0) {
            true
        } else false
    }

}