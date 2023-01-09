package com.intelligent.openapidemo

import android.app.Application
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData

import com.google.firebase.BuildConfig
import com.google.firebase.messaging.FirebaseMessaging
import com.intelligent.openapidemo.services.CallReceiver
import com.intelligent.openapidemo.utils.FcmUtil
import com.sca.in_telligent.openapi.OpenAPI
import com.sca.in_telligent.openapi.data.network.model.Community
import com.sca.in_telligent.openapi.service.HeadsUpNotificationActionReceiver
import com.sca.in_telligent.openapi.util.Environment
import com.sca.in_telligent.openapi.util.OpenApiFlashHelper
import com.sca.seneca.lib.PrintLog
import io.reactivex.rxjava3.plugins.RxJavaPlugins

class TestApplication : Application() {


    companion object {
        private const val TAG = "TestApplication"
        var pushToken = "NA"
    }

    override fun onCreate() {
        super.onCreate()

        RxJavaPlugins.setErrorHandler { throwable: Throwable? ->
            PrintLog.print(
                "Rx Error", throwable
            )
        }


        initOpenApi()
        generateDeviceToken()


    }

    private fun initOpenApi() {
        val configuration =
            OpenAPI.Configuration.Builder().setAppVersion(BuildConfig.VERSION_NAME).setDebug(true)
                .setEnvironment(Environment.DEV)
                .setHeadsUpNotificationActionReceiver<HeadsUpNotificationActionReceiver>(
                    CallReceiver()
                ).setNotificationOpenActivity<MainActivity>(MainActivity::class.java)
                .build(applicationContext)
        val partnerToken = "YegJcZtroTjbv51Vw7PR"
        OpenAPI.init(
            applicationContext,
            partnerToken,
            com.intelligent.openapidemo.BuildConfig.APPLICATION_ID,
            configuration
        )
        OpenApiFlashHelper.newInstance(applicationContext)
    }
    
    private fun generateDeviceToken() {

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            val token = task.result // Now we have the Push device token is ready.
            Log.v(TAG, "Device Token: $token")
             if(token.isNullOrEmpty()) {
                 FcmUtil.registerPush(this)
             }
            SharedPreferencesHelper.setFcmToken(this, token)




        })
    }
}

















