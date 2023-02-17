package com.src.onboarding.di

import com.src.onboarding.domain.repository.LoginRepository
import com.src.onboarding.domain.usecase.login.SignInUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainUserModule {
    @Singleton
    @Provides
    fun provideSignInUseCase(loginRepository: LoginRepository): SignInUseCase {
        return SignInUseCase(loginRepository = loginRepository)
    }
}