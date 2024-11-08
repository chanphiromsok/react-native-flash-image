package com.flashimage.transformer

import android.graphics.Bitmap
import coil3.size.Size
import com.flashimage.transformer.types.CornerType
import android.graphics.Bitmap.createBitmap
import com.flashimage.transformer.core.RoundedCorners
import com.flashimage.transformer.core.bitmapConfig


class RoundedCornersTransformation @JvmOverloads constructor(
  radius: Int,
  diameter: Int = radius * 2,
  margin: Int = 0,
  cornerType: CornerType = CornerType.ALL
) : BaseTransformation(RoundedCorners(radius, diameter, margin, cornerType)) {

  override suspend fun transform(input: Bitmap, size: Size): Bitmap {
    val output = createBitmap(input.width, input.height, bitmapConfig(input))
    return transformer.transform(input, output)
  }
}
