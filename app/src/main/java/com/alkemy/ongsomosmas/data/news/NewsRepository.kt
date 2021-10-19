package com.alkemy.ongsomosmas.data.news

import javax.inject.Inject

open class NewsRepository @Inject constructor(
    private val remote: NewsDataSource
) {
    suspend fun news() =
        remote.getNews()
}