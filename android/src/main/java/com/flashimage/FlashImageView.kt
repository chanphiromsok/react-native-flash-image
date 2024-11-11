package com.flashimage

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import coil3.dispose
import coil3.imageLoader
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableMap
import com.facebook.react.uimanager.UIManagerHelper
import com.facebook.react.uimanager.events.Event

@SuppressLint("ViewConstructor")
class FlashImageView(context: Context) : AppCompatImageView(context) {
  var uri: String? = null
  var headers: ReadableMap? = null
  var cachePolicy: String? = null
  var transitionDuration: Int? = null
  var tint: Int? = null
  var allowHardware: Boolean = true
  var autoPlayGif: Boolean = false
  var recyclingKey: String? = null
  override fun draw(canvas: Canvas) {
    // When the border-radii are not all the same, a convex-path
    // is used for the Outline. Unfortunately clipping is not supported
    // for convex-paths and we fallback to Canvas clipping.
//    outlineProvider.clipCanvasIfNeeded(canvas, this)
    // If we encounter a recycled bitmap here, it suggests an issue where we may have failed to
    // finish clearing the image bitmap before the UI attempts to display it.
    // One solution could be to suppress the error and assume that the second image view is currently responsible for displaying the correct view.
    if ((drawable as? BitmapDrawable)?.bitmap?.isRecycled == true) {
      Log.e("ExpoImage", "Trying to use a recycled bitmap")
      recycleView()
    }
    super.draw(canvas)
  }
  fun recycleView() {
    // Check if drawable is an instance of BitmapDrawable
    val bitmapDrawable = drawable as? BitmapDrawable
    // Check if bitmap is not null and not already recycled
    if (bitmapDrawable?.bitmap != null && !bitmapDrawable.bitmap.isRecycled) {
      bitmapDrawable.bitmap.recycle() // Recycle the bitmap
    }
    dispose()
    setImageDrawable(null) // Clear the drawable reference
  }

  fun onSuccess(width: Int, height: Int) {
    dispatchEvent(EventName.OnSuccess, width, height)
  }

  fun onError(width: Int, height: Int) {
    dispatchEvent(EventName.OnError, width, height)
  }

  private fun dispatchEvent(eventName: EventName, width: Int, height: Int) {
    val reactContext = context as ReactContext
    val surfaceId = UIManagerHelper.getSurfaceId(reactContext)
    val eventDispatcher = UIManagerHelper.getEventDispatcherForReactTag(reactContext, id)
    val payload = Arguments.createMap().apply {
      putInt("width", width)
      putInt("height", height)
      putString("uri", uri)
    }
    val event = FlashImageEvent(surfaceId, id, payload, eventName)
    eventDispatcher?.dispatchEvent(event)
  }

  enum class EventName(val value: String) {
    OnSuccess("onSuccess"),
    OnError("onError")
  }

  inner class FlashImageEvent(
    surfaceId: Int,
    viewId: Int,
    private val payload: WritableMap,
    private val eventName: EventName
  ) : Event<FlashImageEvent>(surfaceId, viewId) {
    override fun getEventName() = eventName.value
    override fun getEventData() = payload
  }

}
