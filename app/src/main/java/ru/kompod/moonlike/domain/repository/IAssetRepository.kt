// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.repository

import ru.kompod.moonlike.domain.entity.base.*

interface IAssetRepository {
    fun getCharacterFractionsInfo(): List<FractionInfoObject>

    fun getCharacterFraction(): List<FractionObject>
    fun getCharacterFractionById(id: Int): FractionObject

    fun getCharacterGenders(): List<GenderObject>
    fun getCharacterGenderById(id: Int): GenderObject

    fun getCharacterRoles(): List<RoleInfoObject>
    fun getCharacterRoleById(id: Int): RoleInfoObject

    fun getMaps(): List<MapObject>
    fun getMapById(id: Int): MapObject

    fun getMonsters(): List<MonsterObject>
    fun getMonsterById(id: Int): MonsterObject
//    fun getBossesByBiomeId(id: Int): List<MonsterObject>
}