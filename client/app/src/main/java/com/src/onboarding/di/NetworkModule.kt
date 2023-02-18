package com.src.onboarding.di

import android.content.Context
import com.src.onboarding.data.remote.dataSource.course.CourseDataSource
import com.src.onboarding.data.remote.dataSource.course.CourseDataSourceImpl
import com.src.onboarding.data.remote.dataSource.employee.EmployeeDataSource
import com.src.onboarding.data.remote.dataSource.employee.EmployeeDataSourceImpl
import com.src.onboarding.data.remote.dataSource.login.LoginDataSource
import com.src.onboarding.data.remote.dataSource.login.LoginDataSourceImpl
import com.src.onboarding.data.remote.dataSource.task.TaskDataSource
import com.src.onboarding.data.remote.dataSource.task.TaskDataSourceImpl
import com.src.onboarding.data.remote.dataSource.user.UserDataSource
import com.src.onboarding.data.remote.dataSource.user.UserDataSourceImpl
import com.src.onboarding.data.remote.interceptor.TokenInterceptor
import com.src.onboarding.data.remote.model.course.colleague.ColleagueMapper
import com.src.onboarding.data.remote.model.course.course.CourseMapper
import com.src.onboarding.data.remote.model.course.mainCourse.MainCourseMapper
import com.src.onboarding.data.remote.model.employee.post.PostMapper
import com.src.onboarding.data.remote.model.employee.team.TeamMapper
import com.src.onboarding.data.remote.model.login.login.LoginMapper
import com.src.onboarding.data.remote.model.task.TaskMapper
import com.src.onboarding.data.remote.model.user.notification.NotificationMapper
import com.src.onboarding.data.remote.model.user.user_profile.UserProfileMapper
import com.src.onboarding.data.remote.service.*
import com.src.onboarding.data.remote.session.SessionController
import com.src.onboarding.data.remote.session.SessionStorage
import com.src.onboarding.data.remote.session.SessionStorageImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {
    @Singleton
    @Provides
    @Named(NAME_OKHTTP_WITHOUT_TOKEN)
    fun provideOkHttpClient(
    ): OkHttpClient =
        OkHttpClient().newBuilder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .build()

    @Singleton
    @Provides
    @Named(NAME_RETROFIT_WITHOUT_TOKEN)
    fun provideRetrofit(@Named(NAME_OKHTTP_WITHOUT_TOKEN) okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    @Named(NAME_OKHTTP_WITH_TOKEN)
    fun provideOkHttpClientToken(
        tokenInterceptor: TokenInterceptor
    ): OkHttpClient =
        OkHttpClient().newBuilder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .addInterceptor(tokenInterceptor)
            .build()

    @Singleton
    @Provides
    @Named(NAME_RETROFIT_WITH_TOKEN)
    fun provideRetrofitToken(@Named(NAME_OKHTTP_WITH_TOKEN) okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideSessionStorage(context: Context): SessionStorage {
        return SessionStorageImpl(context)
    }

    @Singleton
    @Provides
    fun provideUserService(@Named(NAME_RETROFIT_WITH_TOKEN) retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun provideLoginService(@Named(NAME_RETROFIT_WITHOUT_TOKEN) retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    @Singleton
    @Provides
    fun provideSessionService(@Named(NAME_RETROFIT_WITH_TOKEN) retrofit: Retrofit): SessionService {
        return retrofit.create(SessionService::class.java)
    }

    @Singleton
    @Provides
    fun provideCourseService(@Named(NAME_RETROFIT_WITH_TOKEN) retrofit: Retrofit): CourseService {
        return retrofit.create(CourseService::class.java)
    }

    @Singleton
    @Provides
    fun provideTaskService(@Named(NAME_RETROFIT_WITH_TOKEN) retrofit: Retrofit): TaskService {
        return retrofit.create(TaskService::class.java)
    }

    @Singleton
    @Provides
    fun provideEmployeeService(@Named(NAME_RETROFIT_WITH_TOKEN) retrofit: Retrofit): EmployeeService {
        return retrofit.create(EmployeeService::class.java)
    }

    @Singleton
    @Provides
    fun provideSessionController(
        sessionService: SessionService,
        sessionStorage: SessionStorage
    ): SessionController {
        return SessionController(
            sessionService = sessionService,
            sessionStorage = sessionStorage
        )
    }

    @Singleton
    @Provides
    fun provideUserDataSource(
        userService: UserService,
        notificationMapper: NotificationMapper,
        userProfileMapper: UserProfileMapper
    ): UserDataSource {
        return UserDataSourceImpl(
            userService = userService,
            notificationMapper = notificationMapper,
            userProfileMapper = userProfileMapper
        )
    }

    @Singleton
    @Provides
    fun provideLoginDataSource(
        loginService: LoginService,
        sessionStorage: SessionStorage,
        sessionService: SessionService,
        loginMapper: LoginMapper
    ): LoginDataSource {
        return LoginDataSourceImpl(
            loginService = loginService,
            sessionStorage = sessionStorage,
            sessionService = sessionService,
            loginMapper = loginMapper
        )
    }

    @Singleton
    @Provides
    fun provideCourseDataSource(
        courseService: CourseService,
        colleagueMapper: ColleagueMapper,
        mainCourseMapper: MainCourseMapper,
        sessionController: SessionController,
        courseMapper: CourseMapper
    ): CourseDataSource {
        return CourseDataSourceImpl(
            courseService = courseService,
            colleagueMapper = colleagueMapper,
            mainCourseMapper = mainCourseMapper,
            sessionController = sessionController,
            courseMapper = courseMapper
        )
    }

    @Singleton
    @Provides
    fun provideEmployeeDataSource(
        employeeService: EmployeeService,
        postMapper: PostMapper,
        teamMapper: TeamMapper
    ): EmployeeDataSource {
        return EmployeeDataSourceImpl(
            employeeService = employeeService,
            postMapper = postMapper,
            teamMapper = teamMapper
        )
    }

    @Singleton
    @Provides
    fun provideTaskDataSource(
        taskService: TaskService,
        taskMapper: TaskMapper
    ): TaskDataSource {
        return TaskDataSourceImpl(taskService = taskService, taskMapper = taskMapper)
    }

    companion object {
        const val NAME_RETROFIT_WITH_TOKEN = "retrofit_with_token"
        const val NAME_RETROFIT_WITHOUT_TOKEN = "retrofit_without_token"
        const val NAME_OKHTTP_WITH_TOKEN = "okhttp_with_token"
        const val NAME_OKHTTP_WITHOUT_TOKEN = "okhttp_without_token"
        const val BASE_URL = "http://192.168.0.115:8080/"
        const val USER_SERVICE_BASE_URL = "user-service"
        const val COURSE_SERVICE_BASE_URL = "course-service"
    }
}
