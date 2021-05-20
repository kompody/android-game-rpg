// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.network.model

import com.google.gson.annotations.SerializedName
import ru.kompod.moonlike.utils.NO_ID
import ru.kompod.moonlike.utils.extensions.kotlin.empty

data class MapApiModel(
    val id: Int = NO_ID,
    val label: String = String.empty,
    val path: String = String.empty,
    val biome: String = String.empty,
    @SerializedName("monster_limit") val monsterLimit: Int = 0,
    @SerializedName("bosses_limit") val bossesLimit: Int = 0,
    val delay: Int = 0,
    val travels: List<TravelApiModel> = listOf(),
    val monsters: List<Int> = listOf(),
    val objects: List<Int> = listOf(),
    val actors: List<Int> = listOf()
)

data class TravelApiModel(
    val id: Int = NO_ID,
    val label: String = String.empty,
    val to: Int = NO_ID
)