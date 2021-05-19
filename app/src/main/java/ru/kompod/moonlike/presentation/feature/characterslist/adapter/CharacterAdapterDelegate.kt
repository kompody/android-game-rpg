// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.characterslist.adapter

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.jakewharton.rxbinding3.view.clicks
import kotlinx.android.synthetic.main.item_character_list_character.*
import me.dmdev.rxpm.Action
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.recyclerview.adapter.BaseAdapter
import ru.kompod.moonlike.presentation.base.recyclerview.decorator.GridListDecorator
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.characterslist.model.CharacterItem
import ru.kompod.moonlike.presentation.feature.characterslist.model.StateItem
import ru.kompod.moonlike.utils.extensions.android.setVisibleOrGone
import ru.kompod.moonlike.utils.extensions.kotlin.dp
import ru.kompod.moonlike.utils.picasso.PicassoUtil
import ru.kompod.moonlike.utils.picasso.PixelTargetAdapter
import javax.inject.Provider

class CharacterAdapterDelegate(
    private val listener: CharacterItemListener,
    private val picassoUtil: PicassoUtil
) : Provider<AdapterDelegate<List<IListItem>>> {
    override fun get(): AdapterDelegate<List<IListItem>> =
        createAdapterDelegate(listener, picassoUtil)

    private fun createAdapterDelegate(
        listener: CharacterItemListener,
        picassoUtil: PicassoUtil
    ) =
        adapterDelegateLayoutContainer<CharacterItem, IListItem>(R.layout.item_character_list_character) {

            itemView
                .clicks()
                .map { item }
                .subscribe(listener.onCharacterItemClickObserver.consumer)

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

                    selectedTextView.setVisibleOrGone(item.isSelected)

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

                removeImageView.clicks().map { item }
                    .subscribe(listener.onRemoveItemClickObserver.consumer)
            }
        }

    interface CharacterItemListener {
        val onCharacterItemClickObserver: Action<CharacterItem>
        val onRemoveItemClickObserver: Action<CharacterItem>
    }
}