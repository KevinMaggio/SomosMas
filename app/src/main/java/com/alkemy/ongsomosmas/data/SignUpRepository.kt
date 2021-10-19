package com.alkemy.ongsomosmas.data

import com.alkemy.ongsomosmas.data.model.User
import retrofit2.Retrofit
import javax.inject.Inject

open class SignUpRepository @Inject constructor(private val registerDataSource: RegisterDataSource) {

    suspend fun register(name: String, password: String, email: String) =
        registerDataSource.addUser(User(name, email, password))

}