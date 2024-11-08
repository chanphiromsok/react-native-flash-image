package com.flashimage

import android.view.View
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.uimanager.SimpleViewManager

abstract class FlashImageViewManagerSpec<T : View> : SimpleViewManager<T>() {
  /**
   * `source` -> `options : {url,cachePolicy,autoPlayGif}`
   */
  abstract fun setSource(view: T, options: ReadableMap)
}
