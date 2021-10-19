package com.alkemy.ongsomosmas.data.welcome

import javax.inject.Inject

class WelcomeRepository @Inject constructor(
    private val remote: WelcomeDataSource
) {
    suspend fun welcome() =
        remote.getWelcome()
}