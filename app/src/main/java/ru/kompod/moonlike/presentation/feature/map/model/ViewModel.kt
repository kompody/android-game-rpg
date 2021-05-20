// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.map.model

import ru.kompod.moonlike.domain.entity.base.OnMapObject
import ru.kompod.moonlike.domain.entity.base.TravelObject
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem

enum class TitleType {
    LOCATION,
    OBJECTS,
    NPC,
    MONSTERS
}

data class StateItem(val label: String, val value: String): IListItem
data class TitleListItem(val type: TitleType, val title: String, val items: List<IListItem>): IListItem
data class TravelItem(val travel: TravelObject): IListItem
data class MonsterItem(val onMapObj: OnMapObject): IListItem