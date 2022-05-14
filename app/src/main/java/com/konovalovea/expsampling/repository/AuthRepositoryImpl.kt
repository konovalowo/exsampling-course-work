package com.konovalovea.expsampling.repository

import com.konovalovea.expsampling.api.Api
import com.konovalovea.expsampling.api.entities.SignInResult
import io.reactivex.rxjava3.core.Single
import kotlin.time.ExperimentalTime

class AuthRepositoryImpl : AuthRepository {

    @ExperimentalTime
    override fun signInWithId(userId: String): Single<SignInResult> =
        Single.create<SignInResult> {
            val result = Api.service.signIn(userId)
            if (result == null) {
                it.onError(NullPointerException())
            } else {
                it.onSuccess(result)
            }
        }
}