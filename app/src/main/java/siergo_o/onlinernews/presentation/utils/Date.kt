package siergo_o.onlinernews.presentation.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("ConstantLocale")
private val DATE_FORMAT_NORMAL = SimpleDateFormat("HH:mm, dd MMMM, yyyy", Locale.getDefault()).apply {
    timeZone = TimeZone.getTimeZone("UTC")
}

fun Long.asFormattedDate(): String =
        synchronized(DATE_FORMAT_NORMAL) {
            DATE_FORMAT_NORMAL.format(Date(this))
        }
