package com.flashimage

import android.graphics.drawable.Animatable
import android.os.Build.VERSION.SDK_INT
import android.util.Log
import android.widget.ImageView.ScaleType
import coil3.ImageLoader
import coil3.asDrawable
import coil3.dispose
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.request.crossfade
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.common.MapBuilder
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import com.flashimage.decoder.AnimatedPngDecoder


@ReactModule(name = FlashImageViewManager.NAME)
class FlashImageViewManager : FlashImageViewManagerSpec<FlashImageView>() {
  private var imageLoader: ImageLoader? = null  // Track the ImageLoader
  override fun getName(): String {
    return NAME
  }

  override fun createViewInstance(reactContext: ThemedReactContext): FlashImageView {
    return FlashImageView(reactContext)
  }

  override fun onDropViewInstance(view: FlashImageView) {
    super.onDropViewInstance(view)
    view.dispose()
    imageLoader?.shutdown()
    imageLoader = null
  }

  override fun onAfterUpdateTransaction(view: FlashImageView) {
    super.onAfterUpdateTransaction(view)
    loadImage(view)
  }

  override fun getExportedCustomBubblingEventTypeConstants(): MutableMap<String, Any>? {
    return MapBuilder.of(
      "onSuccess", MapBuilder.of("registrationName", "onSuccess"),
      "onError", MapBuilder.of("registrationName", "onError"),
    )
  }

  @ReactProp(name = "source")
  override fun setSource(view: FlashImageView, options: ReadableMap) {
    view.uri = options.getString("uri")

  }

  @ReactProp(name = "allowHardware")
  override fun setAllowHardware(view: FlashImageView, allowHardware: Boolean?) {
    view.allowHardware = allowHardware ?: false
  }

  @ReactProp(name = "contentFit")
  override fun setContentFit(view: FlashImageView, contentFit: String?) {
    if (RESIZE_MODE.containsKey(contentFit)) {
      view.scaleType = RESIZE_MODE[contentFit]
    } else {
      view.scaleType = ScaleType.FIT_CENTER
    }
  }

  @ReactProp(name = "headers")
  override fun setHeaders(view: FlashImageView, headers: ReadableMap?) {
    headers.apply {
      view.headers = headers
    }
  }

  @ReactProp(name = "cachePolicy")
  override fun setCachePolicy(view: FlashImageView, cachePolicy: String?) {
    view.cachePolicy = cachePolicy ?: "discWithCacheControl"
  }

  @ReactProp(name = "autoPlayGif")
  override fun setAutoPlayGif(view: FlashImageView, autoPlayGif: Boolean?) {
    view.autoPlayGif = autoPlayGif ?: false
  }

  @ReactProp(name = "tint")
  override fun setTint(view: FlashImageView, tint: Int?) {
    if (tint != null) {
      view.tint = tint
    }
  }

  private fun loadImage(flashImageView: FlashImageView) {
    val context = flashImageView.context
    val cachePolicy = flashImageView.cachePolicy
    val allowHardware = flashImageView.allowHardware
    val autoPlayGif = flashImageView.autoPlayGif
    val headersBuilder = NetworkHeaders.Builder()
    val transitionDuration = flashImageView.transitionDuration ?: 100
    flashImageView.headers?.let {
      for (entry in it.entryIterator) {
        headersBuilder[entry.key] = entry.value as String
      }
    }
    val imageRequest = ImageRequest.Builder(context)
      .data(flashImageView.uri)
      .httpHeaders(headersBuilder.build())
      .crossfade(transitionDuration)
//      .transformations(RoundedCornersTransformation(20f,20f,20f,20f))
      .target(
        onSuccess = { result ->
          val drawable = result.asDrawable(flashImageView.resources)
          flashImageView.setImageDrawable(drawable)
          if (autoPlayGif && drawable is Animatable) {
            drawable.start()
          }
        },
        onError = { _ ->
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
    imageLoader = ImageLoader.Builder(context).apply {
      if (autoPlayGif) {
        components {
          if (SDK_INT >= 28) {
            add(AnimatedImageDecoder.Factory())
          } else {
            add(GifDecoder.Factory())
          }
          add(AnimatedPngDecoder.Factory())
        }
      }
    }.build()
    imageLoader!!.enqueue(imageRequest)
  }

//  private fun setViewBorderRadius(view: FlashImageView, borderRadii: BorderRadii) {
//    view.clipToOutline = true
//    view.outlineProvider = object : ViewOutlineProvider() {
//      override fun getOutline(view: View, outline: Outline) {
//        val width = view.width
//        val height = view.height
//        val nonUniformRadiiSum = borderRadii.sum() - borderRadii.uniform
//
//        if (nonUniformRadiiSum == 0.0 || nonUniformRadiiSum == borderRadii.uniform) {
//          outline.setRoundRect(0, 0, width, height, borderRadii.uniform.toFloat())
//          return
//        }
//
//        val radii = floatArrayOf(
//          borderRadii.topLeft.toFloat(), borderRadii.topLeft.toFloat(),
//          borderRadii.topRight.toFloat(), borderRadii.topRight.toFloat(),
//          borderRadii.bottomRight.toFloat(), borderRadii.bottomRight.toFloat(),
//          borderRadii.bottomLeft.toFloat(), borderRadii.bottomLeft.toFloat(),
//        )
//        val rect = Rect(0, 0, width, height)
//        val path = Path().apply {
//          addRoundRect(
//            RectF(rect),
//            radii,
//            Path.Direction.CW
//          )
//        }
//        if (SDK_INT >= Build.VERSION_CODES.R) {
//          outline.setPath(path)
//        } else {
//          outline.setRoundRect(rect, borderRadii.sum().toFloat())
//        }
//      }
//    }
//  }

  companion object {
    const val NAME = "FlashImageView"
    private val RESIZE_MODE = mapOf(
      "contain" to ScaleType.FIT_CENTER,
      "cover" to ScaleType.CENTER_CROP,
      "stretch" to ScaleType.FIT_XY,
      "center" to ScaleType.CENTER_INSIDE
    )
  }
}
