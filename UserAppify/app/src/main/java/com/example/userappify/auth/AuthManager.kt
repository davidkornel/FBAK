package com.example.userappify.auth

import android.app.Activity
import android.content.Context
import com.example.userappify.model.User
import com.google.gson.Gson

class AuthManager(private val activity: Activity) {

    private val preferencesKey = "acmePreferenceKey"
    private val userKey = "user"
    private val superMarketPublicKey = "superMarketPublicKey"

    fun setBEPublicKey(publicKey: String) {
        val sharedPref = activity.getSharedPreferences(
            preferencesKey, Context.MODE_PRIVATE
        )
        if (sharedPref != null) {
            with(sharedPref.edit()) {
                val gson = Gson()
                putString(superMarketPublicKey, publicKey)
                apply()
            }
        }
    }

    fun getBEPublicKey(): String? {
        val sharedPref = activity.getSharedPreferences(
            preferencesKey, Context.MODE_PRIVATE
        )
        if (sharedPref != null) {
            return sharedPref.getString(superMarketPublicKey, null)
        }
        return null
    }

    fun setLoginUser(user: User) {
        val sharedPref = activity.getSharedPreferences(
            preferencesKey, Context.MODE_PRIVATE
        )
        if (sharedPref != null) {
            with(sharedPref.edit()) {
                val gson = Gson()
                putString(userKey, gson.toJson(user))
                apply()
            }
        }
    }

    /**
     * This method is ONLY used for login and getting user id, username, password. NOT for user STATE. This is saved on BE
     */
    fun getLoginUser(): User? {
        val sharedPref = activity.getSharedPreferences(
            preferencesKey, Context.MODE_PRIVATE
        )
        if (sharedPref != null) {
            val gson = Gson()
            return gson.fromJson(sharedPref.getString(userKey, null), User::class.java)
        }
        return null
    }

}