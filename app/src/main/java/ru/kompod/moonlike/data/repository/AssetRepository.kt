// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.repository

import com.google.gson.Gson
import ru.kompod.moonlike.data.network.mapper.MapMapper
import ru.kompod.moonlike.data.network.mapper.CharacterMapper
import ru.kompod.moonlike.data.network.mapper.MonsterMapper
import ru.kompod.moonlike.data.network.model.*
import ru.kompod.moonlike.domain.entity.base.*
import ru.kompod.moonlike.domain.factory.Assets
import ru.kompod.moonlike.domain.repository.IAssetRepository
import ru.kompod.moonlike.utils.extensions.gson.fromJson
import ru.kompod.moonlike.utils.tools.AssetProvider
import javax.inject.Inject

class AssetRepository @Inject constructor(
    private val gson: Gson,
    private val assets: Assets,
    private val assetProvider: AssetProvider,
    private val characterMapper: CharacterMapper,
    private val mapMapper: MapMapper,
    private val monsterMapper: MonsterMapper
) : IAssetRepository {

    override fun getCharacterFractionsInfo(): List<FractionInfoObject> =
        gson.fromJson<List<FractionInfoApiModel>>(assetProvider.loadJSONFromAsset(Assets.FRACTION_INFO))
            .map { characterMapper.mapApiModelToEntity(it) }

    override fun getCharacterFraction(): List<FractionObject> =
        gson.fromJson<List<FractionApiModel>>(assetProvider.loadJSONFromAsset(Assets.FRACTION))
            .map { characterMapper.mapApiModelToEntity(it) }

    override fun getCharacterFractionById(id: Short): FractionObject =
        getCharacterFraction().first { it.id == id }

    override fun getCharacterGenders(): List<GenderObject> =
        gson.fromJson<List<GenderApiModel>>(assetProvider.loadJSONFromAsset(Assets.GENDER))
            .map { characterMapper.mapGender(it) }

    override fun getCharacterGenderById(id: Short): GenderObject =
        getCharacterGenders().first { it.id == id }

    override fun getCharacterRoles(): List<RoleObject> =
        gson.fromJson<List<RoleApiModel>>(assetProvider.loadJSONFromAsset(Assets.ROLE))
            .map { characterMapper.mapRole(it) }

    override fun getCharacterRoleById(id: Short): RoleObject =
        getCharacterRoles().first { it.id == id }

    override fun getMaps(): List<MapObject> =
        gson.fromJson<List<MapApiModel>>(assetProvider.loadJSONFromAsset(Assets.MAP))
            .map { model ->
                model to model.monsters.map {
                    getMonsterById(it)
                }
            }
            .map { (model, monsters) -> mapMapper.mapMapApiModelToEntity(model, monsters) }

    override fun getMapById(id: Short): MapObject =
        getMaps().first { it.id == id }

    override fun getMonsters(): List<MonsterObject> =
        gson.fromJson<List<MonsterApiModel>>(assetProvider.loadJSONFromAsset(Assets.MONSTER))
            .map { monsterMapper.mapApiModelToEntity(it) }

    override fun getMonsterById(id: Short): MonsterObject =
        getMonsters()
            .first { it.id == id }

    //    override fun getMonstersByBiomeId(id: Short): List<MonsterObject> =
//        gson.fromJson<List<BiomeApiModel>>(assetProvider.loadJSONFromAsset(Assets.BIOME))
//            .map {  }

//    override fun getBossesByBiomeId(id: Short): List<MonsterObject> = assets.fieldMonsters
}