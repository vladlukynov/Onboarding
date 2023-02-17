package com.src.onboarding.di

import android.content.Context
import com.src.onboarding.data.remote.dataSource.login.LoginDataSource
import com.src.onboarding.data.remote.dataSource.login.LoginDataSourceImpl
import com.src.onboarding.data.remote.dataSource.user.UserDataSource
import com.src.onboarding.data.remote.dataSource.user.UserDataSourceImpl
import com.src.onboarding.data.remote.interceptor.TokenInterceptor
import com.src.onboarding.data.remote.model.login.login.LoginMapper
import com.src.onboarding.data.remote.service.LoginService
import com.src.onboarding.data.remote.service.UserService
import com.src.onboarding.data.remote.session.SessionStorage
import com.src.onboarding.data.remote.session.SessionStorageImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {
    @Singleton
    @Provides
    @Named(NAME_OKHTTP_WITHOUT_TOKEN)
    fun provideOkHttpClient(
    ): OkHttpClient =
        OkHttpClient().newBuilder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .build()

    @Singleton
    @Provides
    @Named(NAME_RETROFIT_WITHOUT_TOKEN)
    fun provideRetrofit(@Named(NAME_OKHTTP_WITHOUT_TOKEN) okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    @Named(NAME_OKHTTP_WITH_TOKEN)
    fun provideOkHttpClientToken(
        tokenInterceptor: TokenInterceptor
    ): OkHttpClient =
        OkHttpClient().newBuilder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .addInterceptor(tokenInterceptor)
            .build()

    @Singleton
    @Provides
    @Named(NAME_RETROFIT_WITH_TOKEN)
    fun provideRetrofitToken(@Named(NAME_OKHTTP_WITH_TOKEN) okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideSessionStorage(context: Context): SessionStorage {
        return SessionStorageImpl(context)
    }

    @Singleton
    @Provides
    fun provideUserService(@Named(NAME_OKHTTP_WITH_TOKEN) retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun provideLoginService(@Named(NAME_RETROFIT_WITHOUT_TOKEN) retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    @Singleton
    @Provides
    fun provideUserDataSource(
        userService: UserService
    ): UserDataSource {
        return UserDataSourceImpl(userService = userService)
    }

    @Singleton
    @Provides
    fun provideLoginDataSource(
        loginService: LoginService,
        sessionStorage: SessionStorage,
        loginMapper: LoginMapper
    ): LoginDataSource {
        return LoginDataSourceImpl(
            loginService = loginService,
            sessionStorage = sessionStorage,
            loginMapper = loginMapper
        )
    }

    companion object {
        const val NAME_RETROFIT_WITH_TOKEN = "retrofit_with_token"
        const val NAME_RETROFIT_WITHOUT_TOKEN = "retrofit_without_token"
        const val NAME_OKHTTP_WITH_TOKEN = "okhttp_with_token"
        const val NAME_OKHTTP_WITHOUT_TOKEN = "okhttp_without_token"
        const val BASE_URL = "http://192.168.0.101:8080/"
        const val USER_SERVICE_BASE_URL = "user-service"
    }
}
