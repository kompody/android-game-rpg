// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.createcharacter.adapter

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.jakewharton.rxbinding3.view.clicks
import kotlinx.android.synthetic.main.item_create_character_fraction.*
import me.dmdev.rxpm.Action
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.createcharacter.model.FractionItem
import javax.inject.Provider

class FractionAdapterDelegate(
    private val listener: FractionItemListener
) : Provider<AdapterDelegate<List<IListItem>>> {
    override fun get(): AdapterDelegate<List<IListItem>> = createAdapterDelegate()

    private fun createAdapterDelegate() =
        adapterDelegateLayoutContainer<FractionItem, IListItem>(R.layout.item_create_character_fraction) {
            bind {
                labelTextView.text = item.items[item.selectedIndex].label

                menuLeftImageView.clicks()
                    .map { item.selectedIndex - 1 }
                    .subscribe(listener.onChangeFractionClickObserver.consumer)
                menuRightImageView.clicks()
                    .map { item.selectedIndex + 1 }
                    .subscribe(listener.onChangeFractionClickObserver.consumer)
            }
        }

    interface FractionItemListener {
        val onChangeFractionClickObserver: Action<Int>
    }
}