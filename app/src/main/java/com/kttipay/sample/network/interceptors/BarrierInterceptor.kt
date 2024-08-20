package com.kttipay.sample.network.interceptors

import com.kttipay.sample.BuildConfig
import com.kttipay.sample.network.NetworkConstants.AUTHORIZATION_KEY
import com.kttipay.sample.network.NetworkConstants.BEARER_KEY
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class BarrierInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader(AUTHORIZATION_KEY, "$BEARER_KEY ${BuildConfig.TOKEN}").build()
        return chain.proceed(newRequest)
    }
}