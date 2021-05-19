// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.repository

import ru.kompod.moonlike.domain.entity.base.*

interface IAssetRepository {
    fun getCharacterFractionsInfo(): List<FractionInfoObject>

    fun getCharacterFraction(): List<FractionObject>
    fun getCharacterFractionById(id: Short): FractionObject

    fun getCharacterGenders(): List<GenderObject>
    fun getCharacterGenderById(id: Short): GenderObject

    fun getCharacterRoles(): List<RoleInfoObject>
    fun getCharacterRoleById(id: Short): RoleInfoObject

    fun getMaps(): List<MapObject>
    fun getMapById(id: Short): MapObject

    fun getMonsters(): List<MonsterObject>
    fun getMonsterById(id: Short): MonsterObject
//    fun getBossesByBiomeId(id: Short): List<MonsterObject>
}