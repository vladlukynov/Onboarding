package com.src.onboarding.di

import com.src.onboarding.presentation.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class,
        NetworkModule::class,
        DataModule::class,
        DomainUserModule::class]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}