// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.android

import android.graphics.Paint
import android.graphics.Rect

fun Paint.getTextBounds(text: String): Rect {
    val bounds = Rect()

    getTextBounds(text, 0, text.length, bounds)

    return bounds
}