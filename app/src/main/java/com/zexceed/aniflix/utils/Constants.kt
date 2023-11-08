package com.zexceed.aniflix.utils

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

object Constants {

    const val TAG = "AniFlix:::::::"

    fun Context.createImageProgress(): CircularProgressDrawable {
        return CircularProgressDrawable(this).apply {
            strokeWidth = 5f
            centerRadius = 30f
            start()
        }
    }

}