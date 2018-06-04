package com.parapaparam.chiphy.di

import com.parapaparam.chiphy.ui.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, ServerModule::class])
@Singleton
interface AppComponent {
    fun inject(saFragment: SearchFragment)
}