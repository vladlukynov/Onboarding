package com.src.onboarding.di

import com.src.onboarding.data.remote.model.login.login.LoginMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MapperModule {
    @Singleton
    @Provides
    fun provideLoginMapper(): LoginMapper {
        return LoginMapper()
    }

}