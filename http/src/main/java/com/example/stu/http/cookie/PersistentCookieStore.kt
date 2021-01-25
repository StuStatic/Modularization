package com.example.stu.http.cookie

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import okhttp3.internal.and
import okio.IOException
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.HttpCookie
import java.net.URI
import java.net.URISyntaxException
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


/**
 * Created by stu on 2021/1/25.
 *
 */
class PersistentCookieStore {
    private val LOG_TAG = "PersistentCookieStore"
    private val COOKIE_PREFS = "CookiePrefsFile"
    private val COOKIE_NAME_PREFIX = "cookie_"

    private var cookies: HashMap<String, ConcurrentHashMap<String, HttpCookie>>? = null
    private var cookiePrefs: SharedPreferences? = null

    /**
     * Construct a persistent cookie store.
     *
     * @param context
     * Context to attach cookie store to
     */
    fun PersistentCookieStore(context: Context) {
        cookiePrefs = context.getSharedPreferences(COOKIE_PREFS, 0)
        cookies = HashMap<String, ConcurrentHashMap<String, HttpCookie>>()

        // Load any previously stored cookies into the store
        val prefsMap = cookiePrefs?.getAll()
        for ((key, value) in prefsMap!!) {
            if (value as String? != null && !(value as String).startsWith(COOKIE_NAME_PREFIX)) {
                val cookieNames = TextUtils.split(value as String?, ",")
                for (name in cookieNames) {
                    val encodedCookie = cookiePrefs?.getString(COOKIE_NAME_PREFIX + name, null)
                    if (encodedCookie != null) {
                        val decodedCookie: HttpCookie? = decodeCookie(encodedCookie)
                        if (decodedCookie != null) {
                            if (!cookies!!.containsKey(key)) cookies!![key] =
                                ConcurrentHashMap<String, HttpCookie>()
                            cookies!![key]!!.put(name, decodedCookie)
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun add(uri: URI, cookie: HttpCookie) {
        val name = getCookieToken(uri, cookie)

        // Save cookie into local store, or remove if expired
        if (!cookie.hasExpired()) {
            if (!cookies!!.containsKey(uri.getHost())) cookies!![uri.getHost()] =
                ConcurrentHashMap<String, HttpCookie>()
            cookies!![uri.getHost()]!!.put(name, cookie)
        } else {
            if (cookies!!.containsKey(uri.toString())) cookies!![uri.getHost()]!!.remove(name)
        }

        // Save cookie into persistent store
        val prefsWriter = cookiePrefs!!.edit()
        prefsWriter.putString(uri.getHost(), TextUtils.join(",", cookies!![uri.getHost()]!!.keys))
        prefsWriter.putString(
            COOKIE_NAME_PREFIX + name,
            encodeCookie(SerializableHttpCookie(cookie))
        )
        prefsWriter.commit()
    }

    protected fun getCookieToken(uri: URI?, cookie: HttpCookie): String {
        return cookie.getName() + cookie.getDomain()
    }

    operator fun get(uri: URI): List<HttpCookie>? {
        val ret: ArrayList<HttpCookie> = ArrayList<HttpCookie>()
        if (cookies!!.containsKey(uri.getHost())) ret.addAll(cookies!![uri.getHost()]!!.values)
        return ret
    }

    fun removeAll(): Boolean {
        val prefsWriter = cookiePrefs!!.edit()
        prefsWriter.clear()
        prefsWriter.commit()
        cookies!!.clear()
        return true
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun remove(uri: URI, cookie: HttpCookie): Boolean {
        val name = getCookieToken(uri, cookie)
        return if (cookies!!.containsKey(uri.getHost()) && cookies!![uri.getHost()]!!.containsKey(name)) {
            cookies!![uri.getHost()]!!.remove(name)
            val prefsWriter = cookiePrefs!!.edit()
            if (cookiePrefs!!.contains(COOKIE_NAME_PREFIX + name)) {
                prefsWriter.remove(COOKIE_NAME_PREFIX + name)
            }
            prefsWriter.putString(
                uri.getHost(),
                TextUtils.join(",", cookies!![uri.getHost()]!!.keys)
            )
            prefsWriter.commit()
            true
        } else {
            false
        }
    }

    fun getCookies(): List<HttpCookie>? {
        val ret: ArrayList<HttpCookie> = ArrayList<HttpCookie>()
        for (key in cookies!!.keys) ret.addAll(cookies!![key]!!.values)
        return ret
    }

    fun getURIs(): List<URI>? {
        val ret: ArrayList<URI> = ArrayList<URI>()
        for (key in cookies!!.keys) try {
            ret.add(URI(key))
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
        return ret
    }

    /**
     * Serializes Cookie object into String
     *
     * @param cookie
     * cookie to be encoded, can be null
     * @return cookie encoded as String
     */
    protected fun encodeCookie(cookie: SerializableHttpCookie?): String? {
        if (cookie == null) return null
        val os = ByteArrayOutputStream()
        try {
            val outputStream = ObjectOutputStream(os)
            outputStream.writeObject(cookie)
        } catch (e: IOException) {
            Log.d(LOG_TAG, "IOException in encodeCookie", e)
            return null
        }
        return byteArrayToHexString(os.toByteArray())
    }

    /**
     * Returns cookie decoded from cookie string
     *
     * @param cookieString
     * string of cookie as returned from http request
     * @return decoded cookie or null if exception occured
     */
    protected fun decodeCookie(cookieString: String): HttpCookie? {
        val bytes = hexStringToByteArray(cookieString)
        val byteArrayInputStream = ByteArrayInputStream(bytes)
        var cookie: HttpCookie? = null
        try {
            val objectInputStream = ObjectInputStream(byteArrayInputStream)
            cookie = (objectInputStream.readObject() as SerializableHttpCookie).getCookie()
        } catch (e: IOException) {
            Log.d(LOG_TAG, "IOException in decodeCookie", e)
        } catch (e: ClassNotFoundException) {
            Log.d(LOG_TAG, "ClassNotFoundException in decodeCookie", e)
        }
        return cookie
    }

    /**
     * Using some super basic byte array &lt;-&gt; hex conversions so we don't
     * have to rely on any large Base64 libraries. Can be overridden if you
     * like!
     *
     * @param bytes
     * byte array to be converted
     * @return string containing hex values
     */
    protected fun byteArrayToHexString(bytes: ByteArray): String? {
        val sb = StringBuilder(bytes.size * 2)
        for (element in bytes) {
            val v: Int = element and 0xff
            if (v < 16) {
                sb.append('0')
            }
            sb.append(Integer.toHexString(v))
        }
        return sb.toString().toUpperCase(Locale.US)
    }

    /**
     * Converts hex values from strings to byte arra
     *
     * @param hexString
     * string of hex-encoded values
     * @return decoded byte array
     */
    protected fun hexStringToByteArray(hexString: String): ByteArray {
        val len = hexString.length
        val data = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            data[i / 2] = ((Character.digit(hexString[i], 16) shl 4)
                    + Character.digit(hexString[i + 1], 16)).toByte()
            i += 2
        }
        return data
    }
}