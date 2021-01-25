package com.example.stu.http.cookie

import okio.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.net.HttpCookie

/**
 * Created by stu on 2021/1/25.
 *
 */
class SerializableHttpCookie : Serializable{
    private val serialVersionUID = 6374381323722046732L

    @Transient
    private var cookie: HttpCookie? = null

    @Transient
    private var clientCookie: HttpCookie? = null

    constructor(cookie: HttpCookie?) {
        this.cookie = cookie
    }

    fun getCookie(): HttpCookie? {
        var bestCookie: HttpCookie? = cookie
        if (clientCookie != null) {
            bestCookie = clientCookie
        }
        return bestCookie
    }

    @Throws(IOException::class)
    private fun writeObject(out: ObjectOutputStream) {
        out.writeObject(cookie?.getName())
        out.writeObject(cookie?.getValue())
        out.writeObject(cookie?.getComment())
        out.writeObject(cookie?.getCommentURL())
        out.writeObject(cookie?.getDomain())
        cookie?.getMaxAge()?.let { out.writeLong(it) }
        out.writeObject(cookie?.getPath())
        out.writeObject(cookie?.getPortlist())
        cookie?.getVersion()?.let { out.writeInt(it) }
        cookie?.getSecure()?.let { out.writeBoolean(it) }
        cookie?.getDiscard()?.let { out.writeBoolean(it) }
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    private fun readObject(`in`: ObjectInputStream) {
        val name = `in`.readObject() as String
        val value = `in`.readObject() as String
        clientCookie = HttpCookie(name, value)
        clientCookie?.setComment(`in`.readObject() as String)
        clientCookie?.setCommentURL(`in`.readObject() as String)
        clientCookie?.setDomain(`in`.readObject() as String)
        clientCookie?.setMaxAge(`in`.readLong())
        clientCookie?.setPath(`in`.readObject() as String)
        clientCookie?.setPortlist(`in`.readObject() as String)
        clientCookie?.setVersion(`in`.readInt())
        clientCookie?.setSecure(`in`.readBoolean())
        clientCookie?.setDiscard(`in`.readBoolean())
    }
}