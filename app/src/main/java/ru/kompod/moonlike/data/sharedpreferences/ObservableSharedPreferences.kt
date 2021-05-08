// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.data.sharedpreferences

import android.content.SharedPreferences
import io.reactivex.Observable
import ru.kompod.moonlike.utils.extensions.kotlin.empty
import javax.inject.Inject

class ObservableSharedPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    private val observable = Observable.create<String> { emitter ->
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            emitter.onNext(key)
        }

        emitter.setCancellable {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }

        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }
        .share()

    fun observeString(key: String): Observable<String> {
        return Observable.concat(
            Observable.just(sharedPreferences.getString(key, String.empty)),
            observable
                .filter { it == key }
                .map { sharedPreferences.getString(key, String.empty) }
        )
    }

    fun observeInt(key: String): Observable<Int> {
        return Observable.concat(
            Observable.just(sharedPreferences.getInt(key, 0)),
            observable
                .filter { it == key }
                .map { sharedPreferences.getInt(key, 0) }
        )
    }

    fun observeShort(key: String): Observable<Short> {
        return Observable.concat(
            Observable.just(sharedPreferences.getInt(key, 0))
                .map { it.toShort() },
            observable
                .filter { it == key }
                .map { sharedPreferences.getInt(key, 0) }
                .map { it.toShort() }
        )
    }

    fun observeBoolean(key: String, default: Boolean): Observable<Boolean> {
        return Observable.concat(
            Observable.just(sharedPreferences.getBoolean(key, default)),
            observable
                .filter { it == key }
                .map { sharedPreferences.getBoolean(key, default) }
        )
    }
}