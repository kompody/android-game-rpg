// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.getSystemService
import io.reactivex.Observable
import javax.inject.Inject

class InternetState @Inject constructor(
    private val context: Context
) {
    val observable: Observable<Boolean> = Observable.create { emitter ->
        val connectivityManager = context.getSystemService<ConnectivityManager>()

        if (connectivityManager == null) {
            emitter.onComplete()

            return@create
        }

        val callback = object: ConnectivityManager.NetworkCallback() {
            override fun onLost(network: Network) {
                emitter.onNext(false)
            }

            override fun onAvailable(network: Network) {
                emitter.onNext(true)
            }
        }

        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            callback
        )

        emitter.setCancellable { connectivityManager.unregisterNetworkCallback(callback) }

        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.let { capabilities ->
            emitter.onNext(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(
                NetworkCapabilities.TRANSPORT_WIFI))
        } ?: emitter.onNext(false)
    }
        get() = field.share()
}