// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.domain.repository

import io.reactivex.Completable
import io.reactivex.Observable

interface IPreferencesRepository {
    fun putUseTestHost(isUseTestHost: Boolean): Completable
    fun isUseTestHost(): Observable<Boolean>
}