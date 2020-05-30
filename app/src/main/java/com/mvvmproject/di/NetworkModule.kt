package com.mvvmproject.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.mvvmproject.rest.StoriesApiInterface
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val HTTP_TIME_OUT = 15
private const val BASE_URL = "https://hacker-news.firebaseio.com/v0/"
@Module
class NetworkModule {

    @Provides
    fun stethoInterceptor() = StethoInterceptor()

    @Singleton
    @Provides
    fun provideHTTPClient(stethoInterceptor: StethoInterceptor) = OkHttpClient.Builder()
            .addNetworkInterceptor(stethoInterceptor)
            .connectTimeout(HTTP_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(HTTP_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .build()

    @Provides
    fun provideRetrofitObject(httpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    fun provideStoriesApiClient(retrofit: Retrofit)= retrofit.create(StoriesApiInterface::class.java)
}