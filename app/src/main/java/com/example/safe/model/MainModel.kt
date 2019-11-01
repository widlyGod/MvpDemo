package com.example.safe.model

import com.example.safe.base.CBaseModel
import com.example.safe.contract.MainContract
import com.example.safe.db.entity.UserBean
import com.example.safe.db.entity.UserDataBase
import com.example.safe.db.repository.UserRepo
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by hy on 2019/10/30
 */
class MainModel
@Inject constructor(private val UserRepoHelper: UserRepo) : CBaseModel(), MainContract.Model {
    override fun query(name: String): Single<UserBean> {
        return  UserRepoHelper.loadUserByName(name)
    }

    override fun cleanUser(): Single<Unit> {
        return  UserRepoHelper.cleanUser()
    }

    override fun queryAll(): Single<List<UserBean>> {
        return  UserRepoHelper.loadUser()
    }


    override fun saveUser(userBean: UserBean): Single<Unit> {
        return UserRepoHelper.insertUser(userBean)

    }
}