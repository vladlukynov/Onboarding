package com.src.onboarding.di

import com.src.onboarding.domain.repository.EmployeeRepository
import com.src.onboarding.domain.usecase.employee.post.GetPostsUseCase
import com.src.onboarding.domain.usecase.employee.team.GetTeamsUseCase
import com.src.onboarding.domain.usecase.employee.worker.AddWorkerUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainEmployeeModule {
    @Singleton
    @Provides
    fun provideGetPostsUseCase(employeeRepository: EmployeeRepository): GetPostsUseCase {
        return GetPostsUseCase(employeeRepository = employeeRepository)
    }

    @Singleton
    @Provides
    fun provideGetTeamsUseCase(employeeRepository: EmployeeRepository): GetTeamsUseCase {
        return GetTeamsUseCase(employeeRepository = employeeRepository)
    }

    @Singleton
    @Provides
    fun provideAddWorkerUseCase(employeeRepository: EmployeeRepository): AddWorkerUseCase {
        return AddWorkerUseCase(employeeRepository = employeeRepository)
    }
}