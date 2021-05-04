// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.android

import android.graphics.Canvas
import android.text.Layout
import androidx.core.graphics.withTranslation

fun Layout.draw(canvas: Canvas, x: Float, y: Float) = canvas.withTranslation(x, y, this::draw)