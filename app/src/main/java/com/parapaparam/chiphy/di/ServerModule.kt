package com.parapaparam.chiphy.di

import com.google.gson.Gson
import com.parapaparam.chiphy.BuildConfig
import com.parapaparam.chiphy.network.ApiKeyInterceptor
import com.parapaparam.chiphy.network.GiphyService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class ServerModule constructor(
        private val url: String
) {

    @Provides
    @Singleton
    fun provideGiphyService(okHttpClient: OkHttpClient, gson: Gson): GiphyService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(url)
            .build()
            .create(GiphyService::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = with(OkHttpClient.Builder()) {
        addNetworkInterceptor(ApiKeyInterceptor())
        addNetworkInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                }
        )

        build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()
}