// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.network.model

import com.google.gson.annotations.SerializedName

data class RoleApiModel(
    val id: Short,
    val label: String,
    val description: String,
    val states: States
)

data class States(
    @SerializedName("f-atk") val fAtk: Short,
    @SerializedName("f-def") val fDef: Short,
    @SerializedName("m-atk") val mAtk: Short,
    @SerializedName("m-def") val mDef: Short
)