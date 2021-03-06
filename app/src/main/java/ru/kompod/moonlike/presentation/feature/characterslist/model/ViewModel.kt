// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.characterslist.model

import ru.kompod.moonlike.domain.entity.base.CharacterObject
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem

data class CharacterItem(val character: CharacterObject, val isSelected: Boolean): IListItem
data class StateItem(val label: String, val value: String): IListItem
object CreateCharacterItem : IListItem