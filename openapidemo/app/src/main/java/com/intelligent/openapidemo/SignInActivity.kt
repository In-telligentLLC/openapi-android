package com.intelligent.openapidemo

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.intelligent.openapidemo.databinding.ActivityMainBinding
import com.intelligent.openapidemo.databinding.ActivitySignInBinding
import com.sca.in_telligent.openapi.OpenAPI
import com.sca.in_telligent.openapi.OpenAPI.TAG
import com.sca.seneca.lib.PrintLog


class SignInActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        var binding = ActivitySignInBinding.inflate(layoutInflater)


        binding.signInButton.setOnClickListener {



        }




    }









}