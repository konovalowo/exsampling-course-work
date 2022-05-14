package com.konovalovea.expsampling.api

import com.konovalovea.expsampling.api.entities.*
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

class ApiServiceStub : ApiService {

    override fun signIn(userId: String): SignInResult {
        return SignInResult(
            token = "1",
            notificationCountPerDay = 5,
            timeNotificationStart = NotificationTime(hours = 1, minutes = 20, totalMinutes = 30),
            timeNotificationEnd = NotificationTime(hours = 2, minutes = 20, totalMinutes = 30),
            notificationTimeout = 10
        )
    }

    override fun getProject(userId: String): Project {
        return Project(
            token = "1",
            dateStart = Date(),
            dateEnd = Date(),
            notificationCountPerDay = 5,
            timeNotificationStart = NotificationTime(hours = 1, minutes = 20, totalMinutes = 30),
            timeNotificationEnd = NotificationTime(hours = 2, minutes = 20, totalMinutes = 30),
            nickname = "Name",
            email = "email@email.com",
            phoneNumber = "88005553535"
        )
    }

    override fun getQuestions(token: String): RecordEntity {
        return RecordEntity(
            listOf(
                QuestionEntity(
                    id = 1,
                    questionType = "Choose",
                    questionText = "Вопрос",
                    questionSubtext = "С подвохом",
                    instructionText = null,
                    options = mutableListOf(
                        VerticalRadioGroupOptionEntity(
                            answers = listOf(
                                "Ответ 1",
                                "Ответ 2",
                                "Ответ 3"
                            )
                        )
                    )
                )
            )
        )
    }

    override fun sendAnswer(answerEntity: AnswerEntity, token: String) {
        return
    }
}