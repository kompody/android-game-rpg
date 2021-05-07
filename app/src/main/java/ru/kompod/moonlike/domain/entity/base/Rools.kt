// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.entity.base

class Character(
    val id: Short,
    val label: String,
    val race: Race,
    val gender: Gender,
    val portrait: Portrait,
    val role: Role
)

class RaceInfo(
    val race: Race,
    val genders: List<Gender>,
    val portraits: List<List<Portrait>>,
    val roles: List<Role>
)

class Race(
    val id: Short,
    val label: String,
    val description: String
)

class Gender(
    val id: Short,
    val label: String
)

class Portrait(
    val id: Short,
    val path: String
)

class Role(
    val id: Short,
    val label: String,
    val description: String,
    val states: States
)

open class State(val value: Short)

class STR(value: Short) : State(value)
class AGI(value: Short) : State(value)
class INT(value: Short) : State(value)

class States(val str: STR, val agi: AGI, val int: INT) {
    companion object {
        fun fromRaw(str: Short, agi: Short, int: Short) = States(STR(str), AGI(agi), INT(int))
    }
}
