package com.flashimage

import android.view.View
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.uimanager.SimpleViewManager

abstract class FlashImageViewManagerSpec<T : View> : SimpleViewManager<T>() {
  /**
   * `source` -> `options : {url,cachePolicy,autoPlayGif}`
   */
  abstract fun setSource(view: T, options: ReadableMap)
  abstract fun setContentFit(view: T, contentFit: String?)
  abstract fun setHeaders(view: T, headers: ReadableMap?)
  abstract fun setCachePolicy(view: FlashImageView, cachePolicy: String?)
  abstract fun setTint(view: FlashImageView, tint: Int?)
  abstract fun setAllowHardware(view: FlashImageView, allowHardware: Boolean?)
  abstract fun setAutoPlayGif(view: FlashImageView, autoPlayGif: Boolean?)

}
