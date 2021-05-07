// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.provider.dao

import ru.kompod.moonlike.data.database.KompodDatabase
import ru.kompod.moonlike.data.database.dao.CharacterDao
import javax.inject.Inject
import javax.inject.Provider

class CharacterDaoProvider @Inject constructor(
    private val database: KompodDatabase
) : Provider<CharacterDao> {
    override fun get() = database.characterDao()
}