// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.kotlin

import android.content.res.Resources
import java.math.RoundingMode

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Float.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Double.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

fun Float.setScale(newScale: Int, roundingMode: RoundingMode = RoundingMode.HALF_UP): Float {
    return toBigDecimal().setScale(newScale, roundingMode).toFloat()
}
