package com.flashimage


import coil3.annotation.ExperimentalCoilApi
import coil3.decode.BlackholeDecoder
import coil3.imageLoader
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap

class FastImageModule(private val context: ReactApplicationContext) :
  ReactContextBaseJavaModule(context) {

  override fun getName(): String = REACT_CLASS

  /**
   * cachePolicy=discWithCacheControl preload to the disk cache you can skip decoding and saving to the memory cache else preload the image and save it to the disk and memory caches.
   */
  @OptIn(ExperimentalCoilApi::class)
  @ReactMethod
  fun prefetchImage(uri: String, cachePolicy: String,headers:ReadableMap?, promise: Promise) {

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
    reactApplicationContext.imageLoader.enqueue(request)
  }

  @ReactMethod()
  fun clearCache(promise:Promise){
    val imageLoader = reactApplicationContext.imageLoader
    imageLoader.diskCache?.clear()
    imageLoader.memoryCache?.clear()
    promise.resolve(true)
  }

  @ReactMethod
  fun prefetch(sources: ReadableArray,headers:ReadableMap?,promise: Promise) {
    val imageLoader = reactApplicationContext.imageLoader
    val requests = sources.toArrayList().map { url ->
      ImageRequest.Builder(reactApplicationContext)
        .data(url as String)
        .apply {
          if(headers != null){
            val headersBuilder = NetworkHeaders.Builder()
            for (entry in headers.entryIterator) {
              headersBuilder[entry.key] = entry.value as String
            }
            httpHeaders(headersBuilder.build())
          }
        }.build()
    }
    requests.forEach { imageLoader.enqueue(it) }
    promise.resolve(true)
  }

  companion object {
    private const val REACT_CLASS = "FlashImageModule"
  }
}
