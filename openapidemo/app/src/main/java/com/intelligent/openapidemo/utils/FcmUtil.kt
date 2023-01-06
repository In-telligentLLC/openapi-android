package com.intelligent.openapidemo.utils

import android.content.Context
import com.intelligent.openapidemo.SharedPreferencesHelper
import com.sca.in_telligent.openapi.OpenAPI
import com.sca.seneca.lib.PrintLog

class FcmUtil {

    companion object {
        fun registerPush(context: Context) {

            OpenAPI.registerPushToken(SharedPreferencesHelper.getFcmToken(context)) { status ->
                PrintLog.print(
                    "Tag",
                    "********register pushToken status $status"
                )
            }
        }


    }
}