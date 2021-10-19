package com.alkemy.ongsomosmas.data

import javax.inject.Inject

/**
 * Class that requests authentication and user information from the remote data source
 */

class LoginRepository @Inject constructor(
    private val remote: LoginDataSource
) {
    suspend fun login(email: String, password: String) = remote.login(email, password)
}