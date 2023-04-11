package com.example.userappify.api

import android.content.Context
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.userappify.api.Constants.baseUrl
import com.example.userappify.model.RegistrationUser
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import org.json.JSONObject
import kotlin.reflect.KFunction1


/**
 * Register user
 */
fun registerUser(registrationUser: RegistrationUser, context: Context, onResponse: KFunction1<JSONObject, Unit>, view: View) {
    val url = "$baseUrl/register"
    try {
        val gson = Gson()
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, JSONObject(gson.toJson(registrationUser)),
            { response ->
                onResponse(response)
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