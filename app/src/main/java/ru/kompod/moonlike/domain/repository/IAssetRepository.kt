// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.repository

import io.reactivex.Single
import ru.kompod.moonlike.domain.entity.base.*

interface IAssetRepository {
    fun getCharacterRacesInfo(): List<RaceInfo>
    fun getCharacterRaces(): List<Race>
    fun getCharacterGenders(): List<Gender>
    fun getCharacterPortraits(raceId: Short, genderId: Short): List<Portrait>
    fun getCharacterRoles(): List<Role>

    fun getCharacterRaceById(id: Short): Race
    fun getCharacterGenderById(id: Short): Gender
    fun getCharacterPortraitById(raceId: Short, genderId: Short, portraitId: Short): Portrait
    fun getCharacterRoleById(id: Short): Role
}