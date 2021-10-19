package com.alkemy.ongsomosmas.data.testimonials

import javax.inject.Inject

class TestimonialRepository @Inject constructor(
    private val testimonialDataSource: TestimonialDataSource
) {
    suspend fun testimonials() = testimonialDataSource.getTestimonials()
}