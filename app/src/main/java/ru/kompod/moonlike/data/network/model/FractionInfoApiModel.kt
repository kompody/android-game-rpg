// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.network.model

import ru.kompod.moonlike.domain.entity.base.RoleInfoObject
import ru.kompod.moonlike.utils.NO_ID
import ru.kompod.moonlike.utils.extensions.kotlin.empty

data class FractionApiModel(
    val id: Short = NO_ID,
    val label: String = String.empty,
    val description: String = String.empty
)

data class FractionInfoApiModel(
    val id: Short = NO_ID,
    val label: String = String.empty,
    val description: String = String.empty,
    val characters: List<CharacterApiModel> = listOf()
)

data class CharacterApiModel(
    val id: Short = NO_ID,
    val label: String = String.empty,
    val description: String = String.empty,
    val gender: GenderApiModel = GenderApiModel(),
    val portraits: List<String> = listOf(),
    val roles: List<Short> = listOf()
) {
    var roleObjects: List<RoleInfoObject> = listOf()
}

data class GenderApiModel(
    val id: Short = NO_ID,
    val label: String = String.empty
)