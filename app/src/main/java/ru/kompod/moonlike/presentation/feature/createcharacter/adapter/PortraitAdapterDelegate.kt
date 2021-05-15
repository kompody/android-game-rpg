// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.createcharacter.adapter

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.jakewharton.rxbinding3.view.clicks
import kotlinx.android.synthetic.main.item_create_character_icon.*
import me.dmdev.rxpm.Action
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.createcharacter.model.PortraitItem
import ru.kompod.moonlike.utils.picasso.PicassoUtil
import javax.inject.Provider

class PortraitAdapterDelegate(
    private val listener: PortraitItemListener,
    private val picasso: PicassoUtil
) : Provider<AdapterDelegate<List<IListItem>>> {
    override fun get(): AdapterDelegate<List<IListItem>> = createAdapterDelegate()

    private fun createAdapterDelegate() =
        adapterDelegateLayoutContainer<PortraitItem, IListItem>(R.layout.item_create_character_icon) {
            bind {
                picasso.load(item.items[item.selectedIndex])
                    .resize(128, 128)
                    .into(iconImageView)

                menuLeftImageView.clicks()
                    .map { item.selectedIndex - 1 }
                    .subscribe(listener.onChangePortraitClickObserver.consumer)
                menuRightImageView.clicks()
                    .map { item.selectedIndex + 1 }
                    .subscribe(listener.onChangePortraitClickObserver.consumer)
            }
        }

    interface PortraitItemListener {
        val onChangePortraitClickObserver: Action<Int>
    }
}