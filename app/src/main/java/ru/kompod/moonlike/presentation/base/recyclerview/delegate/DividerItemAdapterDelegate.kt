// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.base.recyclerview.delegate

import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.item_divider.*
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.recyclerview.model.DividerItemViewModel
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem

fun dividerItemAdapterDelegate() =
    adapterDelegateLayoutContainer<DividerItemViewModel, IListItem>(R.layout.item_divider) {
        bind {
            itemView.setPadding(item.left, item.top, item.right, item.bottom)
            val params: ViewGroup.LayoutParams = divider.layoutParams
            params.height = item.height
            divider.layoutParams = params
            divider.setBackgroundResource(item.colorResId)
        }
    }