// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.network.model

data class FractionApiModel(
    val id: Short,
    val label: String,
    val description: String
)

data class FractionInfoApiModel(
    val id: Short,
    val label: String,
    val description: String,
    val characters: List<CharacterApiModel>
)

data class CharacterApiModel(
    val id: Short,
    val label: String,
    val description: String,
    val gender: GenderApiModel,
    val portraits: List<String>,
    val roles: List<RoleApiModel>
)

data class GenderApiModel(
    val id: Short,
    val label: String
)