// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.network

import com.google.gson.Gson
import ru.kompod.moonlike.domain.entity.HttpErrorBody
import ru.kompod.moonlike.utils.extensions.gson.fromJsonOrNull
import javax.inject.Inject

class HttpErrorMapper @Inject constructor(
    private val gson: Gson
) {
    fun map(rawString: String): HttpErrorBody? {
        return gson.fromJsonOrNull(rawString)
    }
}