// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.android

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ru.kompod.moonlike.presentation.base.BaseFragment
import ru.kompod.moonlike.utils.extensions.kotlin.castTo

inline fun<reified T> Fragment.argument(key: String, defaultValue: T? = null): T {
    return (arguments?.get(key) ?: defaultValue) as T
}

inline fun FragmentManager.beginTransaction(block: FragmentTransaction.() -> Unit) {
    beginTransaction().apply(block)
}

fun FragmentManager.findTopFragment(): Fragment? =
    fragments.firstOrNull(Fragment::isVisible)?.castTo<BaseFragment<*>>()?.findTopFragment()