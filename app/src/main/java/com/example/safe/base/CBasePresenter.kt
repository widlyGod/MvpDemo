package com.example.safe.base

import android.app.Activity
import android.app.Service
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.example.safe.base.mvp.CIModel
import com.example.safe.base.mvp.CIPresenter
import com.example.safe.utils.IRxLifecycleProvider
import com.xmssx.common.mvp.CIView
import io.reactivex.disposables.Disposable


/**
 * Created by hy on 2019/10/30
 */
abstract class BasePresenter<V : CIView, M : CIModel>(model: M? = null) : CIPresenter,
    LifecycleObserver,
    IRxLifecycleProvider {

    protected val TAG: String = javaClass.simpleName

    protected var mView: V? = null
    protected var mModel: M? = null

    init {
        mModel = model

        this.onStart()
    }

    @Suppress("UNCHECKED_CAST")
    final override fun setView(view: CIView) {
        mView = view as V
    }

    override fun onStart() {
        // 将 LifecycleObserver 注册给 LifecycleOwner 后 @OnLifecycleEvent 才可以正常使用
        mView?.let {
            //            if (it is LifecycleOwner) {
            it.lifecycle.addObserver(this)
            if (mModel != null && mModel is LifecycleObserver) {
                it.lifecycle.addObserver(mModel as LifecycleObserver)
            }
//            }
        }
    }

    /**
     * 在框架中 [Activity.onDestroy] 时会默认调用 [CIPresenter.onDestroy]
     */
    override fun onDestroy() {

        mModel?.onDestroy()

        mView = null
        mModel = null
    }

    /**
     * 只有当 [mView] 不为 null, 并且 [mView] 实现了 [LifecycleOwner] 时, 此方法才会被调用
     * 所以当您想在 [Service] 以及一些自定义 [View] 或自定义类中使用 `Presenter` 时
     * 您也将不能继续使用 [OnLifecycleEvent] 绑定生命周期
     *
     * @param owner link [SupportActivity] and [Fragment]
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        /**
         * 注意, 如果在这里调用了 [onDestroy] 方法, 会出现某些地方引用 [mModel] 或 [mView] 为 null 的情况
         * 比如在 [RxLifecycle] 终止 [Observable] 时, 在 [io.reactivex.Observable.doFinally] 中却引用了 [mView] 做一些释放资源的操作, 此时会空指针
         * 或者如果你声明了多个 @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY) 时在其他 @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
         * 中引用了 [mModel] 或 [mView] 也可能会出现此情况
         */
        owner.lifecycle.removeObserver(this)
    }

    /**
     * 是否使用 [EventBus],默认为使用(true)，
     *
     * @return
     */
    open fun useEventBus(): Boolean {
        return false
    }

    override fun bindToLifecycle(disposable: Disposable) {
        mView?.bindToLifecycle(disposable)
    }

    override fun removeFromLifecycle(disposable: Disposable) {
        mView?.removeFromLifecycle(disposable)
    }

}