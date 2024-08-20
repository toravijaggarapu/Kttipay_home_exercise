package com.kttipay.sample.di

import com.kttipay.sample.BuildConfig
import com.kttipay.sample.network.MovieAPIService
import com.kttipay.sample.network.interceptors.BarrierInterceptor
import com.kttipay.sample.network.interceptors.NetworkMonitorInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    @Singleton
    fun provideOkHttpClient(
        barrierInterceptor: BarrierInterceptor,
        networkMonitorInterceptor: NetworkMonitorInterceptor
    ): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(barrierInterceptor)
            .addInterceptor(networkMonitorInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
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