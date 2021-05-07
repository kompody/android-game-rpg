// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.base.recyclerview.adapter

interface IItemsHolder<T: Any> {
    fun getItems(): List<T>
}