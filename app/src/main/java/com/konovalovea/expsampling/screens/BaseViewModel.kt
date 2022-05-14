package com.konovalovea.expsampling.screens

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

interface BaseViewModel {
    val compositeDisposable: CompositeDisposable
        get() = CompositeDisposable()
}