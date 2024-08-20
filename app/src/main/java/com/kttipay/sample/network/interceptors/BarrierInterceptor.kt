package com.kttipay.sample.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class BarrierInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        //TODO:: add custom header auth header etc...

        return chain.proceed(request)
    }

}