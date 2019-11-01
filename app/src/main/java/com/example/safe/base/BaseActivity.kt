package com.example.safe.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.safe.base.mvp.CIPresenter
import com.example.safe.utils.IRxLifecycleProvider
import com.example.safe.utils.RxLifecycleHandler
import com.xmssx.common.mvp.CIView
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract class BaseActivity<P : CIPresenter> : AppCompatActivity(), IRxLifecycleProvider, CIView {

    private val mRxLifecycleHandler: RxLifecycleHandler by lazy { RxLifecycleHandler() }

    protected var mPresenter: P? = null
        @Inject set

    override fun onStart() {
        super.onStart()
        mPresenter?.setView(this)
    }

    override fun bindToLifecycle(disposable: Disposable) {
        mRxLifecycleHandler.bindToLifecycle(disposable)
    }

    override fun removeFromLifecycle(disposable: Disposable) {
        mRxLifecycleHandler.removeFromLifecycle(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        mRxLifecycleHandler.clear()
        mPresenter?.onDestroy()
    }

    override fun error(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
