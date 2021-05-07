// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.characterslist.adapter

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.jakewharton.rxbinding3.view.clicks
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_character_list_character.*
import kotlinx.android.synthetic.main.item_character_list_character.valueAgiTextView
import kotlinx.android.synthetic.main.item_character_list_character.valueIntTextView
import kotlinx.android.synthetic.main.item_character_list_character.valueStrTextView
import me.dmdev.rxpm.Action
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.characterslist.model.CharacterItem
import ru.kompod.moonlike.utils.picasso.PixelTargetAdapter
import ru.kompod.moonlike.utils.tools.AssetProvider
import javax.inject.Provider

class CharacterAdapterDelegate(
    private val listener: CharacterItemListener,
    private val assetProvider: AssetProvider,
    private val picasso: Picasso
) : Provider<AdapterDelegate<List<IListItem>>> {
    override fun get(): AdapterDelegate<List<IListItem>> = createAdapterDelegate(listener, picasso)

    private fun createAdapterDelegate(
        listener: CharacterItemListener,
        picasso: Picasso
    ) =
        adapterDelegateLayoutContainer<CharacterItem, IListItem>(R.layout.item_character_list_character) {

            itemView
                .clicks()
                .map { item }
                .subscribe(listener.onCharacterItemClickObserver.consumer)

            bind {
                with(item.character) {
                    val file = assetProvider.fileFromAsset(portrait.path)
                    picasso.load(file)
                        .into(PixelTargetAdapter(iconImageView))

                    labelTextView.text = "${race.label} - ${role.label}"

                    valueStrTextView.text = getString(R.string.asset_stats_str, role.states.str.value)
                    valueAgiTextView.text = getString(R.string.asset_stats_agi, role.states.agi.value)
                    valueIntTextView.text = getString(R.string.asset_stats_int, role.states.int.value)
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