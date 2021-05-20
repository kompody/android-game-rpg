// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.kompod.moonlike.data.sharedpreferences.ObservableSharedPreferences
import ru.kompod.moonlike.domain.repository.IPreferencesRepository
import ru.kompod.moonlike.utils.NO_ID
import ru.kompod.moonlike.utils.extensions.kotlin.castTo
import ru.kompod.moonlike.utils.extensions.kotlin.unsafeCastTo
import ru.kompod.moonlike.utils.extensions.rxjava.io
import javax.inject.Inject

class PreferencesRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val observableSharedPreferences: ObservableSharedPreferences
) : IPreferencesRepository {
    companion object {
        const val SELECTED_CHARACTER_ID = "selected_character_id"
    }

    private fun putParams(params: Map<String, Any>): Single<Unit> =
        Single.fromCallable {
            sharedPreferences.edit {
                for (param in params) {
                    when (param.value) {
                        is Int -> putInt(param.key, param.value as Int)
                        is Short -> putInt(param.key, (param.value as Short).toInt())
                        is Boolean -> putBoolean(param.key, param.value as Boolean)
                        else -> putString(param.key, param.value.toString())
                    }
                }
            }
        }

    override fun putSelectedCharacter(id: Int): Single<Unit> =
        putParams(
            mapOf(
                SELECTED_CHARACTER_ID to id
            )
        )

    override fun getSelectedCharacter(): Observable<Int> =
        observableSharedPreferences.observeInt(SELECTED_CHARACTER_ID)
            .onErrorReturnItem(NO_ID)
            .subscribeOn(io())
}