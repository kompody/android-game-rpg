// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.network.model

import com.google.gson.annotations.SerializedName

data class MapApiModel(
    val id: Short,
    val label: String,
    val path: String,
    val biome: String,
    @SerializedName("monster_limit") val monsterLimit: Int,
    @SerializedName("bosses_limit") val bossesLimit: Int,
    val delay: Int,
    val travels: List<TravelApiModel>,
    val monsters: List<Short>,
    val objects: List<Short>,
    val actors: List<Short>
)

data class TravelApiModel(
    val id: Short,
    val label: String,
    val to: Short
)