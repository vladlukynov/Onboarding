package com.src.onboarding.di

import com.src.onboarding.data.remote.dataSource.user.UserDataSource
import com.src.onboarding.data.repository.UserRepositoryImpl
import com.src.onboarding.domain.repository.UserRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {
    @Provides
    fun provideUserRepository(userDataSource: UserDataSource): UserRepository {
        return UserRepositoryImpl(userDataSource)
    }
}