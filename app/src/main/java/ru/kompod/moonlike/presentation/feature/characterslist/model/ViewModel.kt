// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.characterslist.model

import ru.kompod.moonlike.domain.entity.base.Character
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem

class CharacterItem(val character: Character): IListItem
object CreateCharacterItem : IListItem