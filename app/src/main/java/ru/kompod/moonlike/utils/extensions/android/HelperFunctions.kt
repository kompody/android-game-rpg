// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.android

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun showSnackbar(view: View, message: String, duration: Int = Snackbar.LENGTH_LONG, block: Snackbar.() -> Unit = {}) {
    Snackbar.make(view, message, duration).apply(block).show()
}