// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.factory

import ru.kompod.moonlike.utils.ResourceDelegate
import javax.inject.Inject

class Assets @Inject constructor(
    private val resources: ResourceDelegate
) {
    companion object {
        const val FRACTION_INFO: String = "json/character/fraction_info.json"
        const val FRACTION: String = "json/character/fraction.json"
        const val GENDER: String = "json/character/gender.json"
        const val ROLE: String = "json/character/role.json"
        const val MAP: String = "json/map/map.json"
        const val BIOME: String = "json/map/biome.json"
        const val MONSTER: String = "json/character/monster.json"
    }
}