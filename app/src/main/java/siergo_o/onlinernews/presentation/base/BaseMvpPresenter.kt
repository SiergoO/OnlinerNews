package com.ipictheaters.ipic.presentation.base

import com.softeq.android.mvp.BasePresenter
import siergo_o.onlinernews.presentation.base.BaseMvpContract

abstract class BaseMvpPresenter<U : BaseMvpContract.Ui, S : BaseMvpContract.Presenter.State> :
    BasePresenter<U, S>(), BaseMvpContract.Presenter<U, S> {

    override fun start() {

    }

    override fun stop() {

    }

    override fun destroy(willBeRecreated: Boolean) {

    }

    override fun saveState(state: S) {

    }

    override fun restoreState(savedState: S?) {

    }
}