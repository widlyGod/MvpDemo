package com.example.safe.dao

import androidx.room.*

/**
 * Created by hy on 2019/10/30
 */
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg entity: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(list: List<T>)

    @Insert
    fun insertAutoGenerateId(entity: T): Long

    @Insert
    fun insertAutoGenerateId(vararg entity: T): List<Long>

    @Insert
    fun insertAutoGenerateId(list: List<T>): List<Long>

    @Insert
    fun insert(vararg entity: T)

    @Insert
    fun insert(list: List<T>)

    @Update
    fun update(vararg entity: T)

    @Update
    fun update(list: List<T>)

    @Delete
    fun delete(vararg entity: T)

    @Delete
    fun delete(list: List<T>)
}