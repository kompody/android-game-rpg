// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.network.model

import com.google.gson.annotations.SerializedName
import ru.kompod.moonlike.utils.NO_ID
import ru.kompod.moonlike.utils.extensions.kotlin.empty

data class MonsterApiModel(
    val id: Short = NO_ID,
    val label: String = String.empty,
    val type: String = String.empty,
    val gender: GenderApiModel = GenderApiModel(),
    val portrait: String = String.empty,
    val role: RoleApiModel = RoleApiModel(),
    @SerializedName("spawn_rate") val spawnRate: Float = 0f,
    val delay: Int = 0
)