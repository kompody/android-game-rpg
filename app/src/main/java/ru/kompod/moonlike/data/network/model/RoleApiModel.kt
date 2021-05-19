// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.network.model

import com.google.gson.annotations.SerializedName
import ru.kompod.moonlike.utils.NO_ID
import ru.kompod.moonlike.utils.extensions.kotlin.empty

data class RoleApiModel(
    val id: Short = NO_ID,
    val label: String = String.empty,
    val description: String = String.empty,
    val states: States = States()
)

data class States(
    val hp: Short = 30,
    val sp: Short = 0,
    @SerializedName("f-atk") val fAtk: Short = 0,
    @SerializedName("f-def") val fDef: Short = 0,
    @SerializedName("m-atk") val mAtk: Short = 0,
    @SerializedName("m-def") val mDef: Short = 0
)