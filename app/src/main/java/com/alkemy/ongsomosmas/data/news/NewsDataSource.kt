package com.alkemy.ongsomosmas.data.news

import com.alkemy.ongsomosmas.data.BaseDataSource
import javax.inject.Inject

class NewsDataSource @Inject constructor(private val newsService: NewsService) : BaseDataSource() {

    suspend fun getNews() = getResult { newsService.news() }
}
