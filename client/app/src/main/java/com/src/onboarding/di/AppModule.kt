package com.src.onboarding.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule(var context: Context) {
    @Provides
    fun provideContext(): Context {
        return context
    }
}