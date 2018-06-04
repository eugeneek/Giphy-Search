package com.parapaparam.chiphy.di

import com.parapaparam.chiphy.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton




@Module
class AppModule constructor(
        private val app: App
) {

    @Provides
    @Singleton
    fun provideApp() = app
}