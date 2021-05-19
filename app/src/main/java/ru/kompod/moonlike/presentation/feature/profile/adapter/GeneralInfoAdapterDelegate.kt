// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.profile.adapter

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.item_profile_general_info.*
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.recyclerview.adapter.BaseAdapter
import ru.kompod.moonlike.presentation.base.recyclerview.decorator.GridListDecorator
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.profile.adapter.StateAdapterDelegate
import ru.kompod.moonlike.presentation.feature.profile.model.StateItem
import ru.kompod.moonlike.presentation.feature.profile.model.GeneralInfoItem
import ru.kompod.moonlike.utils.extensions.kotlin.dp
import ru.kompod.moonlike.utils.picasso.PicassoUtil
import ru.kompod.moonlike.utils.picasso.PixelTargetAdapter
import javax.inject.Provider

class GeneralInfoAdapterDelegate(
    private val picassoUtil: PicassoUtil
) : Provider<AdapterDelegate<List<IListItem>>> {
    override fun get(): AdapterDelegate<List<IListItem>> = createAdapterDelegate()

    private fun createAdapterDelegate() =
        adapterDelegateLayoutContainer<GeneralInfoItem, IListItem>(R.layout.item_profile_general_info) {

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
                with(item.character) {
                    picassoUtil.load(portrait)
                        .into(PixelTargetAdapter(iconImageView))

                    labelTextView.text = "${fraction.label} - ${role.label}"

                    listOf(
                        StateItem("level", level.toString()),
                        StateItem("exp", exp.toString()),
                        StateItem("hp", hp.toString()),
                        StateItem("sp", sp.toString()),
                        StateItem("fAtk", baseFAtk.toString()),
                        StateItem("fDef", baseFDef.toString()),
                        StateItem("mAtk", baseMAtk.toString()),
                        StateItem("mDef", baseMDef.toString())
                    ).apply(innerAdapter::setItems)
                }
            }
        }
}