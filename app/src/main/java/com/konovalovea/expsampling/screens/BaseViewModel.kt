package com.konovalovea.expsampling.screens

import io.reactivex.rxjava3.disposables.CompositeDisposable

interface BaseViewModel {
    val compositeDisposable: CompositeDisposable
        get() = CompositeDisposable()
}