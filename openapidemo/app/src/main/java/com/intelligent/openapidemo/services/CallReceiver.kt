package com.intelligent.openapidemo.services

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.intelligent.openapidemo.R
import com.sca.in_telligent.openapi.Actions
import com.sca.in_telligent.openapi.OpenAPI
import com.sca.in_telligent.openapi.data.network.model.PushNotification
import com.sca.in_telligent.openapi.data.network.model.SuccessResponse
import com.sca.in_telligent.openapi.service.HeadsUpNotificationActionReceiver

@Suppress("DEPRECATION")
class CallReceiver : HeadsUpNotificationActionReceiver() {

    companion object {
        private const val TAG = "CallReceiver"
    }

    override fun onReceive(context: Context, intent: Intent?) {
        Log.i(TAG, "OnReceiveMessage")
        super.onReceive(context, intent)
        if (intent != null && intent.extras != null) {
            val action = intent.action
            val data = intent.getSerializableExtra(Actions.CALL_NOTIFICATION) as PushNotification?
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(data!!.notificationModel.id.toInt())
            OpenAPI.getAudioHelper().stopRingtone()
            if (action != null) {
                if (action == Actions.ACTION_MARK_AS_READ && data != null) {
                    Log.i(TAG, "Mark as Read action")
                    Toast.makeText(context, "Mark as Read action", Toast.LENGTH_LONG).show()
                    OpenAPI.openedAlert(
                        data.notificationModel.id.toInt(),context
                    ) { successResponse: SuccessResponse? ->
                        if (successResponse != null && successResponse.isSuccess) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.alert_opened_success),
                                Toast.LENGTH_LONG
                            ).show()
                        } else if (successResponse != null) {
                            Toast.makeText(context, successResponse.error, Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(
                                context,
                                context.getString(R.string.alert_opened_failed),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } else if (action == Actions.ACTION_DELETE) {
                    Log.i(TAG, "Delete action")
                    Toast.makeText(context, "Delete action", Toast.LENGTH_LONG).show()
                    OpenAPI.deleteAlert(
                        data.notificationModel.id.toInt(),
                   context ) { successResponse: SuccessResponse? ->
                        if (successResponse != null && successResponse.isSuccess) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.alert_delete_success),
                                Toast.LENGTH_LONG
                            ).show()
                        } else if (successResponse != null) {
                            Toast.makeText(context, successResponse.error, Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(
                                context,
                                context.getString(R.string.delete_alert_failed),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } else if (action == Actions.ACTION_OPEN) {
                    Log.i(TAG, "Open action")
                    Toast.makeText(context, "Open action", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}