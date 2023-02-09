package com.intelligent.openapidemo.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intelligent.openapidemo.SharedPreferencesHelper
import com.intelligent.openapidemo.TestApplication
import com.sca.in_telligent.openapi.OpenAPI
import com.sca.seneca.lib.PrintLog

class SignInViewModel:ViewModel () {
    val loginStatus = MutableLiveData<Boolean>()


    fun authorizeDevice(context: Context) {
        val pushTokenSentToServer = TestApplication.pushToken
        PrintLog.print("Tag", "***********authorizeDevice() ${TestApplication.pushToken}******")
        OpenAPI.authorization(pushTokenSentToServer,context) { status ->
            if (status.isSuccess) {
                PrintLog.print("Tag", "Verifying login status - Logged in ")
                SharedPreferencesHelper.setLogedIn(context, status.isSuccess)
               loginStatus.value = status.isSuccess
                PrintLog.print("Tag", "********Login success. Push token sent to server is $pushTokenSentToServer")
                if (pushTokenSentToServer == "NA" && TestApplication.pushToken != "NA") {
                    PrintLog.print("Tag", "********Login success. Push token is $pushTokenSentToServer. Hence we call the registerPush API")
                    registerPush(context, TestApplication.pushToken)
                } else {
                    PrintLog.print("Tag", "********Login success. Push token sent to server is ${TestApplication.pushToken} As push token is sent to server, storing in SPF")
                    SharedPreferencesHelper.setFcmToken(context, TestApplication.pushToken)
                }
            } else {
                loginStatus.value = false
            }
        }
    }
    private fun registerPush(context:Context, token: String) {
        OpenAPI.registerPushToken(
            token,
        context) {

            SharedPreferencesHelper.setFcmToken(context, TestApplication.pushToken)

        }
    }

}
