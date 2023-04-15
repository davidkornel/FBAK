package com.example.userappify.api

import android.content.Context
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.userappify.api.Constants.baseUrl
import com.example.userappify.model.RegistrationUser
import com.example.userappify.model.UserDataRequest
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import org.json.JSONObject
import kotlin.reflect.KFunction2
import com.example.userappify.model.UserDataResponse
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


/**
 * Register user
 */
fun registerUser(registrationUser: RegistrationUser, context: Context, onResponse: KFunction2<JSONObject, RegistrationUser, Unit>, view: View) {
    val url = "$baseUrl/register"
    try {
        val gson = Gson()
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, JSONObject(gson.toJson(registrationUser)),
            { response ->
                onResponse(response, registrationUser)
                println("Response: %s".format(response.toString()))
            },
            { error ->
                Snackbar.make(
                    view,
                    "Server is not available, ${error.message}",
                    Snackbar.LENGTH_SHORT
                )
                    .setAction("Action", null).show()
            }
        )

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    } catch (e: Exception) {
        e.message?.let { Log.d("APP", it) }
        throw e
    }
}

/**
 * Get PAST TRANSACTIONS and available VOUCHERS
 */
fun getUserData(request: UserDataRequest, context: Context, onResponse: KFunction2<JSONObject, UserDataRequest, Unit>, view: View) {
    val url = "$baseUrl/userdata"
    try {
        val gson = Gson()
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        Log.d("oggJson",gson.toJson(request))
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, JSONObject(gson.toJson(request)),
            { response ->
                onResponse(response, request)
                println("Response: %s".format(response.toString()))
            },
            { error ->
                Snackbar.make(
                    view,
                    "Server is not available, ${error.message}",
                    Snackbar.LENGTH_SHORT
                )
                    .setAction("Action", null).show()
            }
        )

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    } catch (e: Exception) {
        e.message?.let { Log.d("APP", it) }
        throw e
    }
}
