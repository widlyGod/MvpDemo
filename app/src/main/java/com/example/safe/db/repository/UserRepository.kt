package com.example.safe.db.repository

import com.example.safe.db.dao.UserDao
import com.example.safe.db.entity.UserBean
import io.reactivex.Single
import javax.inject.Inject

class UserRepository @Inject internal constructor(private val userDao: UserDao) : UserRepo {
    override fun loadUserByName(name:String): Single<UserBean>  =
        Single.fromCallable {
            userDao.query(name)
        }

    override fun insertUser(userBean: UserBean): Single<Unit> =
        Single.fromCallable {
            userDao.insert(userBean)
        }

    override fun cleanUser(): Single<Unit> = Single.create {
        userDao.deleteAll()
    }


    override fun loadUser(): Single<List<UserBean>> = Single.fromCallable {
        userDao.queryAllRx()
    }


}

