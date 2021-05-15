// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.network.model

import com.google.gson.annotations.SerializedName

data class MonsterApiModel(
    val id: Short,
    val label: String,
    val type: String,
    val gender: GenderApiModel,
    val portrait: String,
    val role: RoleApiModel,
    @SerializedName("spawn_rate") val spawnRate: Float,
    val delay: Int
)