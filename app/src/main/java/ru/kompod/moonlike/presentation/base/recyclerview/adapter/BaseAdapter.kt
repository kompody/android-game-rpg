// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.base.recyclerview.adapter

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem

open class BaseAdapter(vararg delegates: AdapterDelegate<List<IListItem>>) : ListDelegationAdapter<List<IListItem>>(), IItemsHolder<IListItem> {
    init {
        items = mutableListOf()
        delegates.forEach { delegatesManager.addDelegate(it) }
    }

    override fun setItems(items: List<IListItem>?) {
        super.setItems(items)
        notifyDataSetChanged()
    }

    fun setItems(items: List<IListItem>?, notifyAdapter: Boolean) {
        super.setItems(items)
        if (notifyAdapter) {
            notifyDataSetChanged()
        }
    }
}