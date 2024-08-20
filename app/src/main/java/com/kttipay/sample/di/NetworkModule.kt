package com.kttipay.sample.di

import android.content.Context
import com.kttipay.sample.network.interceptors.LiveNetworkMonitor
import com.kttipay.sample.network.MovieAPIService
import com.kttipay.sample.network.interceptors.BarrierInterceptor
import com.kttipay.sample.network.interceptors.NetworkMonitor
import com.kttipay.sample.network.interceptors.NetworkMonitorInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideNetworkMonitor(@ApplicationContext appContext: Context): NetworkMonitor {
        return LiveNetworkMonitor(appContext)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(liveNetwork: LiveNetworkMonitor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(NetworkMonitorInterceptor(liveNetwork))
            .addInterceptor(HttpLoggingInterceptor())
            .addInterceptor(BarrierInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://6471a6946a9370d5a41a84bb.mockapi.io/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieAPIService(retrofit: Retrofit): MovieAPIService {
        return retrofit.create(MovieAPIService::class.java)
    }

}