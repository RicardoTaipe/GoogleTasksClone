package com.example.googletasksclone.utils

import android.content.Context

fun Context.dpToPx(dp: Int): Int {
    val density = resources.displayMetrics.density
    return (dp * density).toInt()
}