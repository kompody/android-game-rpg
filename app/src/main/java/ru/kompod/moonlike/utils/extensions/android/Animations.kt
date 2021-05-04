// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.android

import android.animation.ObjectAnimator

fun floatAnimationOf(target: Any, propName: String, vararg values: Float, block: ObjectAnimator.() -> Unit): ObjectAnimator {
    return ObjectAnimator.ofFloat(target, propName, *values).apply(block)
}