package com.konovalovea.expsampling.screens.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.konovalovea.expsampling.repository.AuthRepository
import com.konovalovea.expsampling.screens.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel(), BaseViewModel {

    private val _authResult: MutableLiveData<AuthResult?> = MutableLiveData(null)
    val authResult: LiveData<AuthResult?> get() = _authResult

    fun onSignInButtonClick(userId: String) {
        compositeDisposable.add(
            authRepository.signInWithId(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { _authResult.value = AuthResult(true, it.token) },
                    { _authResult.value = AuthResult(false, null) }
                )
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}