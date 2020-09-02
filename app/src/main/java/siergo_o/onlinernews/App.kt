package siergo_o.onlinernews;

import android.app.Application
import siergo_o.onlinernews.di.component.AppComponent
import siergo_o.onlinernews.di.component.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = buildComponent()
    }

    private fun buildComponent(): AppComponent = DaggerAppComponent.builder().application(this).build()
}
