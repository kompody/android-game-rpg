// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.kotlin

inline fun <T, R : Comparable<R>> Iterable<T>.sortedBy(ascending: Boolean,
                                                       crossinline selector: (T) -> R): List<T> {
    return if (ascending)
        sortedBy(selector)
    else
        sortedByDescending(selector)
}
