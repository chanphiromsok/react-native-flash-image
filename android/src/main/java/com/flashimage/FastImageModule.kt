package com.flashimage


import android.content.Context
import android.os.Build.VERSION.SDK_INT
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.decode.BlackholeDecoder
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import com.facebook.react.bridge.*
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.flashimage.decoder.AnimatedPngDecoder
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.Headers

class FastImageModule(private val context: ReactApplicationContext) :
  ReactContextBaseJavaModule(context) {
  private lateinit var imageLoader : ImageLoader

  override fun getName(): String = REACT_CLASS

  /**
   * cachePolicy=discWithCacheControl preload to the disk cache you can skip decoding and saving to the memory cache else preload the image and save it to the disk and memory caches.
   */
  @OptIn(ExperimentalCoilApi::class)
  @ReactMethod
  fun prefetchImage(uri: String, cachePolicy: String,headers:ReadableMap?, promise: Promise) {
    if (!::imageLoader.isInitialized) {
      imageLoader = createImageLoader(context)
    }
    val request = ImageRequest.Builder(context)
      .data(uri)
      .apply {
        if (cachePolicy == "discWithCacheControl") {
          memoryCachePolicy(CachePolicy.DISABLED) // Disables writing to memory cache.
          decoderFactory(BlackholeDecoder.Factory()) // Skips decoding.
        }
        if(headers != null){
          val headersBuilder = NetworkHeaders.Builder()
            for (entry in headers.entryIterator) {
              headersBuilder[entry.key] = entry.value as String
            }
          httpHeaders(headersBuilder.build())
        }
      }
      .listener(
        onSuccess = { _, _ ->
          promise.resolve(uri)
        },
        onError = { _, _ ->
          promise.resolve(false)
        }
      )
      .build()
    imageLoader.enqueue(request)
  }

  suspend fun preloadImages(urls: List<String>) {
    val imageLoader = ImageLoader.Builder(context).build()
    coroutineScope {
      urls.map { url ->
        async {
          imageLoader.execute(
            ImageRequest.Builder(context)
              .data(url)
              .diskCachePolicy(CachePolicy.ENABLED) // Cache on disk for future use
              .build()
          )
        }
      }.awaitAll() // Waits for all images to preload
    }
  }

private fun createImageLoader(context: Context): ImageLoader {
  return ImageLoader.Builder(context).apply {
    components {
      if (SDK_INT >= 28) add(AnimatedImageDecoder.Factory())
      else add(GifDecoder.Factory())
      add(AnimatedPngDecoder.Factory())
    }
  }.build()
}

  companion object {
    private const val REACT_CLASS = "FlashImageModule"
  }
}
