// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.network.mapper

import android.content.Context
import ru.kompod.moonlike.data.network.model.MapApiModel
import ru.kompod.moonlike.data.network.model.TravelApiModel
import ru.kompod.moonlike.data.repository.AssetRepository
import ru.kompod.moonlike.domain.entity.base.MapObject
import ru.kompod.moonlike.domain.entity.base.MonsterObject
import ru.kompod.moonlike.domain.entity.base.TravelObject
import ru.kompod.moonlike.utils.NO_ID
import ru.kompod.moonlike.utils.extensions.kotlin.getResource
import javax.inject.Inject

class MapMapper @Inject constructor(
    private val context: Context,
    private val monsterMapper: MonsterMapper
) {
    companion object {
        private const val BIOME_FIELD = "field"
        private const val BIOME_FIELD_ID: Short = 1
        private const val BIOME_SWAMP = "swamp"
        private const val BIOME_SWAMP_ID: Short = 2
        private const val BIOME_MINE = "mine"
        private const val BIOME_MINE_ID: Short = 3
        private const val BIOME_RUIN = "ruin"
        private const val BIOME_RUIN_ID: Short = 4
        private const val BIOME_DESERT = "desert"
        private const val BIOME_DESERT_ID: Short = 5
        private const val BIOME_SNOW = "snow"
        private const val BIOME_SNOW_ID: Short = 6
    }

    fun mapMapApiModelToEntity(model: MapApiModel, monsters: List<MonsterObject>): MapObject = MapObject(
        id = model.id,
        label = model.label.getResource(context),
        path = model.path,
        biome = mapBiomeByTitle(model.biome),
        monsterLimit = model.monsterLimit,
        bossesLimit = model.bossesLimit,
        delay = model.delay,
        travels = model.travels.map { mapTravel(it) },
        monsters = monsters,
        objects = model.objects,
        actors = model.actors
    )

    private fun mapBiomeByTitle(biome: String): Short =
        when (biome) {
            BIOME_FIELD -> BIOME_FIELD_ID
            BIOME_SWAMP -> BIOME_SWAMP_ID
            BIOME_MINE -> BIOME_MINE_ID
            BIOME_RUIN -> BIOME_RUIN_ID
            BIOME_DESERT -> BIOME_DESERT_ID
            BIOME_SNOW -> BIOME_SNOW_ID
            else -> NO_ID
        }

    private fun mapTravel(travel: TravelApiModel): TravelObject =
            TravelObject(
                id = travel.id,
                label = travel.label,
                to = travel.to
            )
}