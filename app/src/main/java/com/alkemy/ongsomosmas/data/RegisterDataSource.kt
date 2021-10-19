package com.alkemy.ongsomosmas.data

import com.alkemy.ongsomosmas.data.model.User
import javax.inject.Inject

class RegisterDataSource @Inject constructor(private val registerService: RegisterService) :
    BaseDataSource() {

    suspend fun addUser(user: User) = getResult { registerService.postUser(user) }
}