// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.android

import androidx.lifecycle.Lifecycle

fun Lifecycle.State.isInForeground() = isAtLeast(Lifecycle.State.STARTED)