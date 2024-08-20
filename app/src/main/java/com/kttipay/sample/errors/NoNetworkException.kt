package com.kttipay.sample.errors

import java.io.IOException

class NoNetworkException(message:String = "No network available, please check your WiFi or Data connection."): IOException(message)