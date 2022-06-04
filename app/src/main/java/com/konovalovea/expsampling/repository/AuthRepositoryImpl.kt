package com.konovalovea.expsampling.repository

import com.konovalovea.expsampling.api.ApiService
import com.konovalovea.expsampling.api.entities.SignInResult
import com.konovalovea.expsampling.model.ReminderAlarmManager
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import kotlin.time.ExperimentalTime

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val tokenService: TokenService,
    private val preferenceService: PreferenceService,
    private val reminderAlarmManager: ReminderAlarmManager
): AuthRepository {

    //@ExperimentalTime
    @OptIn(ExperimentalTime::class)
    override fun signInWithId(userId: String): Single<SignInResult> =
        Single.create<SignInResult> {
            val result = apiService.signIn(userId)
            if (result == null) {
                it.onError(NullPointerException())
            } else {
                tokenService.saveToken(result.token)
                preferenceService.saveCode(userId)
                reminderAlarmManager.setNotificationsForTimeInterval(result)
                it.onSuccess(result)
            }
        }
}