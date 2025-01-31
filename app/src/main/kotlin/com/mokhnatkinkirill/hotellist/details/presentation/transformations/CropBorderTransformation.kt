package com.mokhnatkinkirill.hotellist.details.presentation.transformations

import android.graphics.Bitmap
import coil.size.Size
import coil.transform.Transformation

class CropBorderTransformation : Transformation {

    override val cacheKey: String = "CropBorderTransformation"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val width = input.width
        val height = input.height

        return if (input.width >= 3 && input.height >= 3) {
            Bitmap.createBitmap(
                input,
                1, 1, width - 2, height - 2
            )
        } else input
    }
}