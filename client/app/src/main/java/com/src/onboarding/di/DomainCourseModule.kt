package com.src.onboarding.di

import com.src.onboarding.domain.repository.CourseRepository
import com.src.onboarding.domain.usecase.course.*
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

    @Singleton
    @Provides
    fun provideGetCoursesUseCase(courseRepository: CourseRepository): GetCoursesUseCase {
        return GetCoursesUseCase(courseRepository = courseRepository)
    }

    @Singleton
    @Provides
    fun provideGetStartedCoursesForUserUseCase(courseRepository: CourseRepository): GetStartedCoursesForUserUseCase {
        return GetStartedCoursesForUserUseCase(courseRepository = courseRepository)
    }

    @Singleton
    @Provides
    fun provideGetStartedCoursesByIdForUserUseCase(courseRepository: CourseRepository): GetStartedCoursesByIdForUserUseCase {
        return GetStartedCoursesByIdForUserUseCase(courseRepository = courseRepository)
    }

    @Singleton
    @Provides
    fun provideGetCoursesForCoursePageUseCase(courseRepository: CourseRepository): GetCoursesForCoursePageUseCase {
        return GetCoursesForCoursePageUseCase(courseRepository = courseRepository)
    }
}