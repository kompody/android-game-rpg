// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.createcharacter.mapper

import ru.kompod.moonlike.domain.entity.base.CharacterObject
import ru.kompod.moonlike.presentation.feature.createcharacter.model.ViewModel
import ru.kompod.moonlike.utils.extensions.kotlin.empty

fun ViewModel.toEntity(): CharacterObject =
    CharacterObject(
        0,
        "", //todo приделать выбор имен
        raceItem.races[raceItem.selectedIndex].race,
        genderItem.genders[genderItem.selectedIndex],
        portraitItem.portraits[portraitItem.selectedIndex],
        roleItem.roles[roleItem.selectedIndex]
    )