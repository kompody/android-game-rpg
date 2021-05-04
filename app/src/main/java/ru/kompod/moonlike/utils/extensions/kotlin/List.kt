// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.kotlin

inline fun<T> listOf(initializerBlock: MutableList<T>.() -> Unit = {}): List<T> {
    return mutableListOf<T>().apply(initializerBlock)
}

inline fun<reified T> mergeLists(vararg lists: List<T>): List<T> {
    return listOf(*lists.flatMap { it }.toTypedArray())
}