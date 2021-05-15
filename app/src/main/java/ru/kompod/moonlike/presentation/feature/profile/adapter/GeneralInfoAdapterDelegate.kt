// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.profile.adapter

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.item_profile_general_info.*
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.profile.model.GeneralInfoItem
import ru.kompod.moonlike.utils.picasso.PicassoUtil
import ru.kompod.moonlike.utils.picasso.PixelTargetAdapter
import javax.inject.Provider

class GeneralInfoAdapterDelegate(
    private val picasso: PicassoUtil
) : Provider<AdapterDelegate<List<IListItem>>> {
    override fun get(): AdapterDelegate<List<IListItem>> = createAdapterDelegate()

    private fun createAdapterDelegate() =
        adapterDelegateLayoutContainer<GeneralInfoItem, IListItem>(R.layout.item_profile_general_info) {
            bind {
                with(item.character) {
                    picasso.load(portrait)
                        .into(PixelTargetAdapter(iconImageView))

                    labelTextView.text = "${fraction.label} - ${role.label}"
                }
            }
        }
}