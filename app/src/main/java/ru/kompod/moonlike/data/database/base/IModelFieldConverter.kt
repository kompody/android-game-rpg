// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.database.base

interface IModelFieldConverter<T, R> {
    fun saveTo(value: T): R
    fun restoreFrom(value: R): T
}