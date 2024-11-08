package com.flashimage

import android.view.View

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ViewManagerDelegate
import com.facebook.react.viewmanagers.FlashImageViewManagerDelegate
import com.facebook.react.viewmanagers.FlashImageViewManagerInterface

abstract class FlashImageViewManagerSpec<T : View> : SimpleViewManager<T>(), FlashImageViewManagerInterface<T> {
  private val mDelegate: ViewManagerDelegate<T>

  init {
    mDelegate = FlashImageViewManagerDelegate(this)
  }

  override fun getDelegate(): ViewManagerDelegate<T>? {
    return mDelegate
  }
}
