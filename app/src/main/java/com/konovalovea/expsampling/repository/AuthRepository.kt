package com.konovalovea.expsampling.repository

import com.konovalovea.expsampling.api.entities.SignInResult
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface AuthRepository {

    fun signInWithId(userId: String): Single<SignInResult>
}