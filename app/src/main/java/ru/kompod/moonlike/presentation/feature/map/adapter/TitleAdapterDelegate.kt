// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.map.adapter

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.item_map_object_title.*
import me.dmdev.rxpm.Action
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.recyclerview.adapter.BaseAdapter
import ru.kompod.moonlike.presentation.base.recyclerview.decorator.GridListDecorator
import ru.kompod.moonlike.presentation.base.recyclerview.decorator.VerticalListMarginDecorator
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.map.model.MonsterItem
import ru.kompod.moonlike.presentation.feature.map.model.TitleListItem
import ru.kompod.moonlike.presentation.feature.map.model.TravelItem
import ru.kompod.moonlike.utils.extensions.kotlin.dp
import ru.kompod.moonlike.utils.extensions.toothpick.getInstance
import ru.kompod.moonlike.utils.picasso.PicassoUtil
import javax.inject.Provider

class TitleAdapterDelegate(
    private val travelItemListener: TravelAdapterDelegate.TravelItemListener,
    private val monsterItemListener: MonsterAdapterDelegate.MonsterItemListener,
    private val picassoUtil: PicassoUtil
) : Provider<AdapterDelegate<List<IListItem>>> {
    override fun get(): AdapterDelegate<List<IListItem>> = createAdapterDelegate()

    private fun createAdapterDelegate() = adapterDelegateLayoutContainer<TitleListItem, IListItem>(
        R.layout.item_map_object_title) {

        val innerAdapter = BaseAdapter(
            TravelAdapterDelegate(travelItemListener).get(),
            MonsterAdapterDelegate(monsterItemListener, picassoUtil).get()
        )

        with(objectRecyclerView) {
            adapter = innerAdapter
            addItemDecoration(
                GridListDecorator(
                    applyFor = setOf(
                        TravelItem::class,
                        MonsterItem::class
                    ),
                    columnCount = 2,
                    margin = 6.dp,
                    boundaryMargin = 2.dp
                )
            )
        }

        bind {
            titleTextView.text = item.title
            innerAdapter.items = item.items
        }
    }
}