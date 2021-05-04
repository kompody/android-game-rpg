// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.kotlin

import kotlin.math.truncate

fun Int.shortenMillions() = truncate(this / 1e6).toInt()

fun Int.shortenMillionsFloat() = this / 1e6

fun Int.toMillions() = (this * 1e6).toInt()

fun Int.toBoolean(): Boolean = this != 0