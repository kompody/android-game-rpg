// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.kotlin

import androidx.core.os.bundleOf

fun <V> Map<String, V>.toBundle() =
    bundleOf(
        *map { it.toPair() }.toTypedArray()
    )