// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.createcharacter.adapter

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.item_create_character_about.*
import ru.kompod.moonlike.R
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.createcharacter.model.CharacterAboutItem
import javax.inject.Provider

class CharacterAboutAdapterDelegate : Provider<AdapterDelegate<List<IListItem>>> {
    override fun get(): AdapterDelegate<List<IListItem>> =
        createAdapterDelegate()

    private fun createAdapterDelegate() = adapterDelegateLayoutContainer<CharacterAboutItem, IListItem>(R.layout.item_create_character_about) {

        bind {
            raceDescriptionTextView.text = item.race.description
            roleDescriptionTextView.text = item.role.description
            valueStrTextView.text = getString(R.string.asset_stats_str, item.role.states.str.value)
            valueAgiTextView.text = getString(R.string.asset_stats_agi, item.role.states.agi.value)
            valueIntTextView.text = getString(R.string.asset_stats_int, item.role.states.int.value)
        }
    }
}