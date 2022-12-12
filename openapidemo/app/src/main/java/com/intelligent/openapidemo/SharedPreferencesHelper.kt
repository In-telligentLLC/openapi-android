package com.intelligent.openapidemo

import android.content.Context
import android.content.SharedPreferences

object  SharedPreferencesHelper {
    private const val IS_LOGGED_IN = "IsLoggedIn"

    //This method is used for get the shared preferences

    private fun getSharedPreferences(context: Context): SharedPreferences? {
        return context.getSharedPreferences("OpenApiDemoSharedPref", Context.MODE_PRIVATE)
    }

    //This method is used for set the login status

    fun setLogedIn(context: Context, isLoggedIn: Boolean) {
        val editor = getSharedPreferences(context)!!.edit()
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    //This method is used for set the Fcm Token

    fun setFcmToken(context: Context, token: String?) {
        val editor = getSharedPreferences(context)!!.edit()
        editor.putString("FcmToken", token)
        editor.apply()
    }
    /**
     * This method is used for get the login status
     *
     * @return Boolean
     */

    fun getLogedIn(context: Context): Boolean {
        return getSharedPreferences(context)!!.getBoolean(IS_LOGGED_IN, false)
    }
    /**
     * This method is used for get the Fcm Token
     *
     * @return String status
     */
    fun getFcmToken(context: Context): String? {
        return getSharedPreferences(context)!!.getString("FcmToken", "")
    }


}
