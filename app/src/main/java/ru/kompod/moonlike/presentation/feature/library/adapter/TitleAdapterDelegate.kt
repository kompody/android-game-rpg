// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.library.adapter

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.jakewharton.rxbinding3.view.clicks
import kotlinx.android.synthetic.main.item_library_menu_titile.*
import me.dmdev.rxpm.Action
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.library.model.TitleListItem
import javax.inject.Provider

class TitleAdapterDelegate(
    private val listener: LibraryMenuListener
) : Provider<AdapterDelegate<List<IListItem>>> {
    override fun get(): AdapterDelegate<List<IListItem>> =
        createAdapterDelegate(listener)

    private fun createAdapterDelegate(
        listener: LibraryMenuListener
    ) =
        adapterDelegateLayoutContainer<TitleListItem, IListItem>(R.layout.item_library_menu_titile) {

            itemView
                .clicks()
                .map { item }
                .subscribe(listener.onLibraryMenuItemClickObserver.consumer)

            bind {
                iconImageView
                titleTextView.text = item.title
            }
        }

    interface LibraryMenuListener {
        val onLibraryMenuItemClickObserver: Action<TitleListItem>
    }
}