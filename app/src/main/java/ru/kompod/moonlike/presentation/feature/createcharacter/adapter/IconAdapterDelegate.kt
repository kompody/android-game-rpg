// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.createcharacter.adapter

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.jakewharton.rxbinding3.view.clicks
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_create_character_icon.*
import kotlinx.android.synthetic.main.item_create_character_icon.menuLeftImageView
import kotlinx.android.synthetic.main.item_create_character_icon.menuRightImageView
import me.dmdev.rxpm.Action
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.createcharacter.model.PortraitItem
import ru.kompod.moonlike.utils.extensions.picasso.loadFromAsset
import ru.kompod.moonlike.utils.picasso.PixelTargetAdapter
import ru.kompod.moonlike.utils.tools.AssetProvider
import javax.inject.Provider

class IconAdapterDelegate(
    private val listener: IconItemListener,
    private val assetProvider: AssetProvider,
    private val picasso: Picasso
) : Provider<AdapterDelegate<List<IListItem>>> {
    override fun get(): AdapterDelegate<List<IListItem>> =
        createAdapterDelegate(listener, picasso)

    private fun createAdapterDelegate(
        listener: IconItemListener,
        picasso: Picasso
    ) =
        adapterDelegateLayoutContainer<PortraitItem, IListItem>(R.layout.item_create_character_icon) {

            bind {
                picasso.loadFromAsset(assetProvider, item.portraits[item.selectedIndex].path)
                    .into(PixelTargetAdapter(iconImageView))

                menuLeftImageView.clicks().map { item.selectedIndex - 1 }
                    .subscribe(listener.onChangeIconClickObserver.consumer)
                menuRightImageView.clicks().map { item.selectedIndex + 1 }
                    .subscribe(listener.onChangeIconClickObserver.consumer)
            }
        }

    interface IconItemListener {
        val onChangeIconClickObserver: Action<Int>
    }
}