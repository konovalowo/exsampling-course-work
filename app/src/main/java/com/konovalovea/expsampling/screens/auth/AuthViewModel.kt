package com.konovalovea.expsampling.screens.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konovalovea.expsampling.repository.AuthRepository
import com.konovalovea.expsampling.repository.AuthRepositoryImpl
import com.konovalovea.expsampling.screens.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel(), BaseViewModel {

    private val authRepository: AuthRepository = AuthRepositoryImpl()

    private val _authResult: MutableLiveData<AuthResult?> = MutableLiveData(null)
    val authResult: LiveData<AuthResult?> get() = _authResult

    //    fun onSignInButtonClick(userId: String) {
//        viewModelScope.launch {
//            val signInResult = authRepository.signInWithId(userId)
//            val token = signInResult?.token
//            _authResult.value = AuthResult(token != null, token)
//        }
//    }
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