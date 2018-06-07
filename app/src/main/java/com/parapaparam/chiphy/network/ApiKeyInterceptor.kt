package com.parapaparam.chiphy.network

import com.parapaparam.chiphy.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()
        val url = originalUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY_GIPHY)
                .build()

        val request = originalRequest.newBuilder()
                .url(url)
                .build()

        return chain.proceed(request)
    }
}