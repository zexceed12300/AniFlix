package com.zexceed.aniflix.utils

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Constants {

    const val TAG = "AniFlix:::::::"

    fun Context.createImageProgress(): CircularProgressDrawable {
        return CircularProgressDrawable(this).apply {
            strokeWidth = 5f
            centerRadius = 30f
            start()
        }
    }

    fun Long.formatLong(pattern: String, locale: Locale = Locale.getDefault()): String {
        val dateFormat = SimpleDateFormat(pattern, locale)
        return dateFormat.format(Date(this))
    }

}