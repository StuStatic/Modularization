package com.example.stu.http.exception

/**
 * Created by stu on 2021/1/25.
 *
 */
class OkHttpException (ecode: Int, emsg: Any) : Exception() {
    /**
     * the server return code
     */
    private val ecode: Int

    /**
     * the server return error message
     */
    private val emsg: Any
    fun getEcode(): Int {
        return ecode
    }

    fun getEmsg(): Any {
        return emsg
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        this.ecode = ecode
        this.emsg = emsg
    }
}