package com.intelligent.openapidemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.intelligent.openapidemo.utils.FcmUtil
import com.sca.in_telligent.openapi.OpenAPI

class RoutingActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //to install the splashscreen
        installSplashScreen()
        val intent = if (OpenAPI.checkToken(this)) {
            // User already signed in
            if((SharedPreferencesHelper.getFcmToken(this) != "" && SharedPreferencesHelper.getFcmToken(this) != "NA")) {
                FcmUtil.registerPush(this)
            }

            Intent(this, MainActivity::class.java)
        } else {
            // User is not signed in
            Intent(this, SignInActivity::class.java)
        }
        startActivity(intent)
        finish() }
}


