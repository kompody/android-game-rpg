// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.module

import android.content.SharedPreferences
import ru.kompod.moonlike.data.database.KompodDatabase
import ru.kompod.moonlike.data.database.dao.CharacterDao
import ru.kompod.moonlike.di.provider.DatabaseProvider
import ru.kompod.moonlike.di.provider.SharedPreferencesProvider
import ru.kompod.moonlike.di.provider.dao.CharacterDaoProvider
import ru.kompod.moonlike.utils.extensions.toothpick.bind
import toothpick.config.Module

class DbModule : Module() {
    init {
        bind<SharedPreferences>()
            .toProvider(SharedPreferencesProvider::class.java)
            .providesSingleton()

        bind<KompodDatabase>()
            .toProvider(DatabaseProvider::class.java)
            .providesSingleton()

        bind<CharacterDao>()
            .toProvider(CharacterDaoProvider::class.java)
            .providesSingleton()
    }
}