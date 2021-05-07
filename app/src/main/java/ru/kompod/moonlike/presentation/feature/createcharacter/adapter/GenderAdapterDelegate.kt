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
import ru.kompod.moonlike.presentation.feature.createcharacter.model.GenderItem
import javax.inject.Provider

class GenderAdapterDelegate(
    private val listener: GenderItemListener
) : Provider<AdapterDelegate<List<IListItem>>> {
    override fun get(): AdapterDelegate<List<IListItem>> =
        createAdapterDelegate(listener)

    private fun createAdapterDelegate(
        listener: GenderItemListener
    ) =
        adapterDelegateLayoutContainer<GenderItem, IListItem>(R.layout.item_create_character_gender) {

            bind {
                labelTextView.text = item.genders[item.selectedIndex].label

                menuLeftImageView.clicks().map { item.selectedIndex - 1 }
                    .subscribe(listener.onChangeGenderClickObserver.consumer)
                menuRightImageView.clicks().map { item.selectedIndex + 1 }
                    .subscribe(listener.onChangeGenderClickObserver.consumer)
            }
        }

    interface GenderItemListener {
        val onChangeGenderClickObserver: Action<Int>
    }
}