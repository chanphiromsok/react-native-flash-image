package com.flashimage

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager

class FlashImageViewPackage : ReactPackage {
  override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
//    val viewManagers: MutableList<ViewManager<*, *>> = ArrayList()
//    viewManagers.add(FlashImageViewManager())
    return listOf(FlashImageViewManager())
  }

  override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> {
    return listOf(FastImageModule(reactContext))
  }
}
