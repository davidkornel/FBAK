package com.example.terminal

import com.google.gson.Gson
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

private val gson = Gson()

private fun readStream(input: InputStream): String {
    var reader: BufferedReader? = null
    var line: String?
    val response = StringBuilder()
    try {
        reader = BufferedReader(InputStreamReader(input))
        while (reader.readLine().also{ line = it } != null)
            response.append(line)
    }
    catch (e: IOException) {
        response.clear()
        response.append("readStream: ${e.message}")
    }
    reader?.close()
    return response.toString()
}

fun pay(act: CheckoutActivity, checkout: Checkout) {
    val urlRoute = "/checkout"
    val url = URL("http://10.0.2.2:5107$urlRoute")

    var urlConnection: HttpURLConnection? = null
    try {
        urlConnection = (url.openConnection() as HttpURLConnection).apply {
            doOutput = true
            doInput = true
            requestMethod = "POST"
            setRequestProperty("Content-Type", "application/json")
            var payload = gson.toJson(checkout)
            useCaches = false
            connectTimeout = 5000
            with(outputStream) {
                write(payload.toByteArray())
                flush()
                close()
            }
                // get response
            if (responseCode == 200) {
                val response = gson.fromJson(readStream(inputStream),Response::class.java)
                act.writeText("PAYMENT SUCCESSFULL!")
                act.appendText("Total paid ${response.totalAmountPaid} €")
                act.setImg(R.drawable.baseline_done_24)
            }
            else {
                act.writeText("PAYMENT FAILED!")
                act.appendText(readStream(inputStream))
                act.setImg(R.drawable.outline_error_outline_24)
            }
        }
    }
    catch (e: Exception) {
        act.setImg(R.drawable.outline_error_outline_24)
        act.writeText(e.toString())
    }
    urlConnection?.disconnect()

}