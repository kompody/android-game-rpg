// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.di.provider

import io.reactivex.disposables.CompositeDisposable
import toothpick.InjectConstructor
import javax.inject.Provider

@InjectConstructor
class CompositeDisposableProvider : Provider<CompositeDisposable> {
    override fun get() = CompositeDisposable()
}