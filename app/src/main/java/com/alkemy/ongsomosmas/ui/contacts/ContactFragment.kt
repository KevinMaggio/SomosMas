package com.alkemy.ongsomosmas.ui.contacts

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.alkemy.ongsomosmas.data.Resource
import com.alkemy.ongsomosmas.databinding.FragmentContactBinding
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactFragment : Fragment() {

    private lateinit var binding: FragmentContactBinding
    private val contactViewModel: ContactViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactBinding.inflate(inflater, container, false)
        //observe contact form state to catch errors
        contactViewModel.contactFormState.observe(viewLifecycleOwner, Observer {
            val contactState = it ?: return@Observer
            // disable contact button unless fields are valid
            binding.button.isEnabled = contactState.isDataValid
            if (contactState.emailError != null) {
                binding.etEmail.error = getString(contactState.emailError)
            } else {
                binding.tfEmail.error = null
            }
        })

        binding.button.setOnClickListener {
            contactViewModel.contact(
                0,
                binding.etName.text.toString() + " " + binding.etSurname.text.toString(),
                binding.etEmail.text.toString(),
                "",
                binding.etQuery.text.toString(),
                "",
                "",
                ""
            )
        }

        binding.etEmail.afterTextChanged {
            contactViewModel.contactDataChanged(
                binding.etEmail.text.toString(),
                binding.etName.text.toString(),
                binding.etSurname.text.toString(),
                binding.etQuery.text.toString()
            )
        }
        binding.etName.afterTextChanged {
            contactViewModel.contactDataChanged(
                binding.etEmail.text.toString(),
                binding.etName.text.toString(),
                binding.etSurname.text.toString(),
                binding.etQuery.text.toString()
            )
        }
        binding.etSurname.afterTextChanged {
            contactViewModel.contactDataChanged(
                binding.etEmail.text.toString(),
                binding.etName.text.toString(),
                binding.etSurname.text.toString(),
                binding.etQuery.text.toString()
            )
        }
        binding.etQuery.afterTextChanged {
            contactViewModel.contactDataChanged(
                binding.etEmail.text.toString(),
                binding.etName.text.toString(),
                binding.etSurname.text.toString(),
                binding.etQuery.text.toString()
            )
        }

        contactViewModel.contactResponse.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                }
                Resource.Status.SUCCESS -> {
                    Toast.makeText(context, "mensaje enviado", Toast.LENGTH_SHORT).show()
                    cleanFields()
                }
                Resource.Status.ERROR -> {
                    showErrorDialog("Error", "Ha ocurrido un error. Intente nuevamente mas tarde")
                    removeFieldsErrors()
                }
            }
        })
        return binding.root
    }

    fun showErrorDialog(title: String, message: String?) {
        val dialog: AlertDialog? =
            context?.let {
                AlertDialog.Builder(it).setMessage(message).setTitle(title)
                    .setNeutralButton(
                        "Close"
                    ) { _, _ -> }
                    .create()
            }
        dialog?.show()
    }

    fun removeFieldsErrors() {
        binding.etEmail.afterTextChanged {
            binding.tfEmail.error = null
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

    fun cleanFields() {
        binding.etSurname.text?.clear()
        binding.etQuery.text?.clear()
        binding.etName.text?.clear()
        binding.etEmail.text?.clear()
    }
}