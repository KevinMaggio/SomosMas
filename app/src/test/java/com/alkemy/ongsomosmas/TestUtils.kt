package com.alkemy.ongsomosmas

import com.alkemy.ongsomosmas.data.Resource
import com.alkemy.ongsomosmas.data.model.*
import com.alkemy.ongsomosmas.ui.login.LoginFormState
import com.alkemy.ongsomosmas.ui.signup.SignUpFormState
import retrofit2.Response

val testNews = listOf(
    NewsResponse(
        id = 1,
        name = "News 1",
        content = "Content 1",
        image = null,
        createdAt = "2021-09-16T22:37:44.000000Z"
    ),
    NewsResponse(
        id = 2,
        name = "News 2",
        content = "Content 2",
        image = null,
        createdAt = "2021-09-16T22:37:44.000000Z"
    ),
    NewsResponse(
        id = 3,
        name = "News 3",
        content = "Content 3",
        image = null,
        createdAt = "2021-09-16T22:37:44.000000Z"
    )
)

val testNew = testNews[0]

val newsResourceSuccess = Resource.success(testNews)

val newsResourceError = Resource.error<List<NewsResponse>>("Error")

val testAboutUs = listOf(
    MemberResponse(
        createdAt = "2021-09-16T22:37:44.000000Z",
        deletedAt = "2021-09-16T22:37:44.000000Z",
        description = " Content 1",
        facebookUrl = "facebook 1",
        groupId = 1,
        id = 1,
        image = "image 1",
        linkedinUrl = "linkedin 1",
        name = "Person 1",
        updatedAt = "2021-09-16T22:37:44.000000Z"
    ),
    MemberResponse(
        createdAt = "2021-09-16T22:37:44.000000Z",
        deletedAt = "2021-09-16T22:37:44.000000Z",
        description = " Content 2",
        facebookUrl = "facebook 2",
        groupId = 2,
        id = 2,
        image = "image 2",
        linkedinUrl = "linkedin 2",
        name = "Person 2",
        updatedAt = "2021-09-16T22:37:44.000000Z"
    ),
    MemberResponse(
        createdAt = "2021-09-16T22:37:44.000000Z",
        deletedAt = "2021-09-16T22:37:44.000000Z",
        description = " Content 3",
        facebookUrl = "facebook 3",
        groupId = 3,
        id = 3,
        image = "image 3",
        linkedinUrl = "linkedin 3",
        name = "Person 3",
        updatedAt = "2021-09-16T22:37:44.000000Z"
    )
)

val newsRetrofitResponseSuccess = Response.success(ApiResponse(true, testNews, "OK"))

val testAbout = testAboutUs[0]

val aboutUsResourceSuccess = Resource.success(testAboutUs)

val aboutUsResourceError = Resource.error<List<MemberResponse>>("Error")

val aboutUsRetrofitResponseSuccess = Response.success(ApiResponse(true, testAbout, "OK"))

val signUpFormStateNameError =
    SignUpFormState(nameError = R.string.error_state_msg_invalid_name, isDataValid = false)

val signUpFormStateEmailError =
    SignUpFormState(emailError = R.string.error_state_msg_invalid_email, isDataValid = false)

val signUpFormStateConfirmPasswordError = SignUpFormState(
    confirmPasswordError = R.string.error_state_msg_mismatched_password,
    isDataValid = false
)

val signUpFormStateSuccess = SignUpFormState(isDataValid = true)

val loginFormStateEmailError =
    LoginFormState(emailError = R.string.error_state_msg_invalid_email, isDataValid = false)

val loginFormStatePasswordError = LoginFormState(
    passwordError = R.string.error_state_msg_invalid_password_signUp,
    isDataValid = false
)

val loginFormStateSuccess = LoginFormState(isDataValid = true)


// welcome
val testWelcome = listOf(
    WelcomeResponse(name = null, content = null, image = null),
    WelcomeResponse(name = "name 1", content = "content 1", image = "image 1"),
    WelcomeResponse(name = "name 2", content = "content 2", image = "image 2")
)
val welcomeResourceSuccess = Resource.success(testWelcome)
val welcomeResourceError = Resource.error<List<WelcomeResponse>>("Error")
val welcomeRetrofitResponseSuccess = Response.success(ApiResponse(true, testWelcome, "OK"))

// testimonials
val testTesimonials = listOf(
    TestimonialResponse(
        createdAt = "2021-09-16T22:37:44.000000Z",
        deletedAt = null,
        description = null,
        groupId = null,
        id = 1,
        image = "image 1",
        name = "name 1",
        updatedAt = "2021-09-16T22:37:44.000000Z"
    ),
    TestimonialResponse(
        createdAt = "2021-09-16T22:37:44.000000Z",
        deletedAt = "2021-09-16T22:37:44.000000Z",
        description = "description 2",
        groupId = 1,
        id = 2,
        image = "image 2",
        name = "name 2",
        updatedAt = "2021-09-16T22:37:44.000000Z"
    ),
    TestimonialResponse(
        createdAt = "2021-09-16T22:37:44.000000Z",
        deletedAt = "2021-09-16T22:37:44.000000Z",
        description = " description 3",
        groupId = 2,
        id = 3,
        image = "image 3",
        name = "name 3",
        updatedAt = "2021-09-16T22:37:44.000000Z"
    )
)
val testimonialResourceSuccess = Resource.success(testTesimonials)
val testimonialResourceError = Resource.error<List<TestimonialResponse>>("Error")
val testimonialRetrofitResponseSuccess = Response.success(ApiResponse(true, testTesimonials, "OK"))
