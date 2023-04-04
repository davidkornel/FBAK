package com.example.userappify.api

import android.util.Log
import com.example.userappify.api.Constants.baseUrl
import com.example.userappify.model.RegistrationUser
import com.example.userappify.model.User
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


private fun readStream(input: InputStream): String {
    var reader: BufferedReader? = null
    var line: String?
    val response = StringBuilder()
    try {
        reader = BufferedReader(InputStreamReader(input))
        while (reader.readLine().also { line = it } != null)
            response.append(line)
    } catch (e: IOException) {
        response.clear()
        response.append("readStream: ${e.message}")
    }
    reader?.close()
    return response.toString()
}

/**
 * Register user
 */
fun registerUser(registrationUser: RegistrationUser): User {
    val url = URL("$baseUrl/register")
    var urlConnection: HttpURLConnection? = null
    try {
        val gson = Gson()
        urlConnection = (url.openConnection() as HttpURLConnection)
            .apply {
                doOutput = true
                doInput = true
                requestMethod = "POST"
                setRequestProperty("Content-Type", "application/json")
                useCaches = false
                connectTimeout = 5000

//           TODO error      java.net.protocolexception method does not support a request body post
                with(outputStream) {
                    write(gson.toJson(registrationUser).toByteArray())
                    flush()
                    close()
                }
                // get response
                if (responseCode == 200) {
                    return gson.fromJson(readStream(inputStream), User::class.java)
                } else {
                    throw RuntimeException("Code: $responseCode")
                }
            }
    } catch (e: Exception) {
        e.message?.let { Log.d("APP", it) }
        throw e
    } finally {
        urlConnection?.disconnect()
    }
}