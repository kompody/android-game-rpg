// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.characterslist.adapter

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.jakewharton.rxbinding3.view.clicks
import me.dmdev.rxpm.Action
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.characterslist.model.CreateCharacterItem
import javax.inject.Provider

class CreateCharacterAdapterDelegate(
    private val listener: CreateCharacterItemListener
) : Provider<AdapterDelegate<List<IListItem>>> {
    override fun get(): AdapterDelegate<List<IListItem>> = createAdapterDelegate(listener)

    private fun createAdapterDelegate(
        listener: CreateCharacterItemListener
    ) =
        adapterDelegateLayoutContainer<CreateCharacterItem, IListItem>(R.layout.item_character_list_create) {

            itemView
                .clicks()
                .map { item }
                .subscribe(listener.onCreateCharacterItemClickObserver.consumer)

            bind {

            }
        }

    interface CreateCharacterItemListener {
        val onCreateCharacterItemClickObserver: Action<CreateCharacterItem>
    }
}