package com.example.stu.http.https

import okio.IOException
import java.io.InputStream
import java.lang.AssertionError
import java.security.*
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*
import javax.security.cert.CertificateException

/**
 * Created by stu on 2021/1/25.
 *
 */
class HttpsUtils {
    fun getSslSocketFactory(certificates: InputStream, bksFile: InputStream?, password: String?
    ): SSLSocketFactory? {
        return try {
            val trustManagers: Array<TrustManager>? = prepareTrustManager(certificates)
            val keyManagers: Array<KeyManager>? = prepareKeyManager(bksFile, password)
            val sslContext: SSLContext = SSLContext.getInstance("SSL")
            sslContext.init(
                keyManagers,
                arrayOf(MyTrustManager(chooseTrustManager(trustManagers))),
                SecureRandom()
            )
            sslContext.getSocketFactory()
        } catch (e: NoSuchAlgorithmException) {
            throw AssertionError(e)
        } catch (e: KeyManagementException) {
            throw AssertionError(e)
        } catch (e: KeyStoreException) {
            throw AssertionError(e)
        }
    }

    private fun prepareTrustManager(vararg certificates: InputStream): Array<TrustManager>? {
        if (certificates == null || certificates.size <= 0) return null
        try {
            val certificateFactory: CertificateFactory = CertificateFactory.getInstance("X.509")
            val keyStore: KeyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(null)
            var index = 0
            for (certificate in certificates) {
                val certificateAlias = Integer.toString(index++)
                keyStore.setCertificateEntry(
                    certificateAlias,
                    certificateFactory.generateCertificate(certificate)
                )
                try {
                    if (certificate != null) certificate.close()
                } catch (e: IOException) {
                }
            }
            var trustManagerFactory: TrustManagerFactory? = null
            trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(keyStore)
            return trustManagerFactory.getTrustManagers()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: CertificateException) {
            e.printStackTrace()
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun prepareKeyManager(bksFile: InputStream?, password: String?): Array<KeyManager>? {
        try {
            if (bksFile == null || password == null) return null
            val clientKeyStore: KeyStore = KeyStore.getInstance("BKS")
            clientKeyStore.load(bksFile, password.toCharArray())
            val keyManagerFactory: KeyManagerFactory = KeyManagerFactory
                .getInstance(KeyManagerFactory.getDefaultAlgorithm())
            keyManagerFactory.init(clientKeyStore, password.toCharArray())
            return keyManagerFactory.getKeyManagers()
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: UnrecoverableKeyException) {
            e.printStackTrace()
        } catch (e: CertificateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun chooseTrustManager(trustManagers: Array<TrustManager>?): X509TrustManager? {
        for (trustManager in trustManagers!!) {
            if (trustManager is X509TrustManager) {
                return trustManager as X509TrustManager
            }
        }
        return null
    }

    inner class MyTrustManager(localTrustManager: X509TrustManager?) :
        X509TrustManager {
        private val defaultTrustManager: X509TrustManager
        private val localTrustManager: X509TrustManager?
        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate?>?, authType: String?) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate?>?, authType: String?) {
            try {
                defaultTrustManager.checkServerTrusted(chain, authType)
            } catch (ce: CertificateException) {
                localTrustManager?.checkServerTrusted(chain, authType)
            }
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            TODO("Not yet implemented")
        }

        val acceptedIssuers: Array<Any?>
            get() = arrayOfNulls(0)

        init {
            val var4: TrustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            var4.init(null as KeyStore?)
            defaultTrustManager = chooseTrustManager(var4.getTrustManagers())!!
            this.localTrustManager = localTrustManager
        }
    }

    companion object {
        fun initSSLSocketFactory(): SSLSocketFactory? {
            var sslContext: SSLContext? = null
            try {
                sslContext = SSLContext.getInstance("SSL")
                val xTrustArray: Array<X509TrustManager> =
                    arrayOf<X509TrustManager>(initTrustManager())
                sslContext.init(
                    null,
                    xTrustArray, SecureRandom()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return sslContext?.getSocketFactory()
        }

        fun initTrustManager(): X509TrustManager {
            return object : X509TrustManager {
                val acceptedIssuers: Array<Any?>?
                    get() = arrayOf()

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    TODO("Not yet implemented")
                }

                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }
            }
        }
    }
}
