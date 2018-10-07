package br.com.adalbertofjr.topgames.data.api

import br.com.adalbertofjr.topgames.root.App
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * TwitchApiModule
 * Created by Adalberto Fernandes Júnior on 06/10/2018.
 * Copyright © 2018. All rights reserved.
 */
@Module
class TwitchApiModule(val application: App){

    val BASE_URL = "https://api.twitch.tv/kraken/"

    @Provides
    fun provideClient(): OkHttpClient {

        val interceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    fun provideRetrofit(baserURL: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baserURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }


    @Provides
    fun provideTwitchApiService(): TwitchAPI {
        return provideRetrofit(BASE_URL, provideClient()).create(TwitchAPI::class.java)
    }

}