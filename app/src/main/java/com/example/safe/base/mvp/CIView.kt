package com.xmssx.common.mvp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.example.safe.utils.IRxLifecycleProvider


/**
 * Created by hy on 2019/10/30
 */
interface CIView : IRxLifecycleProvider, LifecycleOwner {

    /**
     * 是否处于活动状态 [Lifecycle.State.STARTED] 、[Lifecycle.State.RESUMED]
     */
    fun isActivityState(): Boolean = lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)

    /**
     * Activity 是否依赖注入，如果不需要，则重写此方法，返回 false
     *
     * @return true: 进行依赖注入；false:不进行依赖注入
     */
    fun injectable(): Boolean = true

    fun error(message:String)
}