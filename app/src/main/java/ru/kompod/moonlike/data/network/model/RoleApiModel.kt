// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.network.model

import com.google.gson.annotations.SerializedName
import ru.kompod.moonlike.utils.NO_ID
import ru.kompod.moonlike.utils.extensions.kotlin.empty

data class RoleApiModel(
    val id: Int = NO_ID,
    val label: String = String.empty,
    val description: String = String.empty,
    val states: List<LevelStatesApiModel>
)

data class LevelStatesApiModel(
    val level: Int = 1,
    val states: States = States()
)

data class States(
    val hp: Int = 30,
    val sp: Int = 0,
    @SerializedName("f-atk") val fAtk: Int = 0,
    @SerializedName("f-def") val fDef: Int = 0,
    @SerializedName("m-atk") val mAtk: Int = 0,
    @SerializedName("m-def") val mDef: Int = 0
)