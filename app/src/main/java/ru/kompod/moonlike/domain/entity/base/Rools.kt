// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.entity.base

//character
data class NewCharacterObject(
    val id: Int,
    val label: String,
    val description: String,
    val fraction: FractionObject,
    val gender: GenderObject,
    val portrait: String,
    val role: RoleInfoObject
)

data class CharacterObject(
    val id: Int,
    val label: String,
    val description: String,
    val fraction: FractionObject,
    val gender: GenderObject,
    val portrait: String,
    val role: RoleObject,
    val level: Int,
    val exp: Long,
    val baseHp: Int,
    val baseSp: Int,
    val hp: Int,
    val sp: Int,
    val baseFAtk: Int,
    val baseFDef: Int,
    val baseMAtk: Int,
    val baseMDef: Int
)

data class FractionObject(
    val id: Int,
    val label: String,
    val description: String
)

data class FractionInfoObject(
    val fraction: FractionObject,
    val characters: List<CharacterInfoObject>
)

data class CharacterInfoObject(
    val id: Int,
    val label: String,
    val description: String,
    val gender: GenderObject,
    val portraits: List<String>,
    val roles: List<RoleInfoObject>
)

data class RaceObject(
    val id: Int,
    val label: String,
    val description: String
)

data class GenderObject(
    val id: Int,
    val label: String
)

data class RoleInfoObject(
    val id: Int,
    val label: String,
    val description: String,
    val states: States
)

data class RoleObject(
    val id: Int,
    val label: String,
    val description: String
)

data class States(
    val hp: Int,
    val sp: Int,
    val fAtk: Int,
    val fDef: Int,
    val mAtk: Int,
    val mDef: Int
)

//loot item

//map
data class MapObject(
    val id: Int,
    val label: String,
    val path: String,
    val biome: Int,
    val monsterLimit: Int = 5,
    val bossesLimit: Int = 1,
    val delay: Int = 15 * 1000,
    val travels: List<TravelObject> = listOf(),
    val monsters: List<MonsterObject> = listOf(),
    val objects: List<Int> = listOf(),
    val actors: List<Int> = listOf()
)

data class TravelObject(
    val id: Int,
    val label: String,
    val to: Int
)

//npc
data class NPCObject(
    val id: Int,
    val label: String,
    val gender: GenderObject,
    val portrait: String,
    val role: RoleInfoObject
)

data class PeacefulObject(
    val id: Int,
    val label: String,
    val gender: GenderObject,
    val portrait: String,
    val role: RoleInfoObject
)

data class MonsterObject(
    val id: Int,
    val label: String,
    val gender: GenderObject,
    val portrait: String,
    val level: Int,
    val exp: Int,
    val baseHp: Int,
    val hp: Int,
    val sp: Int,
    val fAtk: Int,
    val fDef: Int,
    val mAtk: Int,
    val mDef: Int,
    val spawnRate: Float = 1f,
    val delay: Int = 20 * 1000
)

data class OnMapObject(
    val monster: MonsterObject,
    val idOnPool: Int = monster.id,
    var isLife: Boolean = false,
    var timeDeath: Long = 0
)

//quest
