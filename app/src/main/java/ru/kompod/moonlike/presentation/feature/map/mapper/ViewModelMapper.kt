// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.map.mapper

import ru.kompod.moonlike.R
import ru.kompod.moonlike.domain.entity.base.MapObject
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.map.model.TitleListItem
import ru.kompod.moonlike.presentation.feature.map.model.TravelItem
import ru.kompod.moonlike.utils.ResourceDelegate
import javax.inject.Inject

class ViewModelMapper @Inject constructor(
    private val resources: ResourceDelegate
) {
    fun mapEntityToViewModel(model: MapObject): List<IListItem> = listOf(
        TitleListItem(resources.getString(R.string.screen_map_travels_title)),
        *model.travels.map { TravelItem(it) }.toTypedArray(),
        TitleListItem(resources.getString(R.string.screen_map_objects_title)),
        TitleListItem(resources.getString(R.string.screen_map_npc_title)),
        TitleListItem(resources.getString(R.string.screen_map_monsters_title))
    )
}