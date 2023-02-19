package com.src.onboarding.di

import com.src.onboarding.data.local.repository.LocalUserRepository
import com.src.onboarding.data.remote.dataSource.course.CourseDataSource
import com.src.onboarding.data.remote.dataSource.employee.EmployeeDataSource
import com.src.onboarding.data.remote.dataSource.login.LoginDataSource
import com.src.onboarding.data.remote.dataSource.task.TaskDataSource
import com.src.onboarding.data.remote.dataSource.user.UserDataSource
import com.src.onboarding.data.repository.*
import com.src.onboarding.domain.repository.*
import dagger.Module
import dagger.Provides

@Module
class DataModule {
    @Provides
    fun provideUserRepository(
        userDataSource: UserDataSource,
        localUserRepository: LocalUserRepository
    ): UserRepository {
        return UserRepositoryImpl(
            userDataSource = userDataSource,
            userLocalUserRepository = localUserRepository
        )
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

    @Provides
    fun provideEmployeeRepository(
        employeeDataSource: EmployeeDataSource
    ): EmployeeRepository {
        return EmployeeRepositoryImpl(employeeDataSource = employeeDataSource)
    }

    @Provides
    fun provideTaskRepository(
        taskDataSource: TaskDataSource
    ): TaskRepository {
        return TaskRepositoryImpl(taskDataSource = taskDataSource)
    }
}