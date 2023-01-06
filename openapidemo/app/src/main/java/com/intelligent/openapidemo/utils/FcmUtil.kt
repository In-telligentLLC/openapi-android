package com.intelligent.openapidemo.utils

import android.content.Context
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.intelligent.openapidemo.SharedPreferencesHelper
import com.intelligent.openapidemo.TestApplication
import com.sca.in_telligent.openapi.OpenAPI
import com.sca.seneca.lib.PrintLog
import io.reactivex.rxjava3.core.Observable

class FcmUtil {

    companion object {
        var fcmToken = Observable.just("NA")
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
