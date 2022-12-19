package com.intelligent.openapidemo

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.intelligent.openapidemo.viewmodels.SignInViewModel


class SignInActivity : AppCompatActivity() {


    private val dialog: Dialog by lazy {
        Dialog(this)

    }


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        val signInViewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        setContentView(R.layout.activity_sign_in)
        var signInButton = findViewById<Button>(R.id.sign_in_button)
        var closeButton = findViewById<Button>(R.id.close_button)
        var signInLayout = findViewById<ConstraintLayout>(R.id.sign_in_layout)


        showDialog()
        signInButton.setOnClickListener {
            dialog.show()


            signInViewModel.authorizeDevice(this)
            signInViewModel.loginStatus.observe(this, Observer { status ->
                dialog.cancel()
                if (status) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Snackbar.make(
                        signInLayout, getString(R.string.signin_error_message),
                        Snackbar.LENGTH_LONG
                    )
                        .setAction("Action", null).show()
                }
            })


        }

        closeButton.setOnClickListener {
            finish()

        }


    }

    private fun showDialog() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.findViewById<ProgressBar>(R.id.progressBar)


    }


}