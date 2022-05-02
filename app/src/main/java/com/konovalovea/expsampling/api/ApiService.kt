package com.konovalovea.expsampling.api

import com.google.gson.GsonBuilder
import com.konovalovea.expsampling.api.entities.AnswerEntity
import com.konovalovea.expsampling.api.entities.Project
import com.konovalovea.expsampling.api.entities.RecordEntity
import com.konovalovea.expsampling.api.entities.SignInResult
import io.reactivex.rxjava3.core.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.*

interface ApiService {

    @POST("ParticipantAuth/Login")
    fun signIn(
        @Query("id") userId: String
    ): SignInResult?

    @POST("ParticipantAuth/Login")
    fun getProject(
        @Query("id") userId: String
    ): Project?

    @GET("Participant/GetQuestions")
    fun getQuestions(
        @Query("token") token: String
    ): RecordEntity?

    @POST("Participant/SendAnswer")
    fun sendAnswer(
        @Body answerEntity: AnswerEntity,
        @Query("token") token: String
    )
}


object Api {

    private const val BASE_URL = "https://psycho.sudox.ru/api/"

    val service: ApiService by lazy {
        val client = OkHttpClient.Builder().apply {
            addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()

        val retrofit = Retrofit.Builder()
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
        retrofit.create(ApiService::class.java)
    }
}