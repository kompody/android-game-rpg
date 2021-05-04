// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.custom

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import kotlinx.android.synthetic.main.custom_edit_text_with_progress.view.*
import ru.kompod.moonlike.R
import ru.kompod.moonlike.utils.extensions.android.setVisibleOrGone
import ru.kompod.moonlike.utils.extensions.kotlin.empty

class PreloaderButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    var labelText: String
        get() = textView.text?.toString() ?: String.empty
        set(value) {
            textView.text = value
        }

    var textColor = Color.BLACK
        set(value) {
            field = value
            textView.setTextColor(value)
        }

    init {
        View.inflate(context, R.layout.custom_edit_text_with_progress, this)
        orientation = VERTICAL

        context.withStyledAttributes(attrs, R.styleable.PreloaderButton) {
            labelText = getString(R.styleable.PreloaderButton_pb_text) ?: String.empty
            textColor = getColor(R.styleable.PreloaderButton_pb_textColor, Color.BLACK)
        }
    }

    fun showProgress() {
        textView.setVisibleOrGone(false)
        progressBar.setVisibleOrGone(true)
    }

    fun hideProgress() {
        progressBar.setVisibleOrGone(false)
        textView.setVisibleOrGone(true)
    }
}