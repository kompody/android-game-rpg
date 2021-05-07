// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.createcharacter.mapper

import ru.kompod.moonlike.domain.entity.base.CharacterObject
import ru.kompod.moonlike.presentation.feature.createcharacter.model.CacheModel

fun CacheModel.toEntity(): CharacterObject =
    CharacterObject(
        0,
        "",
        raceItem.races[raceItem.selectedIndex].race,
        genderItem.genders[genderItem.selectedIndex],
        portraitItem.portraits[portraitItem.selectedIndex],
        roleItem.roles[roleItem.selectedIndex]
    )