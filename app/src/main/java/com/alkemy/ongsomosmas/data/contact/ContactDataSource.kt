package com.alkemy.ongsomosmas.data.contact

import com.alkemy.ongsomosmas.data.BaseDataSource
import com.alkemy.ongsomosmas.data.model.Contact
import javax.inject.Inject

class ContactDataSource @Inject constructor(private val contactService: ContactService) :
    BaseDataSource() {
    suspend fun contact(contact: Contact) = getResult { contactService.contact(contact) }
}