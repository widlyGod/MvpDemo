package com.example.safe.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.example.safe.base.mvp.CIModel

/**
 * Created by hy on 2019/10/30
 */
abstract class CBaseModel : CIModel, LifecycleObserver {

    /**
     * 在框架中 [CBasePresenter.onDestroy] 时会默认调用 [CIModel.onDestroy]
     */
    override fun onDestroy() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    internal fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
    }
}