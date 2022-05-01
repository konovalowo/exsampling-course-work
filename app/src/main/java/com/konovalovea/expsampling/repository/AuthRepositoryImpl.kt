package com.konovalovea.expsampling.repository

import android.util.Log
import com.konovalovea.expsampling.api.Api
import com.konovalovea.expsampling.api.entities.SignInResult
import com.konovalovea.expsampling.app.GlobalDependencies
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.lang.NullPointerException
import kotlin.time.ExperimentalTime

class AuthRepositoryImpl : AuthRepository {

    @ExperimentalTime
    override fun signInWithId(userId: String): Single<SignInResult> {

        return Single.create<SignInResult> {
            val res = Api.service.signIn(userId)
            if (res == null) {
                it.onError(NullPointerException())
            } else {
                it.onSuccess(res)
            }
        }
//        return try {
//            val signInResult = Api.service.signIn(userId)
//            signInResult?.let {
//                GlobalDependencies.INSTANCE.tokenService.saveToken(it.token)
//                GlobalDependencies.INSTANCE.preferenceService.saveCode(userId)
//                GlobalDependencies.INSTANCE.reminderAlarmManger.setNotificationsForTimeInterval(it)
//            }
//            signInResult
//        } catch (e: Exception) {
//            Log.w("AuthRepositoryImpl", e)
//            null
//        }
    }
}