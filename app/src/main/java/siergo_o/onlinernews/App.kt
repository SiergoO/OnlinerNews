package siergo_o.onlinernews;

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import siergo_o.onlinernews.di.component.DaggerAppComponent

class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}
