// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.map.adapter

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.jakewharton.rxbinding3.view.clicks
import kotlinx.android.synthetic.main.item_map_object_travel.*
import me.dmdev.rxpm.Action
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.map.model.TravelItem
import javax.inject.Provider

class TravelAdapterDelegate(
    private val listener: TravelItemListener
) : Provider<AdapterDelegate<List<IListItem>>> {
    override fun get(): AdapterDelegate<List<IListItem>> = createAdapterDelegate(listener)

    private fun createAdapterDelegate(listener: TravelItemListener) =
        adapterDelegateLayoutContainer<TravelItem, IListItem>(R.layout.item_map_object_travel) {

            itemView
                .clicks()
                .map { item }
                .subscribe(listener.onTravelClickObserver.consumer)

            bind {
                travelLabelTextView.text = item.travel.label
            }
        }

    interface TravelItemListener {
        val onTravelClickObserver: Action<TravelItem>
    }
}