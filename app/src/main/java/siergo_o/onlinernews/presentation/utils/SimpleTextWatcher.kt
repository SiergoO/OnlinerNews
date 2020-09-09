package siergo_o.onlinernews.presentation.utils

import android.text.Editable
import android.text.TextWatcher

class SimpleTextWatcher(private val listener: (CharSequence) -> Unit) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        // do nothing
    }

    override fun afterTextChanged(s: Editable) {
        // do nothing
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        listener(s)
    }
}