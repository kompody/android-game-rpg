// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.custom

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class StatelessEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
): AppCompatEditText(context, attrs) {
    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(null)
    }
}