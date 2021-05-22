// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.createcharacter.adapter

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.item_create_character_about.*
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.recyclerview.adapter.BaseAdapter
import ru.kompod.moonlike.presentation.base.recyclerview.decorator.GridListDecorator
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.createcharacter.model.AboutItem
import ru.kompod.moonlike.presentation.feature.createcharacter.model.StateItem
import ru.kompod.moonlike.utils.extensions.kotlin.dp
import javax.inject.Provider

class AboutAdapterDelegate(
) : Provider<AdapterDelegate<List<IListItem>>> {
    override fun get(): AdapterDelegate<List<IListItem>> = createAdapterDelegate()

    private fun createAdapterDelegate() =
        adapterDelegateLayoutContainer<AboutItem, IListItem>(R.layout.item_create_character_about) {

            val innerAdapter = BaseAdapter(
                StateAdapterDelegate().get()
            )

            with(statesRecyclerView) {
                adapter = innerAdapter
                addItemDecoration(
                    GridListDecorator(
                        applyFor = setOf(
                            StateItem::class
                        ),
                        columnCount = 2,
                        margin = 6.dp,
                        boundaryMargin = 8.dp
                    )
                )
            }

            bind {
                with(item.role.levelStates[0].states) {
                    listOf(
                        StateItem("hp", hp),
                        StateItem("sp", sp),
                        StateItem("fAtk", fAtk),
                        StateItem("fDef", fDef),
                        StateItem("mAtk", mAtk),
                        StateItem("mDef", mDef)
                    ).apply(innerAdapter::setItems)
                }
            }
        }
}