// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.base.recyclerview.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import toothpick.InjectConstructor

@InjectConstructor
open class DefaultDiffCallback : DiffUtil.ItemCallback<IListItem>() {
    override fun areItemsTheSame(oldItem: IListItem, newItem: IListItem): Boolean {
        return oldItem::class == newItem::class
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: IListItem, newItem: IListItem): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: IListItem, newItem: IListItem): Any? {
        return newItem
    }
}