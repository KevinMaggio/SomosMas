package com.alkemy.ongsomosmas.ui.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.alkemy.ongsomosmas.data.Resource
import com.alkemy.ongsomosmas.databinding.ActivitySignupBinding
import com.alkemy.ongsomosmas.utils.EventConstants
import com.alkemy.ongsomosmas.utils.sendLog
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nameInput = binding.etName
        val emailInput = binding.etEmail
        val passwordInput = binding.etPassword
        val confirmPasswordInput = binding.etConfirmPassword
        val signupButton = binding.btSignUp

        //observe sign up form state to catch errors
        signUpViewModel.signUpFormState.observe(this, Observer {
            val signUpState = it ?: return@Observer

            // disable sign up button unless fields are valid
            signupButton.isEnabled = signUpState.isDataValid
            if (signUpState.nameError != null) {
                nameInput.error = getString(signUpState.nameError)
            }
            if (signUpState.emailError != null) {
                emailInput.error = getString(signUpState.emailError)
            }
            binding.textInputPassword.error = if (signUpState.passwordError != null) {
                getString(signUpState.passwordError)
            } else {
                null
            }

            binding.textInputConfirmPassword.error = if (signUpState.confirmPasswordError != null) {
                getString(signUpState.confirmPasswordError)
            } else {
                null
            }

        })

        nameInput.afterTextChanged {
            signUpViewModel.signUpDataChanged(
                nameInput.text.toString(),
                emailInput.text.toString(),
                passwordInput.text.toString(),
                confirmPasswordInput.text.toString()
            )
        }

        emailInput.afterTextChanged {
            signUpViewModel.signUpDataChanged(
                nameInput.text.toString(),
                emailInput.text.toString(),
                passwordInput.text.toString(),
                confirmPasswordInput.text.toString()
            )
        }

        passwordInput.afterTextChanged {
            signUpViewModel.signUpDataChanged(
                nameInput.text.toString(),
                emailInput.text.toString(),
                passwordInput.text.toString(),
                confirmPasswordInput.text.toString()
            )
        }

        confirmPasswordInput.afterTextChanged {
            signUpViewModel.signUpDataChanged(
                nameInput.text.toString(),
                emailInput.text.toString(),
                passwordInput.text.toString(),
                confirmPasswordInput.text.toString()
            )
        }

        //show error dialog on click if there's an issue with the sign up api
        fun showErrorDialog(title: String, message: String?) {
            val dialog: AlertDialog =
                AlertDialog.Builder(this).setMessage(message).setTitle(title)
                    .setNeutralButton(
                        "Close"
                    ) { _, _ -> }
                    .create()
            dialog.show()
        }

        //remove error symbol from all fields if any field is modified
        fun removeFieldsErrors() {
            nameInput.afterTextChanged {
                binding.textInputName.error = null
                binding.textInputEmail.error = null
                binding.textInputPassword.error = null
                binding.textInputConfirmPassword.error = null
            }

            emailInput.afterTextChanged {
                binding.textInputName.error = null
                binding.textInputEmail.error = null
                binding.textInputPassword.error = null
                binding.textInputConfirmPassword.error = null
            }

            passwordInput.afterTextChanged {
                binding.textInputName.error = null
                binding.textInputEmail.error = null
                binding.textInputPassword.error = null
                binding.textInputConfirmPassword.error = null
            }

            confirmPasswordInput.afterTextChanged {
                binding.textInputName.error = null
                binding.textInputEmail.error = null
                binding.textInputPassword.error = null
                binding.textInputConfirmPassword.error = null
            }
        }


        signupButton.setOnClickListener {
            signUpViewModel.register(
                nameInput.text?.toString() ?: "",
                passwordInput.text?.toString() ?: "",
                emailInput.text?.toString() ?: ""
            )
            sendLog(EventConstants.REGISTER_PRESSED, "Register button was pressed")
        }

        //observe view model to see post request response status
        signUpViewModel.resultOperation.observe(this, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                }
                Resource.Status.SUCCESS -> {
                    Toast.makeText(this, "User created", Toast.LENGTH_LONG).show()
                    sendLog(EventConstants.SIGNUP_SUCCESS, "User sign up successfully")
                }
                Resource.Status.ERROR -> {
                    sendLog(EventConstants.SIGNUP_ERROR, "Sign up failed")
                    showErrorDialog("Error", it.message)
                    //show all fields with error after opening error dialog
                    binding.textInputName.error = " "
                    binding.textInputEmail.error = " "
                    binding.textInputPassword.error = " "
                    binding.textInputConfirmPassword.error = " "
                    //remove all fields error after modifying any field
                    removeFieldsErrors()
                }
            }
        })
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
}
