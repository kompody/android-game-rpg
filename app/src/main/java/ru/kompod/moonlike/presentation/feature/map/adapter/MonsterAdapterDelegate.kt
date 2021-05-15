// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.map.adapter

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.jakewharton.rxbinding3.view.clicks
import kotlinx.android.synthetic.main.item_map_object_monster.*
import kotlinx.android.synthetic.main.item_map_object_travel.*
import kotlinx.android.synthetic.main.item_map_object_travel.travelLabelTextView
import me.dmdev.rxpm.Action
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.map.model.MonsterItem
import ru.kompod.moonlike.utils.picasso.PicassoUtil
import ru.kompod.moonlike.utils.picasso.PixelTargetAdapter
import javax.inject.Provider

class MonsterAdapterDelegate(
    private val listener: MonsterItemListener,
    private val picasso: PicassoUtil
) : Provider<AdapterDelegate<List<IListItem>>> {
    override fun get(): AdapterDelegate<List<IListItem>> = createAdapterDelegate(listener)

    private fun createAdapterDelegate(listener: MonsterItemListener) =
        adapterDelegateLayoutContainer<MonsterItem, IListItem>(R.layout.item_map_object_monster) {

            itemView
                .clicks()
                .map { item }
                .subscribe(listener.onMonsterClickObserver.consumer)

            bind {
                with(item.monster) {
                    picasso.load(portrait)
                        .into(PixelTargetAdapter(iconImageView))
                    travelLabelTextView.text = label
                }
            }
        }

    interface MonsterItemListener {
        val onMonsterClickObserver: Action<MonsterItem>
    }
}