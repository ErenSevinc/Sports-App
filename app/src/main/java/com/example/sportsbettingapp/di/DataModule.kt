package com.example.sportsbettingapp.di

import com.example.sportsbettingapp.network.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.internal.schedulers.RxThreadFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    private const val BASE_URL = "https://api.the-odds-api.com/v4/"
    private const val API_KEY = "1b5d935ce8a722b9e49ae87706a6cdbf"

    //Loglamayı sağlar
    @Provides
    @Singleton
    fun provideLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
    }

    //rest istekleri atmamızı sağlar
    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    //rest sonucunda dönen json u çevirmemizi sağlar
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    //retrofit üzerinden verilere ulaşmamızı sağlar
    @Provides
    @Singleton
    fun provideApiClient(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }


    // retrofit i interface e bağlar
    @Provides
    @Singleton
    fun provideApiService (retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }


}