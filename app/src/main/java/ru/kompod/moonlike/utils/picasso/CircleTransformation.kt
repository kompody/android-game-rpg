// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.picasso

import android.graphics.*
import com.squareup.picasso.Transformation
import javax.inject.Inject
import kotlin.math.min

class CircleTransformation @Inject constructor() : Transformation {
    override fun key() = "circle"

    override fun transform(source: Bitmap): Bitmap {
        val size = min(source.width, source.height)
        val x = (source.width - size) / 2
        val y = (source.height - size) / 2

        val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
        val bitmap = Bitmap.createBitmap(size, size, source.config)
        source.recycle()
        val canvas = Canvas(bitmap)
        val paint = Paint().apply {
            shader = BitmapShader(squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            isAntiAlias = true
        }

        val radius = size / 2f
        canvas.drawCircle(radius, radius, radius, paint)

        squaredBitmap.recycle()

        return bitmap
    }
}