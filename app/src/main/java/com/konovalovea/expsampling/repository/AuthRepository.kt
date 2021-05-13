package com.konovalovea.expsampling.repository

import com.konovalovea.expsampling.api.entities.SignInResult

interface AuthRepository {

    suspend fun signInWithId(userId: String): SignInResult?
}