// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.createcharacter.model

import ru.kompod.moonlike.domain.entity.base.CharacterInfoObject
import ru.kompod.moonlike.domain.entity.base.FractionInfoObject
import ru.kompod.moonlike.domain.entity.base.RoleObject
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem

class ViewModel(
    val fractionItem: FractionItem,
    val characterItem: CharacterItem,
    val portraitItem: PortraitItem,
    val roleItem: RoleItem,
    val aboutItem: AboutItem
)

data class FractionItem(val items: List<FractionInfoObject>, var selectedIndex: Int): IListItem
data class CharacterItem(var items: List<CharacterInfoObject>, var selectedIndex: Int): IListItem
data class PortraitItem(var items: List<String>, var selectedIndex: Int): IListItem
data class RoleItem(var items: List<RoleObject>, var selectedIndex: Int): IListItem
data class AboutItem(var fraction: FractionInfoObject, var role: RoleObject): IListItem