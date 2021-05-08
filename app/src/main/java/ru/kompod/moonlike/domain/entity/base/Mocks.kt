// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.entity.base

import ru.kompod.moonlike.utils.extensions.kotlin.empty

val emptyRaceObject = RaceObject(
    id = -1,
    label = String.empty,
    description = String.empty
)

val emptyGenderObject = GenderObject(
    id = -1,
    label = String.empty
)

val emptyPortraitObject = PortraitObject(
    id = -1,
    path = String.empty
)

val emptyRoleObject = RoleObject(
    id = -1,
    label = String.empty,
    description = String.empty,
    states = States.fromRaw(0, 0, 0)
)

val emptyCharacterObject = CharacterObject(
    id = -1,
    label = String.empty,
    race = emptyRaceObject,
    gender = emptyGenderObject,
    portrait = emptyPortraitObject,
    role = emptyRoleObject
)