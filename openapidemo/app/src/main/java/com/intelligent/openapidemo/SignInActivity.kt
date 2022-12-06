package com.intelligent.openapidemo

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.sca.in_telligent.openapi.OpenAPI
import com.sca.in_telligent.openapi.OpenAPI.TAG
import com.sca.seneca.lib.PrintLog


class SignInActivity : AppCompatActivity() {

    lateinit var signInButton:Button
    lateinit var closeButton:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signInButton =findViewById(R.id.sign_in_button)
        closeButton =findViewById(R.id.close_button)

        signInButton.setOnClickListener {



        }




    }









}