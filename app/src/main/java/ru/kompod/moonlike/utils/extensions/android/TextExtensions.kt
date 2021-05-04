// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.android

import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.text.inSpans
import ru.kompod.moonlike.presentation.custom.text.CustomTypefaceSpan

class CustomClickableSpan(
    onClick: (() -> Unit)? = null
): ClickableSpan() {
    var clickListener: (() -> Unit)? = onClick

    override fun onClick(widget: View) {
        clickListener?.invoke()
    }

    override fun updateDrawState(drawState: TextPaint) {
        super.updateDrawState(drawState)
        drawState.isUnderlineText = false
    }
}

inline fun SpannableStringBuilder.typeFaceSpan(typeface: Typeface,
                                               builderAction: SpannableStringBuilder.() -> Unit
) =
    inSpans(CustomTypefaceSpan(typeface), builderAction = builderAction)

inline fun SpannableStringBuilder.clickableSpan(noinline onClick: (() -> Unit)? = null,
                                                builderAction: SpannableStringBuilder.() -> Unit
) =
    inSpans(CustomClickableSpan(onClick), builderAction = builderAction)

inline fun SpannableStringBuilder.colorSpan(color: Int, builderAction: SpannableStringBuilder.() -> Unit): SpannableStringBuilder {
    return inSpans(ForegroundColorSpan(color), builderAction)
}