package com.example.safe.utils

import io.reactivex.disposables.Disposable

/**
 * Created by hy on 2019/10/30
 */
interface IRxLifecycleProvider {

    /**
     * 绑定到生命周期
     */
    fun bindToLifecycle(disposable: Disposable)

    fun removeFromLifecycle(disposable: Disposable)
}