package siergo_o.onlinernews.presentation.base

interface BaseMvpContract {

    interface Ui : com.softeq.android.mvp.Ui {
        fun showErrorPopup(
            errorType: BaseMvpContract.ErrorType,
            flags: Int,
            message: String?,
            error: Throwable,
            vararg args: Any
        )
    }

    interface Presenter<U : Ui, S : Presenter.State> : com.softeq.android.mvp.Presenter<U, S> {
        fun start()
        fun stop()

        interface State : com.softeq.android.mvp.Presenter.State
    }

    enum class ErrorType {
        GENERAL
    }
}