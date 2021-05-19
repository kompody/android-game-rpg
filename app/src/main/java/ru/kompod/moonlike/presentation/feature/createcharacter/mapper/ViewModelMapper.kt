// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.feature.createcharacter.mapper

import ru.kompod.moonlike.domain.entity.base.NewCharacterObject
import ru.kompod.moonlike.presentation.feature.createcharacter.model.ViewModel

fun ViewModel.toEntity(): NewCharacterObject =
    NewCharacterObject(
        0,
        characterItem.items[characterItem.selectedIndex].label,
        characterItem.items[characterItem.selectedIndex].description,
        fractionItem.items[fractionItem.selectedIndex].fraction,
        characterItem.items[characterItem.selectedIndex].gender,
        portraitItem.items[portraitItem.selectedIndex],
        roleItem.items[roleItem.selectedIndex]
    )