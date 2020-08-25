package com.ipictheaters.ipic.presentation.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.softeq.android.mvp.MvpFragmentDelegate
import com.softeq.android.mvp.PresenterStateHolder
import siergo_o.onlinernews.presentation.base.BaseMvpContract

abstract class BaseMvpFragment<U : BaseMvpContract.Ui, S : BaseMvpContract.Presenter.State, P : BaseMvpContract.Presenter<U, S>> :
    Fragment(), MvpFragmentDelegate.Callback<U, S, P>, BaseMvpContract.Ui {

    private lateinit var mMvpDelegate: MvpFragmentDelegate<U, S, P>

    protected val presenter: P
        get() = mMvpDelegate.presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMvpDelegate = MvpFragmentDelegate(this, createPresenterStateHolder())
        mMvpDelegate.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        mMvpDelegate.onStart()
        presenter.start()
    }

    override fun onStop() {
        super.onStop()
        mMvpDelegate.onStop()
        presenter.stop()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMvpDelegate.onViewCreated(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mMvpDelegate.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMvpDelegate.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mMvpDelegate.onSaveInstanceState(outState)
    }

    override fun showErrorPopup(
        errorType: BaseMvpContract.ErrorType,
        flags: Int,
        message: String?,
        error: Throwable,
        vararg args: Any
    ) {

    }

    protected abstract fun createPresenterStateHolder(): PresenterStateHolder<S>
}
