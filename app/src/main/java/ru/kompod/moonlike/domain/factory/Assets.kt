// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.factory

import ru.kompod.moonlike.utils.ResourceDelegate
import javax.inject.Inject

class Assets @Inject constructor(
    private val resources: ResourceDelegate
) {
    companion object {
        const val FRACTION_INFO: String = "json/fraction_info.json"
        const val FRACTION: String = "json/fraction.json"
        const val GENDER: String = "json/gender.json"
        const val ROLE: String = "json/role.json"
        const val MAP: String = "json/map.json"
        const val BIOME: String = "json/biome.json"
        const val MONSTER: String = "json/monster.json"
    }
}