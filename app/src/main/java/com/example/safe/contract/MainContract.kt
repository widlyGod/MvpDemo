package com.example.safe.contract

import com.example.safe.base.mvp.CIModel
import com.example.safe.db.entity.UserBean
import com.xmssx.common.mvp.CIView
import io.reactivex.Single

interface MainContract {

    interface View : CIView {
        fun register()
        fun queryAll(list: List<UserBean>)
        fun login(encode: String, boolean: Boolean)
    }

    interface Model : CIModel {
        fun saveUser(userBean: UserBean): Single<Unit>
        fun queryAll(): Single<List<UserBean>>
        fun cleanUser(): Single<Unit>
        fun query(name: String): Single<UserBean>
    }
}