package com.kttipay.sample.network.interceptors

import com.kttipay.sample.errors.NoNetworkException
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class NetworkMonitorInterceptor @Inject constructor(private val liveNetworkMonitor: NetworkMonitor) : Interceptor {

    @Throws(NoNetworkException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        if (liveNetworkMonitor.isConnected()) {
            return chain.proceed(request)
        } else {
            throw NoNetworkException("Unable to connect to the Internet..!")
        }
    }
}