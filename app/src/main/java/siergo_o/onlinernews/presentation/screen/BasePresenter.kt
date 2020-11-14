package siergo_o.onlinernews.presentation.screen

import dagger.android.support.DaggerFragment

interface BasePresenter {
    fun start(ui: DaggerFragment)
    fun stop()
}