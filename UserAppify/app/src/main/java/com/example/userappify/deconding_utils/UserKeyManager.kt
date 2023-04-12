package com.example.userappify.deconding_utils

import android.content.Context
import android.security.KeyPairGeneratorSpec
import android.util.Base64
import android.util.Log
import java.math.BigInteger
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.Signature
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.*
import javax.crypto.Cipher
import javax.security.auth.x500.X500Principal


/*
 *    Constant strings
 */
object InStrings {
    const val haveKeys = "generated"
    const val notHaveKeys = "not generated"
    const val beginCert = "-----BEGIN CERTIFICATE-----\n"
    const val endCert = "-----END CERTIFICATE-----\n"
    const val showKeysFormat = "Modulus(%d):\n%s\nExponent: %s\nPrivate Exponent(%d):\n%s"
    const val contentFormat = "Content(%d):\n%s"
    const val encFormat = "Encrypted(%d):\n%s"
    const val decFormat = "Decrypted(%s):\n%s"
    const val signFormat = "Signature(%s):\n%s"
    const val certFormat = "(DER:%d):\n%s\n\nPEM(b64:%d):\n%s\n%s"
}

/*
 *     General Utility Functions
 */
fun byteArrayToHex(ba: ByteArray): String {
    val sb = StringBuilder(ba.size * 2)
    for (b in ba) sb.append(String.format("%02x", b))
    return sb.toString()
}

data class PubKey(var modulus: ByteArray, var exponent: ByteArray)


fun keysPresent(): Boolean {
    val entry = KeyStore.getInstance(Constants.ANDROID_KEYSTORE).run {
        load(null)
        getEntry(Constants.usersKeyName, null)
    }
    return (entry != null)
}

/**
 * generates key
 * @throws error when key alredy exists
 */
fun generateAndStoreKeys(context: Context): Boolean {
    try {
        if (!keysPresent()) {
            val spec = KeyPairGeneratorSpec.Builder(context)
                .setKeySize(Constants.KEY_SIZE)
                .setAlias(Constants.usersKeyName)
                .setSubject(X500Principal("CN=" + Constants.usersKeyName))
                .setSerialNumber(BigInteger.valueOf(Constants.serialNr))
                .setStartDate(GregorianCalendar().time)
                .setEndDate(GregorianCalendar().apply { add(Calendar.YEAR, 10) }.time)
                .build()
            KeyPairGenerator.getInstance(Constants.KEY_ALGO, Constants.ANDROID_KEYSTORE).run {
                initialize(spec)
                generateKeyPair()
            }
        }
    } catch (ex: Exception) {
        ex.message?.let { Log.d("APP", it) }
        return false
    }
    return true
}

/*
 *      Menu - show keys functionality
 */
fun getPubKey(): PubKey {
    val pKey = PubKey(ByteArray(0), ByteArray(0))
    try {
        val entry = KeyStore.getInstance(Constants.ANDROID_KEYSTORE).run {
            load(null)
            getEntry(Constants.usersKeyName, null)
        }
        val pub = (entry as KeyStore.PrivateKeyEntry).certificate.publicKey
        pKey.modulus = (pub as RSAPublicKey).modulus.toByteArray()
        pKey.exponent = pub.publicExponent.toByteArray()
    } catch (ex: Exception) {
        ex.message?.let { Log.d("APP", it) }
    }
    return pKey
}


fun getPrivExp(): ByteArray {
    var exp = ByteArray(0)
    try {
        val entry = KeyStore.getInstance(Constants.ANDROID_KEYSTORE).run {
            load(null)
            getEntry(Constants.usersKeyName, null)
        }
        val priv = (entry as KeyStore.PrivateKeyEntry).privateKey
        exp = (priv as RSAPrivateKey).privateExponent.toByteArray()
    } catch (ex: Exception) {
        ex.message?.let { Log.d("APP", it) }
    }
    return exp
}


/**
 * returns encrypted result
 */
fun encryptContent(content: ByteArray): String {
    try {
        if (content.isEmpty()) return ""
        val entry = KeyStore.getInstance(Constants.ANDROID_KEYSTORE).run {
            load(null)
            getEntry(Constants.usersKeyName, null)
        }
        val prKey = (entry as KeyStore.PrivateKeyEntry).privateKey
        val result = Cipher.getInstance(Constants.ENC_ALGO).run {
            init(Cipher.ENCRYPT_MODE, prKey)
            doFinal(content)
        }
        // return encrypted result
        return String.format(InStrings.encFormat, result.size, byteArrayToHex(result))
    } catch (e: Exception) {
        e.message?.let { Log.d("APP", it) }
        throw e
    }
}

/**
 * returns decrypted result
 */
fun decryptResult(content: ByteArray, result: ByteArray) {
    if (content.isEmpty() || result.isEmpty()) return
    try {
        val entry = KeyStore.getInstance(Constants.ANDROID_KEYSTORE).run {
            load(null)
            getEntry(Constants.usersKeyName, null)
        }
        val puKey = (entry as KeyStore.PrivateKeyEntry).certificate.publicKey
        val clear = Cipher.getInstance(Constants.ENC_ALGO).run {
            init(Cipher.DECRYPT_MODE, puKey)
            doFinal(result)
        }
        // return decrypted clear content
        String.format(InStrings.decFormat, clear.size, byteArrayToHex(clear))
    } catch (e: Exception) {
        e.message?.let { Log.d("APP", it) }
        throw e
    }
}

/**
 * Sign contetn
 */
fun signContent(content: ByteArray): String {
    if (content.isEmpty()) return ""
    var clear = ByteArray(0)
    try {
        val entry = KeyStore.getInstance(Constants.ANDROID_KEYSTORE).run {
            load(null)
            getEntry(Constants.usersKeyName, null)
        }
        val prKey = (entry as KeyStore.PrivateKeyEntry).privateKey
        val result = Signature.getInstance(Constants.SIGN_ALGO).run {
            initSign(prKey)
            update(content)
            sign()
        }
        // display signature
        return String.format(InStrings.signFormat, result.size, byteArrayToHex(result))
    } catch (e: Exception) {
        e.message?.let { Log.d("APP", it) }
        throw e
    }
}

fun verifySignature(content: ByteArray, result: ByteArray): Boolean {
    if (content.isEmpty() || result.isEmpty()) return false
    try {
        val entry = KeyStore.getInstance(Constants.ANDROID_KEYSTORE).run {
            load(null)
            getEntry(Constants.usersKeyName, null)
        }
        val puKey = (entry as KeyStore.PrivateKeyEntry).certificate.publicKey
        val verified = Signature.getInstance(Constants.SIGN_ALGO).run {
            initVerify(puKey)
            update(content)
            verify(result)
        }
        return verified
    } catch (e: Exception) {
        e.message?.let { Log.d("APP", it) }
        throw e
    }
}

fun getPemCertificate(): String {
    try {
        val entry = KeyStore.getInstance(Constants.ANDROID_KEYSTORE).run {
            load(null)
            getEntry(Constants.usersKeyName, null)
        }
        val cert = (entry as KeyStore.PrivateKeyEntry).certificate
        val encCert = cert.encoded
        return InStrings.beginCert + Base64.encodeToString(
            encCert,
            Base64.DEFAULT
        ) + InStrings.endCert
    } catch (e: Exception) {
        e.message?.let { Log.d("APP", it) }
        throw e
    }
}


fun getCertificate(): String {
    try {
        val entry = KeyStore.getInstance(Constants.ANDROID_KEYSTORE).run {
            load(null)
            getEntry(Constants.usersKeyName, null)
        }
        val cert = (entry as KeyStore.PrivateKeyEntry).certificate
        val encCert = cert.encoded
        val strCert = cert.toString()
        val b64Cert =
            InStrings.beginCert + Base64.encodeToString(encCert, Base64.DEFAULT) + InStrings.endCert
        return String.format(
            InStrings.certFormat, encCert.size, byteArrayToHex(encCert),
            b64Cert.length, b64Cert, strCert
        )
    } catch (e: Exception) {
        e.message?.let { Log.d("APP", it) }
        throw e
    }
}
