// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.repository

import ru.kompod.moonlike.domain.entity.base.*

interface IAssetRepository {
    fun getCharacterRacesInfo(): List<RaceInfoObject>
    fun getCharacterRaces(): List<RaceObject>
    fun getCharacterGenders(): List<GenderObject>
    fun getCharacterPortraits(raceId: Short, genderId: Short): List<PortraitObject>
    fun getCharacterRoles(): List<RoleObject>

    fun getCharacterRaceById(id: Short): RaceObject
    fun getCharacterGenderById(id: Short): GenderObject
    fun getCharacterPortraitById(raceId: Short, genderId: Short, portraitId: Short): PortraitObject
    fun getCharacterRoleById(id: Short): RoleObject

    fun getMaps(): List<MapObject>
    fun getMapById(id: Short): MapObject
}