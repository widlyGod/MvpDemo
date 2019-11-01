package com.example.safe.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
class UserBean (
    @PrimaryKey
    var name :String,
    var password:String,
    var createTime: Long = System.currentTimeMillis()
)