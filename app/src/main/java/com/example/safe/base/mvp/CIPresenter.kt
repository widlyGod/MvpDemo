package com.example.safe.base.mvp

import com.xmssx.common.mvp.CIView

/**
 * Created by hy on 2019/10/30
 */
interface CIPresenter {

    /**
     * 做一些初始化操作
     */
    fun onStart()

    /**
     * 在框架中 [Activity.onDestroy] 时会默认调用 [CIPresenter.onDestroy]
     */
    fun onDestroy()

    /**
     * 关联view
     */
    fun setView(view: CIView)
}