package com.mvvmproject.di

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.mvvmproject.helperclasses.NetworkConnectivityInterceptor
import com.mvvmproject.rest.NewsApiInterface
import com.mvvmproject.rest.StoriesApiInterface
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val HTTP_TIME_OUT = 15
private const val BASE_URL = "https://hacker-news.firebaseio.com/v0/"
private const val NEWS_API_BASE_URL = "https://newsapi.org/v2/"
@Module
object NetworkModule {

    @Provides
    @JvmStatic
    fun stethoInterceptor() = StethoInterceptor()

    @Singleton
    @Provides
    @JvmStatic
    fun provideHTTPClient(stethoInterceptor: StethoInterceptor, context: Context) =
        OkHttpClient.Builder()
            .addNetworkInterceptor(stethoInterceptor)
            .addInterceptor(NetworkConnectivityInterceptor(context))
            .connectTimeout(HTTP_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(HTTP_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .build()

    @Provides
    @Reusable
    @JvmStatic
    fun provideRetrofitObject(httpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(NEWS_API_BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Reusable
    @JvmStatic
    fun provideStoriesApiClient(retrofit: Retrofit)= retrofit.create(StoriesApiInterface::class.java)

    @Provides
    @Reusable
    @JvmStatic
    fun provideNewsApiClient(retrofit: Retrofit)= retrofit.create(NewsApiInterface::class.java)
}