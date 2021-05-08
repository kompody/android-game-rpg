// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.entity.base

//character
class CharacterObject(
    val id: Short,
    val label: String,
    val race: RaceObject,
    val gender: GenderObject,
    val portrait: PortraitObject,
    val role: RoleObject
)

class RaceInfoObject(
    val race: RaceObject,
    val genders: List<GenderObject>,
    val portraits: List<List<PortraitObject>>,
    val roles: List<RoleObject>
)

class RaceObject(
    val id: Short,
    val label: String,
    val description: String
)

class GenderObject(
    val id: Short,
    val label: String
)

class PortraitObject(
    val id: Short,
    val path: String
)

class RoleObject(
    val id: Short,
    val label: String,
    val description: String,
    val states: States
)

open class StateObject(val value: Short)

class STR(value: Short) : StateObject(value)
class AGI(value: Short) : StateObject(value)
class INT(value: Short) : StateObject(value)

class Attack(value: Short) : StateObject(value)
class Magic(value: Short) : StateObject(value)
class PhysicalDefense(value: Short) : StateObject(value)
class MagicalDefense(value: Short) : StateObject(value)

class States(val str: STR, val agi: AGI, val int: INT) {
    companion object {
        fun fromRaw(str: Short, agi: Short, int: Short) = States(STR(str), AGI(agi), INT(int))
    }
}

//loot item

//map
class MapObject(
    val id: Short,
    val label: String,
    val path: String,
    val biome: Short,
    val delay: Int,
    val travels: List<TravelObject>
)

class TravelObject(
    val id: Short,
    val label: String,
    val to: Short
)

//npc
open class NPCObject(
    val id: Short,
    val label: String,
    val race: RaceObject,
    val gender: GenderObject,
    val portrait: PortraitObject,
    val role: RoleObject
)

class Peaceful(
    id: Short,
    label: String,
    race: RaceObject,
    gender: GenderObject,
    portrait: PortraitObject,
    role: RoleObject
) : NPCObject(id, label, race, gender, portrait, role)

class Monster(
    id: Short,
    label: String,
    race: RaceObject,
    gender: GenderObject,
    portrait: PortraitObject,
    role: RoleObject
) : NPCObject(id, label, race, gender, portrait, role)

//quest
