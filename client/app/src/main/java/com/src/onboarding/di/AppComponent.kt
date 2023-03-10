package com.src.onboarding.di

import com.src.onboarding.presentation.HrActivity
import com.src.onboarding.presentation.LoginActivity
import com.src.onboarding.presentation.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class,
        MapperModule::class,
        NetworkModule::class,
        LocalModule::class,
        DataModule::class,
        DomainUserModule::class,
        DomainLoginModule::class,
        DomainCourseModule::class,
        DomainEmployeeModule::class,
        DomainTaskModule::class]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(hrActivity: HrActivity)
}