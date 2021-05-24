// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.library.model

import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem

data class TitleListItem(val type: TypeItem, val title: String) : IListItem

enum class TypeItem {
    CHARACTERS,
    ITEMS,
    LOCATIONS,
    MONSTERS
}