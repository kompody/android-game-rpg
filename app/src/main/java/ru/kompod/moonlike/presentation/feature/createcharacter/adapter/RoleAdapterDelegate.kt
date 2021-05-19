// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.createcharacter.adapter

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.jakewharton.rxbinding3.view.clicks
import kotlinx.android.synthetic.main.item_create_character_role.*
import me.dmdev.rxpm.Action
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.createcharacter.model.RoleItem
import javax.inject.Provider

class RoleAdapterDelegate(
    private val listener: RoleItemListener
) : Provider<AdapterDelegate<List<IListItem>>> {
    override fun get(): AdapterDelegate<List<IListItem>> = createAdapterDelegate()

    private fun createAdapterDelegate() =
        adapterDelegateLayoutContainer<RoleItem, IListItem>(R.layout.item_create_character_role) {
            bind {
                with(item.items[item.selectedIndex]) {
                    labelTextView.text = label
                    descriptionTextView.text = description
                }

                menuLeftImageView.clicks()
                    .map { item.selectedIndex - 1 }
                    .subscribe(listener.onChangeRoleClickObserver.consumer)
                menuRightImageView.clicks()
                    .map { item.selectedIndex + 1 }
                    .subscribe(listener.onChangeRoleClickObserver.consumer)
            }
        }

    interface RoleItemListener {
        val onChangeRoleClickObserver: Action<Int>
    }
}