package com.flashimage.transformer

import android.graphics.Bitmap
import com.facebook.react.BuildConfig

/**
 * Copyright (C) 2020 Wasabeef
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

abstract class Transformer {
  protected val id: String
//    In react native does have class BuildConfig.Version that why i remove this
//    get() = "${this::class.java.name}-${BuildConfig.Version}"
    get() = this::class.java.name


  abstract fun transform(
    source: Bitmap,
    destination: Bitmap
  ): Bitmap

  abstract fun key(): String
}
