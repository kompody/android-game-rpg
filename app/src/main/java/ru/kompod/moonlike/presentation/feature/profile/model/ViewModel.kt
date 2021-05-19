// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.profile.model

import ru.kompod.moonlike.domain.entity.base.CharacterObject
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem

data class TitleListItem(val title: String): IListItem
data class GeneralInfoItem(val character: CharacterObject): IListItem
data class StateItem(val label: String, val value: String): IListItem