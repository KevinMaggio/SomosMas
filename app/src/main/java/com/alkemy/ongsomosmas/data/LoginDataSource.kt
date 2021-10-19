package com.alkemy.ongsomosmas.data

import com.alkemy.ongsomosmas.data.model.Login
import javax.inject.Inject

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource @Inject constructor(private val loginService: LoginService) : BaseDataSource() {

    suspend fun login(email: String, password: String) = getResult { loginService.login(Login(email, password)) }

    fun logout() {
        // TODO: revoke authentication

    }
}
