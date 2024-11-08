package com.flashimage

import android.annotation.SuppressLint
import androidx.appcompat.widget.AppCompatImageView
import coil3.size.Size
import coil3.transform.CircleCropTransformation
import coil3.transform.Transformation
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.uimanager.ThemedReactContext


@SuppressLint("ViewConstructor")
class FlashImageView(reactContext: ThemedReactContext) :
  AppCompatImageView(reactContext) {
  var uri: String? = null
  var headers: ReadableMap? = null
  var cachePolicy: String? = null
  var transitionDuration: Int? = null
  var resize: Size? = null
  var rounded: Boolean? = null
  var monochrome: Int? = null
  var tint: Int? = null
  var allowHardware: Boolean = false
  var autoPlayGif:Boolean=false

  val transformations: MutableList<Transformation>
    get() {
      val list = mutableListOf<Transformation>()

      rounded?.let {
        list.add(CircleCropTransformation())
      }

//      blur?.let {
//        list.add(BlurTransformation(reactContext, it.toFloat()))
//      }

//      monochrome?.let {
//        list.add(MonochromeTransformation(it))
//      }

      tint?.let {
//        list.add(ColorFilterTransformation(it))
      }

      return list
    }

  }
