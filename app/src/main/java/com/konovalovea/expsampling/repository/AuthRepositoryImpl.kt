package com.konovalovea.expsampling.repository

import android.util.Log
import com.konovalovea.expsampling.api.ApiService
import com.konovalovea.expsampling.api.entities.SignInResult
import com.konovalovea.expsampling.model.ReminderAlarmManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import kotlin.time.ExperimentalTime

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val tokenService: TokenService,
    private val preferenceService: PreferenceService,
    private val reminderAlarmManager: ReminderAlarmManager
): AuthRepository {

    @OptIn(ExperimentalTime::class)
     override fun signInWithId(userId: String): Single<SignInResult> =
        Single.create<SignInResult> {
            val result = apiService.signIn(userId)
            if (result == null) {
                it.onError(NullPointerException())
            } else {
                Log.d("TAG_SIGN", "$userId ${result.token}: ")
                tokenService.saveToken(result.token)
                preferenceService.saveCode(userId)
                reminderAlarmManager.setNotificationsForTimeInterval(result)
                it.onSuccess(result)
            }
        }

//    @OptIn(ExperimentalTime::class)
//    override fun signInWithId(userId: String): Single<SignInResult> {
//
//        return apiService.signIn(userId)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .onErrorResumeWith { Log.d("TAG_LODA", "signInWithId: ") }
////            .map {
////                tokenService.saveToken(it.token)
////                preferenceService.saveCode(userId)
////                reminderAlarmManager.setNotificationsForTimeInterval(it)
////                it
////            }
//    }
}