package com.src.onboarding.di

import com.src.onboarding.data.local.repository.LocalUserRepository
import com.src.onboarding.data.local.repository.LocalUserRepositoryImpl
import com.src.onboarding.data.remote.session.SessionStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalModule {
    @Singleton
    @Provides
    fun provideLocalUserRepository(sessionStorage: SessionStorage): LocalUserRepository {
        return LocalUserRepositoryImpl(sessionStorage = sessionStorage)
    }
}