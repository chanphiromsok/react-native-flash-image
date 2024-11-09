package com.flashimage

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableMap
import com.facebook.react.uimanager.UIManagerHelper
import com.facebook.react.uimanager.events.Event

@SuppressLint("ViewConstructor")
class FlashImageView(context: Context) : AppCompatImageView(context) {
//  private val outlineProvider = OutlineProvider(context)
  var uri: String? = null
  var headers: ReadableMap? = null
  var cachePolicy: String? = null
  var transitionDuration: Int? = null
  var tint: Int? = null
  var allowHardware: Boolean = false
  var autoPlayGif: Boolean = false
  var recyclingKey: String? = null
//  private var currentTarget: FlashImageViewWrapper? = null

  override fun draw(canvas: Canvas) {
    if ((drawable as? BitmapDrawable)?.bitmap?.isRecycled == true) {
      Log.e("FlashImage", "Trying to use a recycled bitmap")
//      recycleView()?.let { target ->
//        (parent as? Flash)?.requestManager?.let { requestManager ->
//          target.clear(requestManager)
//        }
//      }
    }
    super.draw(canvas)
  }
  // Method to recycle the view and return the current target
//  fun recycleView(): FlashImageViewWrapper? {
//    setImageDrawable(null)
//
//    val target = currentTarget?.apply {
//      var isUsed = false // Mark as not used anymore
//    }
//
//    currentTarget = null // Clear the current target reference
//
//    return target // Return the recycled target for further handling if needed
//  }


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
