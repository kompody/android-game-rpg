// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.map.mapper

import ru.kompod.moonlike.R
import ru.kompod.moonlike.domain.entity.base.MapObject
import ru.kompod.moonlike.domain.entity.base.MonsterObject
import ru.kompod.moonlike.domain.entity.base.OnMapObject
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem
import ru.kompod.moonlike.presentation.feature.map.model.MonsterItem
import ru.kompod.moonlike.presentation.feature.map.model.TitleListItem
import ru.kompod.moonlike.presentation.feature.map.model.TitleType
import ru.kompod.moonlike.presentation.feature.map.model.TravelItem
import ru.kompod.moonlike.utils.ResourceDelegate
import javax.inject.Inject

class MapViewModelMapper @Inject constructor(
    private val resources: ResourceDelegate
) {
    fun mapEntityToViewModel(model: MapObject): List<IListItem> =
        mapEntityToViewModel(model, listOf())

    fun mapEntityToViewModel(model: MapObject, monsters: List<OnMapObject>): List<IListItem> = listOf(
        TitleListItem(TitleType.LOCATION, resources.getString(R.string.screen_map_travels_title), model.travels.map { TravelItem(it) }),
        TitleListItem(TitleType.MONSTERS, resources.getString(R.string.screen_map_monsters_title), monsters.map { MonsterItem(it) }),
        TitleListItem(TitleType.OBJECTS, resources.getString(R.string.screen_map_objects_title), listOf()),
        TitleListItem(TitleType.NPC, resources.getString(R.string.screen_map_npc_title), listOf())
    )
}