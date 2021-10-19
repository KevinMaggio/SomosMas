package com.alkemy.ongsomosmas.data.welcome

import com.alkemy.ongsomosmas.data.BaseDataSource
import javax.inject.Inject

class WelcomeDataSource @Inject constructor(private val welcomeService: WelcomeService) :
    BaseDataSource() {

    suspend fun getWelcome() = getResult { welcomeService.welcome() }
}
