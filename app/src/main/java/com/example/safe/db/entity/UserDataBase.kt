package com.example.safe.db.entity

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.safe.db.dao.UserDao

@Database(entities = [
    UserBean::class
], version = 1)
abstract class UserDataBase : RoomDatabase() {

    abstract fun searchUserDao(): UserDao

}