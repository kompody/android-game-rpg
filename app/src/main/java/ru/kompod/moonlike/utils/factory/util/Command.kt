// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.factory.util

import ru.kompod.moonlike.domain.entity.base.OnMapObject

sealed class Command {
    object RecoveryHealCharacterCommand : Command()
    object RecoverySpellPointCharacterCommand : Command()

    class RefreshMapCommand(val mapId: Int) : Command()
    class KillMonsterOnMapCommand(val mapId: Int, val monster: OnMapObject) : Command()
}