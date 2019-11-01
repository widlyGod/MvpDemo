package com.example.safe.presenter

import com.example.safe.base.BasePresenter
import com.example.safe.contract.MainContract
import com.example.safe.db.entity.UserBean
import com.example.safe.db.repository.UserRepo
import com.example.safe.utils.ActivityScope
import com.example.safe.utils.bindToLifecycle
import com.example.safe.utils.subscribeWithView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@ActivityScope
class MainPresenter
@Inject
constructor(model: MainContract.Model) :
    BasePresenter<MainContract.View, MainContract.Model>(model) {

    fun saveUser(name: String, password: String) {
        mModel!!.saveUser(UserBean(name, password))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWithView(mView, "注册失败") {
                mView?.register()
            }.bindToLifecycle(this)
    }

    fun load() {
        mModel!!.queryAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWithView(mView, "查询失败") {
                mView?.queryAll(it)
            }.bindToLifecycle(this)
    }

    fun cleanUser() {
        mModel!!.cleanUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWithView(mView, "删除失败") {

            }.bindToLifecycle(this)
    }

    fun query(name: String, password: String, encrypt: String) {
        mModel!!.query(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWithView(mView, "无此账号") {
                mView?.login(password, it.password == encrypt)
            }.bindToLifecycle(this)
    }
}
