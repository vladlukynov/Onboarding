package com.src.onboarding.di

import com.src.onboarding.domain.repository.UserRepository
import com.src.onboarding.domain.usecase.user.ClearNotificationsUseCase
import com.src.onboarding.domain.usecase.user.GetCountNotificationUseCase
import com.src.onboarding.domain.usecase.user.GetNotificationsUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainUserModule {
    @Singleton
    @Provides
    fun provideGetNotificationsUseCase(userRepository: UserRepository): GetNotificationsUseCase {
        return GetNotificationsUseCase(userRepository = userRepository)
    }

    @Singleton
    @Provides
    fun provideGetCountNotificationUseCase(userRepository: UserRepository): GetCountNotificationUseCase {
        return GetCountNotificationUseCase(userRepository = userRepository)
    }

    @Singleton
    @Provides
    fun provideClearNotificationsUseCAse(userRepository: UserRepository): ClearNotificationsUseCase {
        return ClearNotificationsUseCase(userRepository = userRepository)
    }
}