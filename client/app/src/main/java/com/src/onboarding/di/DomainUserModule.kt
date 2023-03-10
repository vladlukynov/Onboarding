package com.src.onboarding.di

import com.src.onboarding.domain.repository.UserRepository
import com.src.onboarding.domain.usecase.user.*
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

    @Singleton
    @Provides
    fun provideGetUserProfileUseCase(userRepository: UserRepository): GetUserProfileUseCase {
        return GetUserProfileUseCase(userRepository = userRepository)
    }

    @Singleton
    @Provides
    fun provideGetActivitiesUseCase(userRepository: UserRepository): GetActivitiesUseCase {
        return GetActivitiesUseCase(userRepository = userRepository)
    }

    @Singleton
    @Provides
    fun provideLogoutUseCase(userRepository: UserRepository): LogoutUseCase {
        return LogoutUseCase(userRepository = userRepository)
    }

    @Singleton
    @Provides
    fun provideEditProfileUseCase(userRepository: UserRepository): EditProfileUseCase {
        return EditProfileUseCase(userRepository = userRepository)
    }

    @Singleton
    @Provides
    fun provideGetUserByIdUseCase(userRepository: UserRepository): GetUserByIdUseCase {
        return GetUserByIdUseCase(userRepository = userRepository)
    }

    @Singleton
    @Provides
    fun provideGetIdUseCase(userRepository: UserRepository): GetIdUseCase {
        return GetIdUseCase(userRepository = userRepository)
    }

    @Singleton
    @Provides
    fun provideAddNewAnswerUseCase(userRepository: UserRepository): AddNewAnswerUseCase {
        return AddNewAnswerUseCase(userRepository = userRepository)
    }

    @Singleton
    @Provides
    fun provideAddNewQuestionUseCase(userRepository: UserRepository): AddNewQuestionUseCase {
        return AddNewQuestionUseCase(userRepository = userRepository)
    }

    @Singleton
    @Provides
    fun provideGetQuestionsUseCase(userRepository: UserRepository): GetQuestionsUseCase {
        return GetQuestionsUseCase(userRepository = userRepository)
    }
}