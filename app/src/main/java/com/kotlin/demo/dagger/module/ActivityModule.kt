package com.kotlin.demo.dagger.module

import com.kotlin.demo.dagger.provider.MoviesFragmentProvider
import com.kotlin.demo.presentation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidSupportInjectionModule::class])
interface ActivityModule {

    @ContributesAndroidInjector(
        modules = [
            MoviesFragmentProvider::class
        ]
    )
    fun mainActivityInjector(): MainActivity
}