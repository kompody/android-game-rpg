// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ru.kompod.moonlike.data.sharedpreferences.ObservableSharedPreferences
import ru.kompod.moonlike.domain.repository.IPreferencesRepository
import javax.inject.Inject

class PreferencesRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val observableSharedPreferences: ObservableSharedPreferences
) : IPreferencesRepository {
    companion object {
        const val USE_TEST_HOST = "use_test_host"
    }

    private fun putParams(params: Map<String, Any>): Completable =
        Completable.fromCallable {
            sharedPreferences.edit {
                for (param in params) {
                    putString(param.key, param.value.toString())
                }
            }
        }

    override fun putUseTestHost(isUseTestHost: Boolean): Completable =
        putParams(
            mapOf(
                USE_TEST_HOST to isUseTestHost
            )
        )

    override fun isUseTestHost(): Observable<Boolean> =
        observableSharedPreferences.observeString(USE_TEST_HOST)
            .map(String::toBoolean)
            .subscribeOn(Schedulers.io())
}