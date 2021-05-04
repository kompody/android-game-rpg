// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.android

import android.content.Context
import android.content.Intent
import android.util.DisplayMetrics
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.annotation.StyleRes
import androidx.core.content.getSystemService

fun Context.getWindowWidth() =
    DisplayMetrics()
        .apply {
            getSystemService<WindowManager>()
                ?.defaultDisplay
                ?.getMetrics(this)
        }
        .widthPixels

fun intentOf(action: String, block: Intent.() -> Unit): Intent {
    return Intent(action).apply(block)
}

val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)

fun Context.toContextThemeWrapper(@StyleRes themeRes: Int): ContextThemeWrapper {
    return ContextThemeWrapper(this, themeRes)
}