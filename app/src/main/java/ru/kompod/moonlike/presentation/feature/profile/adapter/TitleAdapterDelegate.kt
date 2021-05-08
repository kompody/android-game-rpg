// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.profile.adapter

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.item_profile_title.*
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.profile.model.TitleListItem
import javax.inject.Provider

class TitleAdapterDelegate : Provider<AdapterDelegate<List<IListItem>>> {
    override fun get(): AdapterDelegate<List<IListItem>> = createAdapterDelegate()

    private fun createAdapterDelegate() =
        adapterDelegateLayoutContainer<TitleListItem, IListItem>(R.layout.item_profile_title) {
            bind {
                titleTextView.text = item.title
            }
        }
}