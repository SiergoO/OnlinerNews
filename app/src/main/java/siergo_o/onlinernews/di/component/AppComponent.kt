package siergo_o.onlinernews.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import siergo_o.onlinernews.App
import siergo_o.onlinernews.di.module.*
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class, PresenterModule::class, DomainModule::class, AdapterModule::class, BuildersModule::class, AndroidInjectionModule::class])

interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}