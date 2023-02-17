package com.src.onboarding.app

import android.app.Application
import com.src.onboarding.di.AppComponent
import com.src.onboarding.di.AppModule
import com.src.onboarding.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context = this))
            .build()
    }
}