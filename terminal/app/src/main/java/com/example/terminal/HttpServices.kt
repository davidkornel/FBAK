package com.example.terminal

import android.content.Context
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import kotlin.reflect.KFunction2


/**
 * checkout call to BE
 */
fun checkoutUser(
    checkout: Checkout,
    context: Context,
    onResponse: KFunction2<JSONObject, CheckoutActivity, Unit>,
    act: CheckoutActivity
) {
    val url = "http://10.0.2.2:5107/checkout"
    try {
        val gson = Gson()
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        Log.d("cane",JSONObject(gson.toJson(checkout)).toString())
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, JSONObject(gson.toJson(checkout)),
            { response ->
                onResponse(response, act)
                println("Response: %s".format(response.toString()))
            },
            { error ->
                Log.d("CANE",error.toString())
                act.writeText("PAYMENT FAILED!")
                act.setImg(R.drawable.outline_error_outline_24)
            }
        )

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    } catch (e: Exception) {
        e.message?.let { Log.d("APP", it) }
        throw e
    }
}