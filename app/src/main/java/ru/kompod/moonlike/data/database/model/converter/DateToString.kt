// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.database.model.converter

import androidx.room.TypeConverter
import ru.kompod.moonlike.data.database.base.IModelFieldConverter
import java.util.*

class DateToString : IModelFieldConverter<Date, String> {

    @TypeConverter
    override fun saveTo(value: Date): String {
        return value.time.toString()
    }

    @TypeConverter
    override fun restoreFrom(value: String): Date {
        return Date(value.toLong())
    }
}