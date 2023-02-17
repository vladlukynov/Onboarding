package com.src.onboarding.di

import com.src.onboarding.data.local.repository.LocalUserRepository
import com.src.onboarding.data.remote.dataSource.course.CourseDataSource
import com.src.onboarding.data.remote.dataSource.login.LoginDataSource
import com.src.onboarding.data.remote.dataSource.user.UserDataSource
import com.src.onboarding.data.repository.CourseRepositoryImpl
import com.src.onboarding.data.repository.LoginRepositoryImpl
import com.src.onboarding.data.repository.UserRepositoryImpl
import com.src.onboarding.domain.repository.CourseRepository
import com.src.onboarding.domain.repository.LoginRepository
import com.src.onboarding.domain.repository.UserRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {
    @Provides
    fun provideUserRepository(userDataSource: UserDataSource): UserRepository {
        return UserRepositoryImpl(userDataSource = userDataSource)
    }

    @Provides
    fun provideLoginRepository(
        loginDataSource: LoginDataSource,
        localUserRepository: LocalUserRepository
    ): LoginRepository {
        return LoginRepositoryImpl(
            loginDataSource = loginDataSource,
            userLocalRepository = localUserRepository
        )
    }

    @Provides
    fun provideCourseRepository(
        courseDataSource: CourseDataSource
    ): CourseRepository {
        return CourseRepositoryImpl(courseDataSource = courseDataSource)
    }
}