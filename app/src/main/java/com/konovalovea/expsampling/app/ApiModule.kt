package com.konovalovea.expsampling.app

import com.google.gson.GsonBuilder
import com.konovalovea.expsampling.api.ApiService
import com.konovalovea.expsampling.api.ApiServiceStub
import com.konovalovea.expsampling.api.DateDeserializer
import com.konovalovea.expsampling.api.QuestionsDeserializer
import com.konovalovea.expsampling.api.entities.RecordEntity
import com.konovalovea.expsampling.app.ApiModule_ApiServiceFactory.apiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

@Module
open class ApiModule {

    @Provides
    open fun apiService(): ApiService {
        return if (USE_STUB) {
            ApiServiceStub()
        } else {
            val client = OkHttpClient.Builder().apply {
                addNetworkInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }.build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder()
                            .registerTypeAdapter(RecordEntity::class.java, QuestionsDeserializer())
                            .registerTypeAdapter(Date::class.java, DateDeserializer())
                            .create()
                    )
                )
                .build()
                .create(ApiService::class.java)
        }
    }

    companion object {
        const val BASE_URL = "https://psycho.sudox.ru/api/"
        const val USE_STUB = true
    }
}