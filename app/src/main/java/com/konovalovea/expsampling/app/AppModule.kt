package com.konovalovea.expsampling.app

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
open class AppModule(private val application: Application) {

    @Provides
    fun providerContext(): Context = application.applicationContext

    @Provides
    fun application(): Application = application
}