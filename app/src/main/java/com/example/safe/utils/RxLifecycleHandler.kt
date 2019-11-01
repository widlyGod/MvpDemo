package com.example.safe.utils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by hy on 2019/10/30
 */
open class RxLifecycleHandler : IRxLifecycleProvider {

    private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    @Synchronized
    fun clear() {
        mCompositeDisposable.clear()
    }

    @Synchronized
    fun size() = mCompositeDisposable.size()

    @Synchronized
    override fun bindToLifecycle(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }

    @Synchronized
    override fun removeFromLifecycle(disposable: Disposable) {
        mCompositeDisposable.remove(disposable)
    }
}