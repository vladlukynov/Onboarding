package com.src.onboarding.di

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
        DomainLoginModule::class]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}