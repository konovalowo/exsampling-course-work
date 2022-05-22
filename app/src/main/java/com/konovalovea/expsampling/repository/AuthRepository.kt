package com.konovalovea.expsampling.repository

import com.konovalovea.expsampling.api.entities.SignInResult
import io.reactivex.rxjava3.core.Single
import kotlin.time.ExperimentalTime

interface AuthRepository {

    fun signInWithId(userId: String): Single<SignInResult>
}