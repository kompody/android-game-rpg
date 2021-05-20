// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.repository

import io.reactivex.Observable
import io.reactivex.Single

interface IPreferencesRepository {
    fun putSelectedCharacter(id: Int): Single<Unit>
    fun getSelectedCharacter(): Observable<Int>
}