package com.konovalovea.expsampling.repository

import com.konovalovea.expsampling.api.entities.NotificationTime
import com.konovalovea.expsampling.api.entities.SignInResult
import com.konovalovea.expsampling.app.GlobalDependencies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.time.ExperimentalTime

class AuthRepositoryMock : AuthRepository {

    @ExperimentalTime
    override suspend fun signInWithId(userId: String): SignInResult? =  withContext(Dispatchers.IO) {
        if (userId == "1234") {
            val signInResult =  SignInResult(
                "token",
                NotificationTime(20,10,20 * 60 + 10),
                NotificationTime(20,40,20 * 60 + 40),
                30,
                20
            )
            signInResult.let {
                GlobalDependencies.INSTANCE.tokenService.saveToken(it.token)
                GlobalDependencies.INSTANCE.preferenceService.saveCode(userId)
                GlobalDependencies.INSTANCE.reminderAlarmManger.setNotificationsForTimeInterval(it)
            }
           signInResult
        } else {
            null
        }
    }
}