package com.alkemy.ongsomosmas.di

import android.content.Context
import com.alkemy.ongsomosmas.data.LoginDataSource
import com.alkemy.ongsomosmas.data.LoginService
import com.alkemy.ongsomosmas.data.RegisterDataSource
import com.alkemy.ongsomosmas.data.RegisterService
import com.alkemy.ongsomosmas.data.Preferences
import com.alkemy.ongsomosmas.data.activities.ActivitiesService
import com.alkemy.ongsomosmas.data.member.MemberDataSource
import com.alkemy.ongsomosmas.data.member.MemberService
import com.alkemy.ongsomosmas.data.contact.ContactDataSource
import com.alkemy.ongsomosmas.data.contact.ContactService
import com.alkemy.ongsomosmas.data.news.NewsService
import com.alkemy.ongsomosmas.data.testimonials.TestimonialDataSource
import com.alkemy.ongsomosmas.data.testimonials.TestimonialService
import com.alkemy.ongsomosmas.data.welcome.WelcomeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
class DataModule {

    val API_URL = "http://ongapi.alkemy.org/"

    @ApiAlkemy
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Provides
    fun provideRegisterService(@ApiAlkemy retrofit: Retrofit) =
        retrofit.create(RegisterService::class.java)

    @Provides
    fun provideRegisterDataSource(registerService: RegisterService): RegisterDataSource {
        return RegisterDataSource(registerService)
    }

    @Provides
    fun provideLoginService(@ApiAlkemy retrofit: Retrofit) =
        retrofit.create(LoginService::class.java)

    @Provides
    fun provideLoginDataSource(loginService: LoginService): LoginDataSource {
        return LoginDataSource(loginService)
    }

    @Provides
    fun provideWelcomeService(@ApiAlkemy retrofit: Retrofit) =
        retrofit.create(WelcomeService::class.java)

    @Provides
    fun provideNewsService(@ApiAlkemy retrofit: Retrofit) =
        retrofit.create(NewsService::class.java)

    @Provides
    fun providePreference(@ApplicationContext context: Context): Preferences {
        return Preferences(context)
    }

    @Provides
    fun provideTestimonialService(@ApiAlkemy retrofit: Retrofit) =
        retrofit.create(TestimonialService::class.java)

    @Provides
    fun provideTestimonialDataSource(testimonialService: TestimonialService): TestimonialDataSource {
        return TestimonialDataSource(testimonialService)
    }

    @Provides
    fun provideActivitiesDataSource(@ApiAlkemy retrofit: Retrofit) =
        retrofit.create(ActivitiesService::class.java)

    @Provides
    fun provideMemberService(@ApiAlkemy retrofit: Retrofit) =
        retrofit.create(MemberService::class.java)

    @Provides
    fun provideMemberDataSource(memberService: MemberService): MemberDataSource {
        return MemberDataSource(memberService)
    }

    @Provides
    fun provideContactService(@ApiAlkemy retrofit: Retrofit) =
        retrofit.create(ContactService::class.java)

    @Provides
    fun provideContactDataSource(contactService: ContactService): ContactDataSource {
        return ContactDataSource(contactService)
    }
}