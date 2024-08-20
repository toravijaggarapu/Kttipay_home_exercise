package com.kttipay.sample.network.interceptors

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

interface NetworkMonitor {
    fun isConnected(): Boolean
}

class LiveNetworkMonitor @Inject constructor(context: Context) : NetworkMonitor {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun isConnected(): Boolean {
        val network = connectivityManager.activeNetwork
        return network != null
    }
}