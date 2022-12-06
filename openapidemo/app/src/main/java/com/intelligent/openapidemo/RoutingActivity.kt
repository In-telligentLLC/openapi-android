package com.intelligent.openapidemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.sca.in_telligent.openapi.OpenAPI

class RoutingActivity:Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //to install the splashscreen
        installSplashScreen()
        setContentView(R.layout.activity_main)
        if(OpenAPI.getInstance().checkToken()){
            // User already signed in
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // User is not signed in
            val intent = Intent(this, SignInActivity::class.java)

            startActivity(intent)
            finish()
        }


    }
}