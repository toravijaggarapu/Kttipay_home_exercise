package com.kttipay.sample.network.interceptors

import com.kttipay.sample.BuildConfig
import com.kttipay.sample.network.NetworkConstants.API_KEY
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class BarrierInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
        val updatedURL = chain.request().url.newBuilder().addQueryParameter(API_KEY, BuildConfig.API_KEY).build()
        newRequest.url(updatedURL)
        return chain.proceed(newRequest.build())
    }
}