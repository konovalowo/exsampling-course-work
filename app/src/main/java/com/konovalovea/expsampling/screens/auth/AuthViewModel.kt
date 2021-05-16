package com.konovalovea.expsampling.screens.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konovalovea.expsampling.repository.AuthRepository
import com.konovalovea.expsampling.repository.AuthRepositoryImpl
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val authRepository: AuthRepository = AuthRepositoryImpl()

    private val _authResult: MutableLiveData<AuthResult?> = MutableLiveData(null)
    val authResult: LiveData<AuthResult?> get() = _authResult

    fun onSignInButtonClick(userId: String) {
        viewModelScope.launch {
            val signInResult = authRepository.signInWithId(userId)
            val token = signInResult?.token
            _authResult.value = AuthResult(token != null, token)
        }
    }
}