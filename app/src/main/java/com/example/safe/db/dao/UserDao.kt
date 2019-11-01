package com.example.safe.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.safe.dao.BaseDao
import com.example.safe.db.entity.UserBean
import io.reactivex.Single

@Dao
interface UserDao : BaseDao<UserBean> {

    @Query("select * from UserBean order by createTime desc")
    fun queryAllRx(): List<UserBean>

    @Query("select * from UserBean where name = :name")
    fun query(name: String): UserBean

    @Query("delete from UserBean")
    fun deleteAll(): Int
}