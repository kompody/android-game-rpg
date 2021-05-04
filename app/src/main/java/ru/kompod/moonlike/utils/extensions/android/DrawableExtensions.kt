// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.android

import android.graphics.drawable.Drawable

fun Drawable.setAlpha(alpha: Float) {
    this.alpha = (MAX_ALPHA_VALUE * alpha).toInt()
}

private const val MAX_ALPHA_VALUE = 255