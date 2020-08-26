package com.kotlin.demo.dagger.component

import android.app.Application
import com.kotlin.demo.DemoApplication
import com.kotlin.demo.dagger.module.ActivityModule
import com.kotlin.demo.dagger.module.ApplicationModule
import com.kotlin.demo.dagger.module.DatabaseModule
import com.kotlin.demo.dagger.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class, ActivityModule::class, ApplicationModule::class, NetworkModule::class, DatabaseModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: DemoApplication)
}