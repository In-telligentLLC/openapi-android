package com.intelligent.openapidemo.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.intelligent.openapidemo.SharedPreferencesHelper
import com.intelligent.openapidemo.TestApplication
import com.intelligent.openapidemo.utils.FcmUtil
import com.sca.in_telligent.openapi.OpenAPI
import com.sca.in_telligent.openapi.data.network.model.ErrorType
import com.sca.in_telligent.openapi.data.network.model.SuccessResponse
import com.sca.in_telligent.openapi.util.AudioHelper
import com.sca.seneca.lib.PrintLog

class FcmService: FirebaseMessagingService() {


    private val TAG = "FCMService"

    private var state: TestApplication? = null

    private var audioHelper: AudioHelper? = null

    override fun onCreate() {
        super.onCreate()
        audioHelper = OpenAPI.getInstance().audioHelper
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            state = application as TestApplication
            Log.d("STATE EQUALS TO ----->", " $state")
            OpenAPI.getInstance().relayPushNotification(
                remoteMessage.data, this, true
            ) { errorType: ErrorType ->
                if (errorType == ErrorType.NOTIFICATION_PERMISSION_NOT_GRANTED) {
                    PrintLog.print("Tag", "Notification permission not allowed")
                }
            }

        }
    }

    override fun onNewToken(s: String) {
        super.onNewToken(s)
        FcmUtil.registerPush(this)
        SharedPreferencesHelper.setFcmToken(this, s)
    }




}