package com.alkemy.ongsomosmas.ui.contacts

import android.util.Patterns
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkemy.ongsomosmas.R
import com.alkemy.ongsomosmas.data.Resource
import com.alkemy.ongsomosmas.data.contact.ContactRepository
import com.alkemy.ongsomosmas.data.model.ContactResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactViewModel @ViewModelInject constructor(private val contactRepository: ContactRepository) :
    ViewModel() {

    private val _contactForm = MutableLiveData<ContactsFormState>()
    val contactFormState: LiveData<ContactsFormState> = _contactForm

    private val _contactResponse: MutableLiveData<Resource<ContactResponse>> = MutableLiveData()
    val contactResponse: LiveData<Resource<ContactResponse>> = _contactResponse

    //validate all contact fields
    fun contactDataChanged(email: String, name: String, surname: String, query: String) {
        if (!isEmailValid(email)) {
            _contactForm.value = ContactsFormState(emailError = R.string.error_state_msg_invalid_email)
        } else if (name.isEmpty() || surname.isEmpty() || query.isEmpty()) {
        } else {
            _contactForm.value = ContactsFormState(isDataValid = true)
        }
    }

    //validate email
    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun contact(
        id: Int,
        name: String,
        email: String,
        phone: String,
        message: String,
        deletedAt: String,
        createdAt: String,
        updatedAt: String
    ) = viewModelScope.launch(Dispatchers.Main) {
        val result = withContext(Dispatchers.IO) {
            contactRepository.contact(
                id,
                name,
                email,
                phone,
                message,
                deletedAt,
                createdAt,
                updatedAt
            )
        }
        _contactResponse.value = result
    }
}