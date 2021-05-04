// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.custom.toolbarlayout

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import ru.kompod.moonlike.R

open class DoubleStateButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatImageButton(context, attrs, defStyle) {
    private val extraStates: IntArray = IntArray(1)

    var isActive: Boolean = false
        set(value) {
            field = value
            refreshDrawableState()
        }

    init {
        refreshDrawableState()
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val states = super.onCreateDrawableState(extraSpace + 1)
        val extraStates = if (isActive)
            STATE_ACTIVE
        else
            STATE_INACTIVE
        return mergeDrawableStates(states, extraStates)
    }

    companion object {
        private val STATE_ACTIVE = intArrayOf(R.attr.state_button_active)
        private val STATE_INACTIVE = intArrayOf(R.attr.state_button_inactive)
    }
}