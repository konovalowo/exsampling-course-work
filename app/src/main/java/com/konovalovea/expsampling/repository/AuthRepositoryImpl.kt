package com.konovalovea.expsampling.repository

import android.util.Log
import com.konovalovea.expsampling.api.Api
import com.konovalovea.expsampling.api.entities.SignInResult
import com.konovalovea.expsampling.app.GlobalDependencies
import java.lang.Exception
import kotlin.time.ExperimentalTime

class AuthRepositoryImpl : AuthRepository {

    @ExperimentalTime
    override suspend fun signInWithId(userId: String): SignInResult? {
        return try {
            val signInResult = Api.service.signIn(userId)
            signInResult?.let {
                GlobalDependencies.INSTANCE.tokenService.saveToken(it.token)
                GlobalDependencies.INSTANCE.preferenceService.saveCode(userId)
                GlobalDependencies.INSTANCE.reminderAlarmManger.setNotificationsForTimeInterval(it)
            }
            signInResult
        } catch (e: Exception) {
            Log.w("AuthRepositoryImpl", e)
            null
        }
    }
}