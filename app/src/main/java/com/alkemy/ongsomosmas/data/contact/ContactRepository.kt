package com.alkemy.ongsomosmas.data.contact

import com.alkemy.ongsomosmas.data.model.Contact
import javax.inject.Inject

class ContactRepository @Inject constructor(private val contactDataSource: ContactDataSource) {
    suspend fun contact(
        id: Int,
        name: String,
        email: String,
        phone: String,
        message: String,
        deletedAt: String,
        createdAt: String,
        updatedAt: String
    ) = contactDataSource.contact(
        Contact(
            id,
            name,
            email,
            phone,
            message,
            deletedAt,
            createdAt,
            updatedAt
        )
    )
}