// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.custom.text

import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.TypefaceSpan

class CustomTypefaceSpan(
    private val typeface: Typeface
) : TypefaceSpan(null) {
    override fun updateDrawState(textPaint: TextPaint) {
        textPaint.typeface = typeface
    }

    override fun updateMeasureState(textPaint: TextPaint) {
        textPaint.typeface = typeface
    }

    override fun getTypeface() = typeface
}