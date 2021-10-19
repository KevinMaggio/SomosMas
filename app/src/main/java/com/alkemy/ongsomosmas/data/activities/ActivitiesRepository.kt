package com.alkemy.ongsomosmas.data.activities

import javax.inject.Inject

class ActivitiesRepository @Inject constructor(
    private val remote: ActivitiesDataSource
) {
    suspend fun activities() =
        remote.getActivities()
}