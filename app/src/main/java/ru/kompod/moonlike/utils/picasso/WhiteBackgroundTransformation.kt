// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.picasso

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.squareup.picasso.Transformation
import toothpick.InjectConstructor

@InjectConstructor
class WhiteBackgroundTransformation : Transformation {
    override fun key(): String = "RGB565"

    override fun transform(source: Bitmap): Bitmap {
        val result = Bitmap.createBitmap(source.width, source.height, source.config)
        val canvas = Canvas(result)
        val paint = Paint().apply {
            isAntiAlias = true
        }

        canvas.drawColor(Color.WHITE)
        canvas.drawBitmap(source, 0f, 0f, paint)

        source.recycle()
        return result
    }
}