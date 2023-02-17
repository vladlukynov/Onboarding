package com.src.onboarding.di

import com.src.onboarding.domain.repository.CourseRepository
import com.src.onboarding.domain.usecase.course.GetColleaguesUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainCourseModule {
    @Singleton
    @Provides
    fun provideGetColleaguesUseCase(courseRepository: CourseRepository): GetColleaguesUseCase {
        return GetColleaguesUseCase(courseRepository = courseRepository)
    }
}