// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.picasso

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import androidx.core.graphics.scale
import com.squareup.picasso.Transformation

class ScaleTransformation : Transformation {
    override fun key() = "scale"

    override fun transform(source: Bitmap): Bitmap {
        val bitmap = Bitmap.createScaledBitmap(source, 16, 16, true)
        source.recycle()
        return bitmap
    }
}