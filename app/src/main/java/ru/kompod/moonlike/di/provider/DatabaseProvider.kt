// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.provider

import android.content.Context
import androidx.room.Room
import ru.kompod.moonlike.data.database.KompodDatabase
import javax.inject.Inject
import javax.inject.Provider

class DatabaseProvider @Inject constructor(
    private val context: Context
) : Provider<KompodDatabase> {
    override fun get() = Room
        .databaseBuilder(context, KompodDatabase::class.java, DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()

    companion object {
        private const val DATABASE_NAME = "MOONLIKE_DATABASE"
    }
}