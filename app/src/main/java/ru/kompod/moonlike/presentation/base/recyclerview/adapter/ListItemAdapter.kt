// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.base.recyclerview.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import javax.inject.Inject

class ListItemAdapter @Inject constructor(
    diffCallback: DiffUtil.ItemCallback<IListItem>,
    delegates: Set<AdapterDelegate<List<IListItem>>>
) : AsyncListDifferDelegationAdapter<IListItem>(diffCallback), IItemsHolder<IListItem> {
    init {
        items = mutableListOf()
        delegates.forEach { delegatesManager.addDelegate(it) }
    }

    fun addDelegate(delegate: AdapterDelegate<List<IListItem>>): ListItemAdapter {
        delegatesManager.addDelegate(delegate)
        return this
    }
}