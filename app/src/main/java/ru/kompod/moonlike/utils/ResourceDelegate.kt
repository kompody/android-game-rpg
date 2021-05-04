// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.getColorOrThrow
import javax.inject.Inject

class ResourceDelegate @Inject constructor(
    private val context: Context
) {
    fun getString(@StringRes id: Int, vararg formatArgs: Any)
            = context.resources.getString(id, *formatArgs)

    fun getQuantityString(@PluralsRes id: Int, quantity: Int, vararg formatArgs: Any)
            = context.resources.getQuantityString(id, quantity, quantity, *formatArgs)

    fun getColor(@ColorRes colorRes: Int) = ContextCompat.getColor(context, colorRes)

    fun getDimension(@DimenRes dimenRes: Int) = context.resources.getDimension(dimenRes)

    fun getTypeface(@FontRes fontRes: Int) = ResourcesCompat.getFont(context, fontRes) ?: throw RuntimeException()

    fun getDrawable(@DrawableRes drawableRes: Int): Drawable = context.resources.getDrawable(drawableRes, context.theme)

    fun getColorsArray(@ArrayRes arrayRes: Int): IntArray {
        return context
            .resources
            .obtainTypedArray(arrayRes)
            .let { array ->
                val result = IntArray(array.length())
                for (index in 0 until array.length()) {
                    result[index] = array.getColorOrThrow(index)
                }
                result
            }
    }

    fun getDisplayMetrics(): DisplayMetrics = context.resources.displayMetrics

    fun getInteger(@IntegerRes intRes: Int) = context.resources.getInteger(intRes)

    fun getFloat(@DimenRes floatRes: Int) = ResourcesCompat.getFloat(context.resources, floatRes)
}