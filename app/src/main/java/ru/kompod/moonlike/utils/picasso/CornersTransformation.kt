// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.picasso

import android.graphics.*
import com.squareup.picasso.Transformation

class CornersTransformation(private val cornersRadiusPx: Float): Transformation {
    override fun key() = "corners"

    override fun transform(source: Bitmap): Bitmap {
        val squaredBitmap = Bitmap.createBitmap(source, 0, 0, source.width, source.height)
        val bitmap = Bitmap.createBitmap(source.width, source.height, source.config)
        source.recycle()
        val canvas = Canvas(bitmap)
        val paint = Paint().apply {
            shader = BitmapShader(squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            isAntiAlias = true
        }

        canvas.drawRoundRect(RectF(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat()), cornersRadiusPx, cornersRadiusPx, paint)

        squaredBitmap.recycle()

        return bitmap
    }
}