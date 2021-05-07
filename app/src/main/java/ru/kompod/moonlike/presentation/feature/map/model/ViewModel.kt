// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.map.model

import ru.kompod.moonlike.domain.entity.base.TravelObject
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem

class TitleListItem(val title: String): IListItem
class TravelItem(val travel: TravelObject): IListItem