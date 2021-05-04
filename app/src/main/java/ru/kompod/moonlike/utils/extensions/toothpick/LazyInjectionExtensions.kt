// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.toothpick

import toothpick.ktp.delegate.LazyDelegateProvider
import kotlin.reflect.KClass

inline fun <reified T : Any> lazyInjection() = LazyDelegateProvider(T::class.java)
inline fun <reified T : Any> lazyInjection(name: String) = LazyDelegateProvider(T::class.java, name)
inline fun <reified T : Any> lazyInjection(name: KClass<out Annotation>) = LazyDelegateProvider(T::class.java, name.java)