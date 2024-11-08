package com.flashimage

import android.graphics.Color
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp

import android.graphics.drawable.Animatable
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.util.Log
import android.widget.ImageView.ScaleType
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import coil3.ImageLoader
import coil3.asDrawable
import coil3.dispose
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.request.crossfade
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.uimanager.SimpleViewManager
import com.flashimage.decoder.AnimatedPngDecoder

//@ReactModule(name = FlashImageViewManager.NAME)
//class FlashImageViewManager :
//  FlashImageViewManagerSpec<FlashImageView>() {
//  override fun getName(): String {
//    return NAME
//  }
//
//  public override fun createViewInstance(context: ThemedReactContext): FlashImageView {
//    return FlashImageView(context)
//  }
//
//  @ReactProp(name = "color")
//  override fun setColor(view: FlashImageView?, color: String?) {
//    view?.setBackgroundColor(Color.parseColor(color))
//  }
//
//  companion object {
//    const val NAME = "FlashImageView"
//  }
//}
@ReactModule(name = FlashImageViewManager.NAME)
class FlashImageViewManager : FlashImageViewManagerSpec<AppCompatImageView>() {
    override fun getName(): String {
    return NAME
  }
  private lateinit var imageView: AppCompatImageView
  override fun createViewInstance(reactContext: ThemedReactContext): AppCompatImageView {
    imageView = AppCompatImageView(reactContext)
    return imageView
  }

  @RequiresApi(Build.VERSION_CODES.P)
  @ReactProp(name = "source")
  override fun setSource(view: AppCompatImageView, options: ReadableMap) {

    val url = options.getString("url")
    val autoPlayGif =
      if (options.hasKey("autoPlayGif")) options.getBoolean("autoPlayGif") else false
    val cachePolicy =if(options.hasKey("cachePolicy"))  options.getString("cachePolicy") else "discWithCacheControl"
    val allowHardware =
      if (options.hasKey("allowHardware")) options.getBoolean("allowHardware") else false
    view.scaleType = ScaleType.FIT_CENTER
    val context = view.context
    val imageRequest = ImageRequest.Builder(context)
      .data(url)
      .crossfade(true)
      .target(
        onSuccess = { result ->
          val drawable = result.asDrawable(view.resources)
          view.setImageDrawable(drawable)
          // Check if the drawable is animatable and start animation if it is
          if (autoPlayGif && drawable is Animatable) {
            drawable.start()  // Start GIF animation if autoPlayGif is true
          }
        },
        onError = { error ->
          Log.d("ImageRequest", "Error")
        }
      )
      .memoryCachePolicy(
        when (cachePolicy) {
          "memory", "memoryAndDisc" -> CachePolicy.ENABLED
          else -> CachePolicy.DISABLED
        }
      )
      .diskCachePolicy(
        when (cachePolicy) {
          "discWithCacheControl", "discNoCacheControl", "memoryAndDisc" -> CachePolicy.ENABLED
          else -> CachePolicy.DISABLED
        }
      )
      .allowHardware(allowHardware)
      .build()

    val imageLoader = ImageLoader.Builder(context).apply {
      if (autoPlayGif) {
        components {
          // Play Gif
          if (SDK_INT >= 28) {
            add(AnimatedImageDecoder.Factory())
          } else {
            add(GifDecoder.Factory())
          }
          // Animated Png
          add(AnimatedPngDecoder.Factory())
        }
      }
    }.build()
    Log.d(NAME, view.id.toString())
    imageLoader.enqueue(imageRequest)
  }

  override fun onDropViewInstance(view: AppCompatImageView) {
    super.onDropViewInstance(view)
    view.dispose()
  }

  companion object {
    const val NAME = "FlashImageView"
  }
}
