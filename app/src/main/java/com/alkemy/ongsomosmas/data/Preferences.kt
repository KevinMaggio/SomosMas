package com.alkemy.ongsomosmas.data

import android.content.Context

class Preferences(val context: Context) {


    val KEY_USER_TOKEN = "user_token"
    val storage = context.getSharedPreferences("com.alkemy.ongsomosmas_app", Context.MODE_PRIVATE)


    fun saveUserToken(user: String) {
        storage.edit().putString(this.KEY_USER_TOKEN, user).apply()
    }

    fun getUserToken(): String {
        return storage.getString(this.KEY_USER_TOKEN, "")!!
    }

    fun clear() {
        storage.edit().clear().apply()
    }
}