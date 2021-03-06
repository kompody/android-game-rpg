// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.network.model

import com.google.gson.annotations.SerializedName
import ru.kompod.moonlike.utils.NO_ID
import ru.kompod.moonlike.utils.extensions.kotlin.empty

data class MonsterApiModel(
    val id: Int = NO_ID,
    val label: String = String.empty,
    val type: String = String.empty,
    val gender: GenderApiModel = GenderApiModel(),
    val portrait: String = String.empty,
    val level: Int = 1,
    val exp: Int = 0,
    val hp: Int = 0,
    val sp: Int = 0,
    @SerializedName("f-atk") val fAtk: Int = 0,
    @SerializedName("f-def") val fDef: Int = 0,
    @SerializedName("m-atk") val mAtk: Int = 0,
    @SerializedName("m-def") val mDef: Int = 0,
    @SerializedName("spawn_rate") val spawnRate: Float = 0f,
    val delay: Int = 0
)