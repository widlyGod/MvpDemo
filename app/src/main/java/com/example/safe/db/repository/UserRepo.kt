package com.example.safe.db.repository

import com.example.safe.db.entity.UserBean
import io.reactivex.Single

interface UserRepo {

    fun cleanUser(): Single<Unit>

    fun insertUser(userBean: UserBean): Single<Unit>

    fun loadUser(): Single<List<UserBean>>

    fun loadUserByName(name:String): Single<UserBean>
}