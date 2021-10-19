package com.alkemy.ongsomosmas.ui.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alkemy.ongsomosmas.ui.home.HomeActivity
import com.alkemy.ongsomosmas.ui.login.LoginActivity
import com.alkemy.ongsomosmas.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private val loginViewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(4000)
        Toast.makeText(this, "\"Timer\" has finished", Toast.LENGTH_SHORT).show()
        check()

    }


    private fun check() {
        if (loginViewModel.isUserLogged()) {
            startActivity(Intent(this, HomeActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}