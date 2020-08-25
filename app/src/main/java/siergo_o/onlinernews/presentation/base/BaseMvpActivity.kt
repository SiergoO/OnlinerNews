package siergo_o.onlinernews.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.softeq.android.mvp.MvpActivityDelegate
import com.softeq.android.mvp.PresenterStateHolder


abstract class BaseMvpActivity<U : BaseMvpContract.Ui, S : BaseMvpContract.Presenter.State, P : BaseMvpContract.Presenter<U, S>> :
    AppCompatActivity(), MvpActivityDelegate.Callback<U, S, P>, BaseMvpContract.Ui {

    private lateinit var mMvpDelegate: MvpActivityDelegate<U, S, P>

    protected val presenter: P
        get() = mMvpDelegate.presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMvpDelegate = MvpActivityDelegate<U, S, P>(this, createPresenterStateHolder())
        mMvpDelegate.onCreate(savedInstanceState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mMvpDelegate.onPostCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mMvpDelegate.onDestroy()
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
