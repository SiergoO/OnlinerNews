package siergo_o.onlinernews.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import siergo_o.onlinernews.data.rest.OnlinerApiFactory
import siergo_o.onlinernews.di.module.RetrofitModule
import siergo_o.onlinernews.presentation.screen.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class])

interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(onlinerApiFactory: OnlinerApiFactory)
}