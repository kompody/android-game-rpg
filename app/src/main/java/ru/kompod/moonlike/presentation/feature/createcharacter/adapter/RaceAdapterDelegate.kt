// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.createcharacter.adapter

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.jakewharton.rxbinding3.view.clicks
import kotlinx.android.synthetic.main.item_create_character_race.*
import me.dmdev.rxpm.Action
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.createcharacter.model.RaceItem
import javax.inject.Provider

class RaceAdapterDelegate(
    private val listener: RaceItemListener
) : Provider<AdapterDelegate<List<IListItem>>> {
    override fun get(): AdapterDelegate<List<IListItem>> =
        createAdapterDelegate(listener)

    private fun createAdapterDelegate(
        listener: RaceItemListener
    ) = adapterDelegateLayoutContainer<RaceItem, IListItem>(R.layout.item_create_character_race) {

        bind {
            labelTextView.text = item.races[item.selectedIndex].race.label

            menuLeftImageView.clicks().map { item.selectedIndex - 1 }
                .subscribe(listener.onChangeRaceClickObserver.consumer)
            menuRightImageView.clicks().map { item.selectedIndex + 1 }
                .subscribe(listener.onChangeRaceClickObserver.consumer)
        }
    }

    interface RaceItemListener {
        val onChangeRaceClickObserver: Action<Int>
    }
}