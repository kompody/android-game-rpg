// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.createcharacter.model

import ru.kompod.moonlike.domain.entity.base.*
import ru.kompod.moonlike.presentation.base.recyclerview.model.IListItem

class CacheModel(
    val raceItem: RaceItem,
    val genderItem: GenderItem,
    val portraitItem: PortraitItem,
    val roleItem: RoleItem
)

class RaceItem(val races: List<RaceInfo>, var selectedIndex: Int) : IListItem
class GenderItem(var genders: List<Gender>, var selectedIndex: Int) : IListItem
class PortraitItem(var portraits: List<Portrait>, var selectedIndex: Int) : IListItem
class RoleItem(var roles: List<Role>, var selectedIndex: Int) : IListItem
class CharacterAboutItem(var race: Race, var role: Role) : IListItem