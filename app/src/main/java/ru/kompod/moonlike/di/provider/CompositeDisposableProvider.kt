// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.provider

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Provider

class CompositeDisposableProvider @Inject constructor(): Provider<CompositeDisposable> {
    override fun get() = CompositeDisposable()
}