package siergo_o.onlinernews.presentation.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun showKeyboard(context: Context, view: View?) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    view?.also { v ->
        v.requestFocus()
        imm.toggleSoftInput(0, 0)
    }
}

fun hideKeyboard(context: Context, view: View?) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    view?.also { v ->
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }
}