package com.example.hnhapp.di


import com.example.hnhapp.data.HnHService
import com.example.hnhapp.data.interceptor.HeaderInterceptor
import com.example.hnhapp.data.repository.SharedPreferencesReq
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    companion object{
        private const val BASE_URL = "http://45.144.64.179/cowboys/api/"
    }
    @Provides
    fun provideOkHttp(
        preferenceStorage: SharedPreferencesReq
    ) = OkHttpClient.Builder().apply {
        addInterceptor(HeaderInterceptor(preferenceStorage = preferenceStorage))
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        addInterceptor(loggingInterceptor)
    }
        .connectTimeout(20000L, TimeUnit.MILLISECONDS)
        .readTimeout(20000L, TimeUnit.MILLISECONDS)
        .writeTimeout(20000L, TimeUnit.MILLISECONDS)
        .build()

    @Provides
    fun provideRetrofit(
        httpClient: OkHttpClient,
        gson: Gson
    ) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient)
        .build()



    @Provides
    fun provideGson():Gson = GsonBuilder().create()

    @Provides
    fun provideHnHService(retrofit: Retrofit): HnHService = retrofit.create(HnHService::class.java)
}