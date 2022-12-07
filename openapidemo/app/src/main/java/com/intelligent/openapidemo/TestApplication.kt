package com.intelligent.openapidemo

import android.app.Application
import com.google.firebase.BuildConfig
import com.google.firebase.FirebaseApp
import com.sca.in_telligent.openapi.OpenAPI
import com.sca.in_telligent.openapi.service.HeadsUpNotificationActionReceiver
import com.sca.in_telligent.openapi.util.Environment
import com.sca.in_telligent.openapi.util.OpenApiFlashHelper
import com.sca.seneca.lib.PrintLog
import io.reactivex.rxjava3.plugins.RxJavaPlugins

class TestApplication: Application() {
    override fun onCreate() {
        super.onCreate()


        FirebaseApp.initializeApp(applicationContext)
        RxJavaPlugins.setErrorHandler { throwable: Throwable? ->
            PrintLog.print(
                "Rx Error",
                throwable
            )
        }

        initOpenApi()

    }
    fun initOpenApi() {
        val configuration = OpenAPI.Configuration.Builder()
            .setAppVersion(BuildConfig.VERSION_NAME)
            .setDebug(true)
            .setEnvironment(Environment.DEV)
            .setHeadsUpNotificationActionReceiver<HeadsUpNotificationActionReceiver>(CallReceiver()).build(applicationContext)
        val partnerToken = "YegJcZtroTjbv51Vw7PR"
        OpenAPI.init(
            applicationContext,
            partnerToken,
            com.intelligent.openapidemo.BuildConfig.APPLICATION_ID,
            configuration
        )
        OpenApiFlashHelper.newInstance(applicationContext)
    }




}



