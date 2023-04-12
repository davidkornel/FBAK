package com.example.userappify.deconding_utils

import android.security.keystore.KeyProperties
import android.security.keystore.KeyProtection
import android.util.Log
import com.example.userappify.model.NamedProduct
import java.io.ByteArrayInputStream
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import java.security.PublicKey
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.*
import javax.crypto.Cipher


private var pub: PublicKey? = null


private fun importPublicKey() {
    Log.d("APP","IMPORT PUBLIC KEY")
    try {
        val cert = CertificateFactory.getInstance("X509").generateCertificate(
            ByteArrayInputStream(Constants.THE_CERT.toByteArray(StandardCharsets.UTF_8))
        ) as X509Certificate
        Log.d("APP","PUB KEY IMPORTED")
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        val keyProtection =
            KeyProtection.Builder(KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_VERIFY)
                .setDigests(KeyProperties.DIGEST_SHA1, KeyProperties.DIGEST_SHA256)
                .setRandomizedEncryptionRequired(true)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_OAEP)
                .setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PSS)
                .setUserAuthenticationRequired(false)
                .build()
        keyStore.load(null)
        keyStore.setEntry("AcmeKey", KeyStore.TrustedCertificateEntry(cert), keyProtection)
        Log.d("APP","ACME KEY SET")
        pub = cert.publicKey
    } catch (e: java.lang.Exception) {
        throw RuntimeException(e)
    }
}



fun readKey() {
    Log.d("APP","Trying to read the key")
    try {
        val cert = KeyStore.getInstance(Constants.ANDROID_KEYSTORE).run {
            load(null)
            Log.d("APP","Trying to GET the cert")
            getCertificate(Constants.serversKeyName)
        }
        Log.d("APP","Got certificate")
        if (cert != null) {
            Log.d("APP","Certificate NOT NULL")
            pub = cert.publicKey
        } else {
            Log.d("APP","Invoke importPublicKey")
            importPublicKey()
        }
    } catch (e: Exception) { e
        Log.d("APP",e.message.toString())
    }
}

fun decodeQRCODE(encTag: ByteArray): NamedProduct? {
    val clearTextTag: ByteArray

    try {
        clearTextTag = Cipher.getInstance(Constants.ENC_ALGO).run {
            init(Cipher.DECRYPT_MODE, pub)
            doFinal(encTag)
        }
    } catch (e: Exception) {
        println(e.message)
        return null
    }

    val tag = ByteBuffer.wrap(clearTextTag)
    val tId = tag.int
    val id = UUID(tag.long, tag.long)
    val euros = tag.int
    val cents = tag.int
    val bName = ByteArray(tag.get().toInt())
    tag[bName]
    val name = String(bName, StandardCharsets.ISO_8859_1)
    val strCents = String.format("%02d", cents)
    val price = "${euros}.${cents}".toDouble()

    return NamedProduct(id,price,name)

}