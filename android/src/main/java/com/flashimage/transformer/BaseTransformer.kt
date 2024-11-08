package com.flashimage.transformer

import coil3.transform.Transformation

abstract class BaseTransformation(
  val transformer: Transformer
) : Transformation() {
  override val cacheKey: String
    get() = transformer.key()
}
