package com.seiko.net.param

import com.seiko.net.NetHttp
import okhttp3.Request

class HeadParamNetHttp internal constructor(
  netHttp: NetHttp,
  private val url: String,
) : AbsHeaderParamNetHttp<HeadParamNetHttp>(netHttp) {
  override fun buildRequest(): Request {
    return Request.Builder()
      .url(buildHttpUrl(url))
      .head()
      .headers(buildHeaders())
      .build()
  }
}

fun NetHttp.head(url: String): HeadParamNetHttp {
  return HeadParamNetHttp(this, url)
}