package com.alkemy.ongsomosmas.ui.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.alkemy.ongsomosmas.R
import com.alkemy.ongsomosmas.data.Resource
import com.alkemy.ongsomosmas.data.model.LoginDetails
import com.alkemy.ongsomosmas.databinding.ActivityLoginBinding
import com.alkemy.ongsomosmas.ui.home.HomeActivity
import com.alkemy.ongsomosmas.ui.signup.SignUpActivity
import com.alkemy.ongsomosmas.utils.EventConstants
import com.alkemy.ongsomosmas.utils.sendLog
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var binding: ActivityLoginBinding
    private val callbackManager = CallbackManager.Factory.create()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        //observe login form state to catch errors
        loginViewModel.loginFormState.observe(this, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless fields are valid
            binding.btnLogin.isEnabled = loginState.isDataValid
            if (loginState.emailError != null) {
                binding.etEmail.error = getString(loginState.emailError)
            }
            if (loginState.passwordError != null) {
                binding.tfPassword.error = getString(loginState.passwordError)
            } else {
                binding.tfPassword.error = null
            }
        })

        binding.etEmail.afterTextChanged {
            loginViewModel.loginDataChanged(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }

        binding.etPassword.afterTextChanged {
            loginViewModel.loginDataChanged(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }

        //remove error from all fields if any field is modified
        fun removeFieldsErrors() {
            binding.etEmail.afterTextChanged {
                binding.tfEmail.error = null
                binding.tfPassword.error = null
            }
            binding.etPassword.afterTextChanged {
                binding.tfEmail.error = null
                binding.tfPassword.error = null
            }
        }

        // start home activity on login button click
        binding.btnLogin.setOnClickListener {
            var loginDetails = LoginDetails(
                binding.tfEmail.editText?.text.toString().trim(),
                binding.tfPassword.editText?.text.toString().trim()
            )
            loginViewModel.login(loginDetails)
            sendLog(EventConstants.LOGIN_PRESSED, "User has pressed the login button")
        }

        binding.btnSignup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            sendLog(EventConstants.SIGNUP_PRESSED, "User has pressed the signup button")
        }

        loginViewModel.loginResponse.observe(this, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                }
                Resource.Status.SUCCESS -> {
                    sendLog(EventConstants.LOGIN_SUCCESS, "User login successfully")
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
                Resource.Status.ERROR -> {
                    showErrorDialog("Error", it.message)
                    sendLog(EventConstants.LOGIN_ERROR, "User login failed")
                    //show all fields with error after opening error dialog
                    binding.tfEmail.error = " "
                    binding.tfPassword.error = "Email or password incorrect"
                    //remove all fields' error after modifying any field
                    removeFieldsErrors()
                }
            }
        })
        // facebook login setup
        binding.ibFacebook.setOnClickListener {
            loginWithFacebook()
            sendLog(EventConstants.FB_PRESSED, "User has pressed the facebook button")
        }
    }

    private fun TextInputEditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null)
        auth.currentUser
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    Log.d(TAG, "signInWithCredential:success")
                    auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        baseContext, R.string.facebook_auth_error,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    // facebook login transaction
    private fun loginWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, setOf("email"))
        LoginManager.getInstance().registerCallback(
            callbackManager, object : FacebookCallback<com.facebook.login.LoginResult> {
                override fun onSuccess(loginResult: com.facebook.login.LoginResult?) {
                    Log.d(TAG, "facebook:onSuccess:$loginResult")
                    if (loginResult != null) {
                        handleFacebookAccessToken(loginResult.accessToken)
                    }
                    // start home activity
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                    finish()
                }

                override fun onCancel() {
                    Log.d(TAG, "facebook:onCancel")
                }

                override fun onError(error: FacebookException) {
                    Log.d(TAG, "facebook:onError", error)
                    Toast.makeText(
                        baseContext, R.string.facebook_auth_error,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    //show error dialog if there's an issue with the login api
    fun showErrorDialog(title: String, message: String?) {
        val dialog: AlertDialog =
            AlertDialog.Builder(this).setMessage(message).setTitle(title)
                .setNeutralButton(
                    R.string.error_dialog
                ) { _, _ -> }
                .create()
        dialog.show()
    }
}
