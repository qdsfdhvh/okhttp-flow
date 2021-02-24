package com.seiko.net.param

import com.seiko.net.NetHttp
import com.seiko.net.model.KeyValue
import com.seiko.net.util.addQueryParameters
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request

fun NetHttp.get(url: String) = GetParamFlowHttp(this, url)

class GetParamFlowHttp internal constructor(
  netHttp: NetHttp,
  private val url: String,
) : AbsHeaderParamNetHttp<GetParamFlowHttp>(netHttp) {

  private val queryParams = hashSetOf<KeyValue>()

  fun add(key: String, value: Any) = apply {
    add(key, value, false)
  }

  fun addEncode(key: String, value: Any) = apply {
    add(key, value, true)
  }

  private fun add(key: String, value: Any, encode: Boolean) {
    queryParams.add(KeyValue(key, value, encode))
  }

  override fun buildRequest(): Request {
    var httpUrl = wrapperUrl(url).toHttpUrl()
    if (queryParams.isNotEmpty()) {
      httpUrl = httpUrl.newBuilder()
        .addQueryParameters(queryParams)
        .build()
    }
    return Request.Builder()
      .url(httpUrl)
      .get()
      .headers(buildHeaders())
      .build()
  }
}